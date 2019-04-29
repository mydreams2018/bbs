package cn.kungreat.bbs.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class User {
    private Long id;

    private String account;

    private String password;

    private String alias;

    private Long phone;

    private String img;

    private Boolean isVip;

    private Byte state;

    private String email;

    private String description;
}