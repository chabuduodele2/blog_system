package com.itheima.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserAuthorityMapper {
    @Insert("insert into t_user_authority(user_id, authority_id) values(#{userId}, #{authorityId})")
    public void setUserAuthority(Integer userId, Integer authorityId);
}
