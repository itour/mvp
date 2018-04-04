package com.icss.mvp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.icss.mvp.entity.UserDetailInfo;
import com.icss.mvp.entity.UserInfo;

public interface IUserManagerDao {
	List<UserInfo> queryUserInfos(@Param("user") UserInfo userInfo,
			@Param("sort") String sort, @Param("order") String order);

	int deleteUser(UserInfo user);

	int updateUser(UserInfo user);

	int addUserInfo(UserDetailInfo userDetailInfo);

	List<UserDetailInfo> isExistsByAccount(String userName);
	
	UserInfo isExistis(String userName);
}