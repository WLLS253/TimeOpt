package com.example.demo.controller;


import com.example.demo.Enums.ExceptionEnums;
import com.example.demo.Result.Result;
import com.example.demo.Util.Util;
import com.example.demo.entity.TUser;
import com.example.demo.plugins.Tokener;
import com.example.demo.repository.TUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.sound.midi.Soundbank;
import java.util.Optional;

@RestController
public class TUserController  {

    @Autowired
    private TUserRepository tUserRepository;


    @PostMapping(value = "/login",produces = "application/json; charset=utf-8")
    private Result Login(@RequestParam("username") String username, @RequestParam("password") String password, HttpServletResponse response){

        try {
            TUser tUser =new TUser();
            tUser.setUsername(username);

            Optional<TUser> userOptional = tUserRepository.findOne(Example.of(tUser));
            boolean valid = false;
            if (userOptional.isPresent()) {
                if (userOptional.get().checkPassword(password)) {
                    String token = Tokener.generateToken(String.valueOf(userOptional.get().getId()));
                    response.setHeader("isLogin", "true");
                    response.setHeader("token", token);
                    return Util.success(tUser);
                }
            }
            response.setHeader("isLogin", "false");
            return  Util.failure(ExceptionEnums.PASSWORD_ERROR);
        } catch (Exception e) {
            e.printStackTrace();
            response.setHeader("isLogin", "false");
            return  Util.failure(ExceptionEnums.UNKNOW_ERRPR);
        }




    }



    @PostMapping(value = "/user/add")
    public Result addUser(TUser user){
        try {
            user.setUsername(user.getUsername());
            System.out.println(user);
            user.setPasswordtWO(user.getPassword());
            System.out.println(user.getPassword());
            user.setTel(user.getTel());
            user.setEmail(user.getEmail());
            user.setGrade(user.getGrade());
            user.setWechat(user.getWechat());
            user.setQq(user.getQq());
            return Util.success(tUserRepository.save(user));
        }catch (Exception e){
            user.setUsername(user.getUsername());
            System.out.println(user);
            e.printStackTrace();
            return Util.failure(ExceptionEnums.UNKNOW_ERRPR);
        }


    }



}
