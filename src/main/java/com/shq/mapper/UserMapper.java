package com.shq.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shq.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
