package com.spirit.community.roomServer.service;


import com.spirit.community.common.exception.MainStageException;
import com.spirit.community.roomServer.service.dao.entity.UserInfo;

public interface UserInfoService {
    boolean identity(Long uid, String passwd) throws MainStageException;
    UserInfo register(UserInfo info);
    void removeUser(Long uid);
}
