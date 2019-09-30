package com.spirit.community.roomgate.service;

import com.spirit.community.roomgate.common.exception.MainStageException;
import com.spirit.community.roomgate.service.dao.entity.UserInfo;

public interface UserInfoService {
    boolean identity(Long uid, String passwd) throws MainStageException;
    UserInfo register(UserInfo info);
    void removeUser(Long uid);
}
