package cn.kungreat.bbs.service;

import cn.kungreat.bbs.domain.JavaDetails;
import cn.kungreat.bbs.domain.User;
import java.util.List;

public interface UserService {

    int insert(User record);

    User selectByPrimaryKey(String account);

    List<User> selectAll();
    List<User> selectByAccounts(List<JavaDetails> accounts);
    int updateByPrimaryKey(User record);
    int updateImg(String account,String path);
}
