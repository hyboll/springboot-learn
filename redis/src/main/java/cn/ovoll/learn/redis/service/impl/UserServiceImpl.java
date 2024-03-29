package cn.ovoll.learn.redis.service.impl;

import cn.ovoll.learn.redis.entity.UserExample;
import cn.ovoll.learn.redis.mapper.UserMapper;
import cn.ovoll.learn.redis.service.UserService;
import cn.ovoll.learn.redis.common.RedisUtil;
import cn.ovoll.learn.redis.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    @Autowired
    private RedisUtil redisUtil;

    public List<User> getUserListByExample(String loginName) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andLoginNameLike("%" + loginName + "%");
        return userMapper.selectByExample(userExample);
    }

    public User getUserByExample(Integer userId) {
        if (redisUtil.exists(userId.toString())) {
            System.out.println("从redis中获取用户数据》》》》》》");
            return (User) redisUtil.get(userId.toString());
        } else {
            System.out.println("从数据库中获取用户数据》》》》》》");
            User user = userMapper.selectByPrimaryKey(userId);
            System.out.println("将用户数据存入redis中》》》》》》");
            redisUtil.set(userId.toString(), user, 60L);
            return user;
        }
    }
}
