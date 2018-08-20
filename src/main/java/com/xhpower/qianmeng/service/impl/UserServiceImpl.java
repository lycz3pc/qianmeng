package com.xhpower.qianmeng.service.impl;

import com.xhpower.qianmeng.entity.User;
import com.xhpower.qianmeng.dao.UserMapper;
import com.xhpower.qianmeng.service.UserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xc
 * @since 2018-07-25
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
