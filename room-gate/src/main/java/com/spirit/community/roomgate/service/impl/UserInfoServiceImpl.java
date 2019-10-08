package com.spirit.community.roomgate.service.impl;


import com.spirit.community.common.exception.MainStageException;
import com.spirit.community.roomgate.service.UserInfoService;
import com.spirit.community.roomgate.service.dao.entity.UserInfo;
import com.spirit.community.roomgate.service.dao.repository.UserInfoRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Optional;

import static com.spirit.community.common.exception.ExceptionCode.USERID_OR_PASSWD_INVALID;
import static com.spirit.community.common.exception.ExceptionCode.USERINFO_NOT_EXIST;


@Component
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Override
    public boolean identity(Long uid, String passwd) throws MainStageException {
        if (userInfoRepository.existsById(uid)) {
            Optional<UserInfo> optional = userInfoRepository.findById(uid);
            UserInfo userInfo = optional.get();
            if (StringUtils.equals(userInfo.getPassword(), passwd)) {
                return true;
            }
            else {
                throw new MainStageException(USERID_OR_PASSWD_INVALID);
            }
        }
        else {
            throw new MainStageException(USERINFO_NOT_EXIST);
        }
    }

    @Override
    public UserInfo register(UserInfo info) {
        return userInfoRepository.save(info);
    }

    @Override
    public void removeUser(Long uid) {
        userInfoRepository.deleteById(uid);
    }
}
