package cn.kungreat.bbs.service.impl;

import cn.kungreat.bbs.domain.JavaDetails;
import cn.kungreat.bbs.domain.JavaPosts;
import cn.kungreat.bbs.mapper.JavaDetailsMapper;
import cn.kungreat.bbs.mapper.JavaPostsMapper;
import cn.kungreat.bbs.query.JavaDetailsQuery;
import cn.kungreat.bbs.service.JavaDetailsService;
import com.alibaba.druid.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Date;

@Service
public class JavaDetailsServiceImpl implements JavaDetailsService {
    @Autowired
    private JavaDetailsMapper javaDetailsMapper;
    @Autowired
    private JavaPostsMapper javaPostsMapper;

    @Transactional
    public long insert(JavaDetails record) {
        Assert.isTrue(!StringUtils.isEmpty(record.validMessage()),record.validMessage());
        String account = SecurityContextHolder.getContext().getAuthentication().getName();
        record.setAccount(account);
        record.setPublishTime(new Date());
        javaDetailsMapper.insert(record);
        JavaPosts javaPosts = javaPostsMapper.selectByPrimaryKey(record.getPostsId());
        Assert.isTrue(javaPosts!=null,"数据异常");
        javaPosts.setReplyTimeEnd(new Date());
        javaPosts.setReplyTotal(javaPosts.getReplyTotal() + 1);
        return javaPostsMapper.updateByReply(javaPosts);
    }

    //  用户名AND主贴ID 删除指定用户下指定贴子所有回贴 并减掉回贴数量 要权限控制
    @Transactional
    public int deleteAll(JavaDetailsQuery query) {
        Assert.isTrue(!StringUtils.isEmpty(query.getPostsId().toString())
                        && !StringUtils.isEmpty(query.getAccount()),"主贴ID-用户不能为空");
        int i = javaDetailsMapper.deleteAll(query);
        if(i > 0){
            subtractReply(i, query.getPostsId());
        }
        return i;
    }

    @Transactional
    public int deleteByPrimaryId(Long PrimaryId){
        JavaDetails javaDetails = javaDetailsMapper.selectByPrimaryId(PrimaryId);
        Assert.isTrue(javaDetails!=null,"查询数据失败");
        String account = SecurityContextHolder.getContext().getAuthentication().getName();
        Assert.isTrue(account.equals(javaDetails.getAccount()),
                "没有权限删除别人的贴子");
        int i = javaDetailsMapper.deleteByPrimaryId(PrimaryId);
        if(i > 0){
            subtractReply(i, javaDetails.getPostsId());
        }
        return i;
    }

    private void subtractReply(int i, Long postsId) {
        JavaPosts javaPosts = javaPostsMapper.selectByPrimaryKey(postsId);
        Assert.isTrue(javaPosts != null, "数据异常");
        javaPosts.setReplyTotal(javaPosts.getReplyTotal() - i);
        javaPostsMapper.updateByReply(javaPosts);
    }

    @Override
    public int updateByPrimaryId(JavaDetails record) {
        return 0;
    }

    @Override
    public int updateForPosts(JavaDetails record) {
        return 0;
    }
}
