package cn.kungreat.bbs.service;

import cn.kungreat.bbs.domain.User;
import cn.kungreat.bbs.query.UserQuery;
import cn.kungreat.bbs.vo.CategoryTotal;
import cn.kungreat.bbs.vo.QueryResult;

import java.util.List;

public interface UserService {

    int insert(User record);

    User selectByPrimaryKey(String account);
    List<User> selectByAccounts(List accounts);
    int updateByPrimaryKey(User record);
    int updateImg(String account,String path);
    List<CategoryTotal> selectCategoryTotal(UserQuery query);

    List<String> categoryNames();
    int updateAccumulatePoints(int number,String account);
    QueryResult query(UserQuery query);
}
