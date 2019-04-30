package cn.kungreat.bbs.service;

import cn.kungreat.bbs.domain.User;

import java.util.List;

public interface UserService {

    int insert(User record);

    User selectByPrimaryKey(String account);

    List<User> selectAll();

    int updateByPrimaryKey(User record);
}
