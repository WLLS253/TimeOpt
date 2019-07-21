package com.example.demo.repository;

import com.example.demo.entity.TUser;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TUserRepository extends JpaRepository<TUser,Long> {

    public List<TUser> findByUsername(String username);

    public  TUser findByEmail(String email);

    public  TUser findByWechat(String wechat);

    public List<TUser>findByGrade(Integer grade);
}
