package com.icss.mvp.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.icss.mvp.dao.IDtsTaskDao;

/**
 * XmlSaxParserUtil Sax解析XML
 * 
 * @author Administrator
 * 
 */
public class XmlSaxParserUtil extends DefaultHandler {
	@Autowired
	private IDtsTaskDao dtsTaskDao;
	private List<Map<String, String>> contentsList = new ArrayList<Map<String, String>>(
			0);
	private Map<String, String> contentsMap;
	// 作用是记录解析时的上一个节点名称
	private String preTag = null;
	private int index = 0;
	public List<Map<String, String>> getContents(InputStream xmlStream)
			throws Exception {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser = factory.newSAXParser();
		parser.parse(xmlStream, this);
		return this.getContents();
	}

	public List<Map<String, String>> getContents() {
		return contentsList;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if ("ROW".equals(qName)) {
			contentsMap = new HashMap<String, String>();
		}
		// 将正在解析的节点名称赋给preTag
		preTag = qName;
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if ("ROW".equals(qName)) {
			contentsList.add(contentsMap);
			contentsMap = null;
			index=0;
		}
		preTag = null;
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		if (preTag != null) {
			String content = new String(ch, start, length);
			if ("data".equals(preTag)) {
				index++;
				contentsMap.put(String.valueOf(index), content);
			}
		}
	}

	public static void main(String[] args) throws Exception {
		XmlSaxParserUtil sax = new XmlSaxParserUtil();
		InputStream input = new FileInputStream("d:/dts.xml");
		List<Map<String, String>> contentsList = sax.getContents(input);
		System.out.println(contentsList);
	}
}
