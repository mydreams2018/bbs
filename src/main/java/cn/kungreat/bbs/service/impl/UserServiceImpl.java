package cn.kungreat.bbs.service.impl;

import cn.kungreat.bbs.domain.AssemblerDetails;
import cn.kungreat.bbs.domain.JavaDetails;
import cn.kungreat.bbs.domain.User;
import cn.kungreat.bbs.mapper.UserMapper;
import cn.kungreat.bbs.service.UserService;
import com.alibaba.druid.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public int insert(User record) {
        String s = record.validMessage();
        Assert.isTrue(StringUtils.isEmpty(s),s);
        Assert.isTrue(userMapper.selectByPrimaryKey(record.getAccount())==null,"用户名已经存在");
        record.setRegisterTime(new Date());
        record.setIsVip(false);
        record.setPassword(bCryptPasswordEncoder.encode(record.getPassword()));
        return userMapper.insert(record);
    }

    @Override
    public User selectByPrimaryKey(String account) {
        return userMapper.selectByPrimaryKey(account);
    }

    @Override
    public List<User> selectAll() {
        return userMapper.selectAll();
    }

    @Override
    public List<User> selectByAccounts(List accounts) {
        List<User> act = new ArrayList<>();
        for(int x = 0;x < accounts.size(); x++){
            String account = "";
            if(accounts.get(x) instanceof JavaDetails){
                JavaDetails javaDetails =(JavaDetails) accounts.get(x);
                account = javaDetails.getAccount();
            }
            if(accounts.get(x) instanceof AssemblerDetails){
                AssemblerDetails assemblerDetails = (AssemblerDetails) accounts.get(x);
                account =assemblerDetails.getAccount();
            }
            act.add(userMapper.selectByPrimaryKey(account));
        }
        return act;
    }

    @Override
    public int updateByPrimaryKey(User record) {
        return userMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateImg(String account, String path) {
        return userMapper.updateImg(account,path);
    }
}
