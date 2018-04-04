package com.icss.mvp.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.tmatesoft.svn.core.ISVNLogEntryHandler;
import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNLogEntryPath;
import org.tmatesoft.svn.core.SVNNodeKind;
import org.tmatesoft.svn.core.SVNProperties;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.fs.FSRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.internal.wc.DefaultSVNOptions;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNDiffClient;
import org.tmatesoft.svn.core.wc.SVNLogClient;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

import com.icss.mvp.entity.SvnLogs;

public class SvnkitUtils
{
    private static String userName;
    
    private static String password;
    
    private static String urlString;
    
    private static String tempDir = System.getProperty("java.io.tmpdir");
    
    private static DefaultSVNOptions options = SVNWCUtil.createDefaultOptions(true);
    
    private static Random random = new Random();
    
    private static SVNRepository repos;
    
    private static ISVNAuthenticationManager authManager;
    
    @SuppressWarnings("deprecation")
    public static void setupLibrary(String userName2, String password2, String urlString2)
    {
        userName = userName2;
        password = password2;
        urlString = urlString2;
        DAVRepositoryFactory.setup();
        // svn://, svn+xxx:// (svn+ssh:// in particular)
        SVNRepositoryFactoryImpl.setup();
        // file:///
        FSRepositoryFactory.setup();
        
        try
        {
            repos = SVNRepositoryFactory.create(SVNURL.parseURIDecoded(urlString));
            authManager =
                SVNWCUtil.createDefaultAuthenticationManager(userName, password);
            repos.setAuthenticationManager(authManager);
        }
        catch (SVNException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    /**获取一段时间内，所有的commit记录 
     * @param st    开始时间 
     * @param et    结束时间 
     * @return 
     * @throws SVNException 
     */
    public static SVNLogEntry[] getLogByTime(Date st, Date et)
        throws SVNException
    {
        long startRevision = repos.getDatedRevision(st);
        long endRevision = repos.getDatedRevision(et);
        
        @SuppressWarnings("unchecked")
        Collection<SVNLogEntry> logEntries = repos.log(new String[] {""},
            null,
            startRevision,
            endRevision,
            true,
            true);
        SVNLogEntry[] svnLogEntries = logEntries.toArray(new SVNLogEntry[0]);
        return svnLogEntries;
    }
    
    /**获取版本比较日志，并存入临时文件 
     * @param startVersion 
     * @param endVersion 
     * @return 
     * @throws SVNException 
     * @throws IOException 
     */
    public static File getChangeLog(long startVersion, long endVersion)
        throws SVNException, IOException
    {
        SVNDiffClient diffClient = new SVNDiffClient(authManager, options);
        diffClient.setGitDiffFormat(true);
        File tempLogFile = null;
        OutputStream outputStream = null;
        String svnDiffFile = null;
        
        do
        {
            svnDiffFile =
                tempDir + "/svn_diff_file_" + startVersion + "_" + endVersion + "_" + random.nextInt(10000) + ".txt";
            tempLogFile = new File(svnDiffFile);
        } while (tempLogFile != null && tempLogFile.exists());
        try
        {
            tempLogFile.createNewFile();
            outputStream = new FileOutputStream(svnDiffFile);
            diffClient.doDiff(SVNURL.parseURIEncoded(urlString),
                SVNRevision.create(startVersion),
                SVNURL.parseURIEncoded(urlString),
                SVNRevision.create(endVersion),
                org.tmatesoft.svn.core.SVNDepth.UNKNOWN,
                true,
                outputStream);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (outputStream != null)
                try
                {
                    outputStream.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
        }
        return tempLogFile;
    }
    
    /**解析单个文件变更日志 
     * @param str 
     * @param log 
     * @return 
     */
    public static ChangeFile parseChangeFile(StringBuffer str, SVNLogEntry log)
    {
        int index = str.indexOf("\n@@");
        if (index > 0)
        {
            String header = str.substring(0, index);
            String[] headers = header.split("\n");
            String filePath = "/" + headers[0].substring(7);
            // char changeType = log.getChangedPaths().get(filePath).getType();
            int bodyIndex = str.indexOf("@@\n") + 3;
            String body = str.substring(bodyIndex);
            ChangeFile changeFile = new ChangeFile(filePath, 'U', body);
            return changeFile;
        }
        else
        {
            String[] headers = str.toString().split("\n");
            String filePath = "/" + headers[0].substring(7);
            ChangeFile changeFile = new ChangeFile(filePath, 'U', null);
            return changeFile;
        }
    }
    
    /**通过比较日志，统计以+号开头的非空行 
     * @param changeFile 
     * @return 
     */
    public static void staticOneFileChange(ChangeFile changeFile)
    {
        String content = changeFile.getFileContent();
        int sum = 0;
        int delSum = 0;
        if (changeFile != null)
        {
            content = '\n' + content + '\n';
            char[] chars = content.toCharArray();
            int len = chars.length;
            // 判断当前行是否以+号开头
            boolean startPlus = false;
            boolean startPlusDel = false;
            // 判断当前行，是否为空行（忽略第一个字符为加号）
            boolean notSpace = false;
            
            for (int i = 0; i < len; i++)
            {
                char ch = chars[i];
                if (ch == '\n')
                {
                    // 当当前行是+号开头，同时其它字符都不为空，则行数+1
                    if (startPlus && notSpace)
                    {
                        sum++;
                        notSpace = false;
                    }
                    // 当当前行是-号开头，同时其它字符都不为空，则行数+1
                    if (startPlusDel && notSpace)
                    {
                        delSum++;
                        notSpace = false;
                    }
                    // 为下一行做准备，判断下一行是否以+头
                    if (i < len - 1 && chars[i + 1] == '+')
                    {
                        startPlus = true;
                        // 跳过下一个字符判断，因为已经判断了
                        i++;
                    }
                    else
                    {
                        startPlus = false;
                    }
                    // 为下一行做准备，判断下一行是否以-头
                    if (i < len - 1 && chars[i + 1] == '-')
                    {
                        startPlusDel = true;
                        // 跳过下一个字符判断，因为已经判断了
                        i++;
                    }
                    else
                    {
                        startPlusDel = false;
                    }
                }
                else if ((startPlus || startPlusDel) && ch > ' ')
                {// 如果当前行以+开头才进行非空行判断
                    notSpace = true;
                }
            }
        }
        changeFile.setModifySum(sum);
        changeFile.setDelSum(delSum);
    }
    
    /**统计一段时间内代码增加量 
     * @param st 
     * @param et 
     * @param projectNo 项目编号
     * @return 
     * @throws Exception 
     */
    public static List<SvnLogs> staticticsCodeAddByTime(Date st, Date et, String projectNo)
        throws Exception
    {
        List<SvnLogs> history = new ArrayList<SvnLogs>();
        SVNLogEntry[] logs = getLogByTime(st, et);
        if (logs.length > 0)
        {
            for (SVNLogEntry log : logs)
            {
                SvnLogs entity = new SvnLogs();
                entity.setID(UUIDUtil.getNew());
                entity.setAUTHOR(log.getAuthor());
                entity.setCOMMITTIME(log.getDate());
                entity.setMESSAGE(log.getMessage());
                entity.setREVISION(log.getRevision());
                entity.setFILENUM(log.getChangedPaths().size());
                entity.setCOLDATE(st);
                entity.setNO(projectNo);
                File logFile = getChangeLog(log.getRevision() - 1, log.getRevision());
                staticticsCodeAdd(log, logFile, entity);
                history.add(entity);
            }
        }
        return history;
    }
    
    /**分析变更的代码，统计代码增量 
     * @param file 
     * @param listPath 
     * @param logId 
     * @return 
     * @throws Exception 
     */
    private static void staticticsCodeAdd(SVNLogEntry log, File file, SvnLogs entity)
        throws Exception
    {
        System.out.println("开始统计修改代码行数");
        FileReader fileReader = new FileReader(file);
        BufferedReader in = new BufferedReader(fileReader);
        int sum = 0;
        int delSum = 0;
        String line = null;
        StringBuffer buffer = new StringBuffer(1024);
        boolean start = false;
        while ((line = in.readLine()) != null)
        {
            if (line.startsWith("Index:"))
            {
                if (start)
                {
                    ChangeFile changeFile = parseChangeFile(buffer, log);
                    staticOneFileChange(changeFile);
                    System.out.println("filePath=" + changeFile.getFilePath() + " changeType="
                        + changeFile.getChangeType() + " addLines=" + changeFile.getModifySum());
                    sum += changeFile.getModifySum();
                    delSum += changeFile.getDelSum();
                    buffer.setLength(0);
                }
                start = true;
            }
            buffer.append(line).append('\n');
        }
        if (buffer.length() > 0)
        {
            ChangeFile changeFile = parseChangeFile(buffer, log);
            staticOneFileChange(changeFile);
            System.out.println("filePath=" + changeFile.getFilePath() + "  changeType=" + changeFile.getChangeType()
                + "  addLines=" + changeFile.getModifySum());
            sum += changeFile.getModifySum();
            delSum += changeFile.getDelSum();
        }
        in.close();
        fileReader.close();
        boolean deleteFile = file.delete();
        System.out.println("-----delete file-----" + deleteFile);
        entity.setMODIFYNUM(sum);
        entity.setDELNUM(delSum);
    }
    
    /**获取某一版本有变动的文件路径 
     * @param version 
     * @return 
     * @throws SVNException 
     */
    public List<SVNLogEntryPath> getChangeFileList(long version)
        throws SVNException
    {
        
        final List<SVNLogEntryPath> result = new ArrayList<SVNLogEntryPath>();
        SVNLogClient logClient = new SVNLogClient(authManager, options);
        SVNURL url = SVNURL.parseURIEncoded(urlString);
        String[] paths = {"."};
        SVNRevision pegRevision = SVNRevision.create(version);
        SVNRevision startRevision = SVNRevision.create(version);
        SVNRevision endRevision = SVNRevision.create(version);
        boolean stopOnCopy = false;
        boolean discoverChangedPaths = true;
        long limit = 9999l;
        
        ISVNLogEntryHandler handler = new ISVNLogEntryHandler()
        {
            
            /** 
             * This method will process when doLog() is done 
             */
            @Override
            public void handleLogEntry(SVNLogEntry logEntry)
                throws SVNException
            {
                System.out.println("Author: " + logEntry.getAuthor());
                System.out.println("Date: " + logEntry.getDate());
                System.out.println("Message: " + logEntry.getMessage());
                System.out.println("Revision: " + logEntry.getRevision());
                System.out.println("-------------------------");
                Map<String, SVNLogEntryPath> maps = logEntry.getChangedPaths();
                Set<Map.Entry<String, SVNLogEntryPath>> entries = maps.entrySet();
                for (Map.Entry<String, SVNLogEntryPath> entry : entries)
                {
                    // System.out.println(entry.getKey());
                    SVNLogEntryPath entryPath = entry.getValue();
                    result.add(entryPath);
                    System.out.println(entryPath.getType() + " " + entryPath.getPath());
                }
            }
        };
        // Do log
        try
        {
            logClient.doLog(url,
                paths,
                pegRevision,
                startRevision,
                endRevision,
                stopOnCopy,
                discoverChangedPaths,
                limit,
                handler);
        }
        catch (SVNException e)
        {
            System.out.println("Error in doLog() ");
            e.printStackTrace();
        }
        return result;
    }
    
    /**获取指定文件内容 
     * @param url   svn地址 
     * @return 
     */
    public String checkoutFileToString(String url)
    {// "", -1, null
        try
        {
            SVNDirEntry entry = repos.getDir("", -1, false, null);
            int size = (int)entry.getSize();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream(size);
            SVNProperties properties = new SVNProperties();
            repos.getFile("", -1, properties, outputStream);
            String doc = new String(outputStream.toByteArray(), Charset.forName("utf-8"));
            return doc;
        }
        catch (SVNException e)
        {
            e.printStackTrace();
        }
        return null;
    }
    
    /**列出指定SVN 地址目录下的子目录  
     * @param url  
     * @return  
     * @throws SVNException  
     */
    public List<SVNDirEntry> listFolder(String url)
    {
        if (checkPath(url) == 1)
        {
            
            try
            {
                Collection<SVNDirEntry> list = repos.getDir("", -1, null, (List<SVNDirEntry>)null);
                List<SVNDirEntry> dirs = new ArrayList<SVNDirEntry>(list.size());
                dirs.addAll(list);
                return dirs;
            }
            catch (SVNException e)
            {
                e.printStackTrace();
            }
            
        }
        return null;
    }
    
    /**检查路径是否存在  
     * @param url  
     * @return 1：存在    0：不存在   -1：出错  
     */
    public int checkPath(String url)
    {
        SVNNodeKind nodeKind;
        try
        {
            nodeKind = repos.checkPath("", -1);
            boolean result = nodeKind == SVNNodeKind.NONE ? false : true;
            if (result)
                return 1;
        }
        catch (SVNException e)
        {
            e.printStackTrace();
            return -1;
        }
        return 0;
    }
    
    // public static void main(String[] args)
    // {
    // SvnkitUtils demo = new SvnkitUtils();
    // Date now = new Date();
    // Date twoDayAgo = new Date(now.getTime() - 24 * 3600000);
    // try
    // {
    // List<SvnLogs> sum = demo.staticticsCodeAddByTime(now, twoDayAgo);
    // System.out.println("sum=" + sum);
    // // demo.getChangeFileList(487837L);
    // }
    // catch (Exception e)
    // {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }
    //
    // }
    
}

class ChangeFile
{
    
    private String filePath;
    
    private String fileType;
    
    /**A表示增加文件，M表示修改文件，D表示删除文件，U表示末知 
     *  
     */
    private Character changeType;
    
    private String fileContent;
    
    private int delSum;
    
    private int modifySum;
    
    public int getDelSum()
    {
        return delSum;
    }
    
    public void setDelSum(int delSum)
    {
        this.delSum = delSum;
    }
    
    public int getModifySum()
    {
        return modifySum;
    }
    
    public void setModifySum(int modifySum)
    {
        this.modifySum = modifySum;
    }
    
    public ChangeFile()
    {
    }
    
    public ChangeFile(String filePath)
    {
        this.filePath = filePath;
        this.fileType = getFileTypeFromPath(filePath);
    }
    
    public ChangeFile(String filePath, Character changeType, String fileContent)
    {
        this.filePath = filePath;
        this.changeType = changeType;
        this.fileContent = fileContent;
        this.fileType = getFileTypeFromPath(filePath);
    }
    
    public String getFilePath()
    {
        return filePath;
    }
    
    public void setFilePath(String filePath)
    {
        this.filePath = filePath;
    }
    
    public String getFileType()
    {
        return fileType;
    }
    
    public void setFileType(String fileType)
    {
        this.fileType = fileType;
    }
    
    public Character getChangeType()
    {
        return changeType;
    }
    
    public void setChangeType(Character changeType)
    {
        this.changeType = changeType;
    }
    
    public String getFileContent()
    {
        return fileContent;
    }
    
    public void setFileContent(String fileContent)
    {
        this.fileContent = fileContent;
    }
    
    private static String getFileTypeFromPath(String path)
    {
        String FileType = "";
        int idx = path.lastIndexOf(".");
        if (idx > -1)
        {
            FileType = path.substring(idx + 1).trim().toLowerCase();
        }
        return FileType;
    }
}
