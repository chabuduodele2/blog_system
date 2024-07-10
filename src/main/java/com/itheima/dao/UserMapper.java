package com.itheima.dao;

import com.itheima.model.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author p5471
 */
@Mapper
public interface UserMapper {
    @Insert("insert into t_user(username, password, email, created, valid) values(#{username}, #{password}, #{email}, #{created}, #{valid})")
    public int registerUser(User user);
    @Select("select * from t_user where username = #{username}")
    public User getUserByUsername(String username);
}
