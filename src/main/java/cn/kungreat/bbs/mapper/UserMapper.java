package cn.kungreat.bbs.mapper;

import cn.kungreat.bbs.domain.User;
import cn.kungreat.bbs.query.UserQuery;
import cn.kungreat.bbs.vo.CategoryTotal;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {

    int insert(User record);

    User selectByPrimaryKey(String account);

    List<User> selectAll(UserQuery query);
    long selectCount(UserQuery query);

    List<User> selectByAccounts(@Param("acts") List<String> acts);
    int updateByPrimaryKey(User record);

    int updateAccumulatePoints(@Param("current") int current, @Param("original") int original, @Param("account") String account);

    int updateImg(@Param("account") String account, @Param("path") String path);

    List<CategoryTotal> selectCategoryTotal(UserQuery query);

    List<String> categoryNames();
}