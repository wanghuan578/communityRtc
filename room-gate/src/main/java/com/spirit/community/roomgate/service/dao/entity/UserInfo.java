package com.spirit.community.roomgate.service.dao.entity;

import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "tb_user_info")
public class UserInfo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String userName;
    private String password;
    private String nickName;
    private String identificationCardId;
    private Short gender;
    private String invitationCode;


}
