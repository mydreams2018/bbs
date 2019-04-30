package cn.kungreat.bbs.mapper;

import cn.kungreat.bbs.domain.User;
import java.util.List;

public interface UserMapper {

    int insert(User record);

    User selectByPrimaryKey(String account);

    List<User> selectAll();

    int updateByPrimaryKey(User record);
}