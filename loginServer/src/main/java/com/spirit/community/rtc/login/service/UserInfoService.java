package com.spirit.community.rtc.login.service;

import com.spirit.community.rtc.login.common.exception.MainStageException;
import com.spirit.community.rtc.login.service.dao.entity.UserInfo;

public interface UserInfoService {
    boolean identity(Long uid, String passwd) throws MainStageException;
    void register(UserInfo info);
    void removeUser(Long uid);
}
