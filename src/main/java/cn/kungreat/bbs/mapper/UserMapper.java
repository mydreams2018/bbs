package cn.kungreat.bbs.mapper;

import cn.kungreat.bbs.domain.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {

    int insert(User record);

    User selectByPrimaryKey(String account);

    List<User> selectAll();

    int updateByPrimaryKey(User record);

    int updateImg(@Param("account") String account, @Param("path") String path);
}