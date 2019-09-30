package com.spirit.community.roomgate.service.dao.repository;

import com.spirit.community.roomgate.service.dao.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
    //UserInfo findByUserInfo(Long id);
}
