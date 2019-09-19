package com.spirit.community.rtc.login.service.impl;

import com.spirit.community.rtc.login.service.UserInfoService;
import com.spirit.community.rtc.login.service.dao.entity.UserInfo;
import org.springframework.stereotype.Component;

@Component
public class UserInfoServiceImpl implements UserInfoService {

    @Override
    public boolean identity(int uid, String passwd) {
        return false;
    }

    @Override
    public void register(UserInfo info) {

    }
}
