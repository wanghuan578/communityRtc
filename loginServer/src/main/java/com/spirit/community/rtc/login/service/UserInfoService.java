package com.spirit.community.rtc.login.service;

import com.spirit.community.rtc.login.service.dao.entity.UserInfo;

public interface UserInfoService {
    boolean identity(int uid, String passwd);
    void register(UserInfo info);
}
