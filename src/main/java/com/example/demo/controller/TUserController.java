package com.example.demo.controller;


import com.example.demo.Enums.ExceptionEnums;
import com.example.demo.Result.Result;
import com.example.demo.Serivce.TokenService;
import com.example.demo.Serivce.UploadSerivce;
import com.example.demo.Util.Util;
import com.example.demo.entity.TShareEvent;
import com.example.demo.entity.TUser;
import com.example.demo.plugins.Tokener;
import com.example.demo.repository.TUserRepository;
import org.antlr.v4.runtime.atn.TokensStartState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.sound.midi.Soundbank;
import java.util.List;
import java.util.Optional;

@RestController
public class TUserController  {

    @Autowired
    private TUserRepository tUserRepository;

    @Autowired
    private  UploadSerivce uploadSerivce;


    @Autowired
    private TokenService tokenService;

    @PostMapping(value = "/login",produces = "application/json; charset=utf-8")
    public Result Login(@RequestParam("username") String username, @RequestParam("password") String password){

        try {
            System.out.println(username);
            List<TUser> tUsers=tUserRepository.findByUsername(username);

            boolean valid = false;
            if (tUsers.size()!=0) {
                if (tUsers.get(0).checkPassword(password)) {
                    String token = tokenService.generateToken(String.valueOf(tUsers.get(0).getId()));
//                    response.setHeader("isLogin", "true");
//                    response.setHeader("token", token);
                    return Util.success(tUsers.get(0));
                }
            }
//            response.setHeader("isLogin", "false");
            return  Util.failure(ExceptionEnums.PASSWORD_ERROR);
        } catch (Exception e) {
            e.printStackTrace();
//            response.setHeader("isLogin", "false");
            return  Util.failure(ExceptionEnums.UNKNOW_ERRPR);
        }




    }



    @PostMapping(value = "/user/add")
    public Result addUser(TUser user) {
        try {

            user.setUsername(user.getUsername());
            user.setPasswordT(user.getPassword());
            user.setTel(user.getTel());
            user.setEmail(user.getEmail());
            user.setGrade(user.getGrade());
            user.setWechat(user.getWechat());
            user.setQq(user.getQq());
            System.out.println(user);
            return Util.success(tUserRepository.save(user));
        }catch (Exception e){
            e.printStackTrace();
            return Util.failure(ExceptionEnums.UNKNOW_ERRPR);
        }


    }

    @PostMapping(value = "shareEvent/add")
    public  Result  addshare(TShareEvent shareEvent, @RequestParam("file")MultipartFile file){

        try {
            shareEvent.setImageURL(uploadSerivce.upImageFire(file));
            return Util.success(shareEvent);
        }catch (Exception e){
            return Util.failure(ExceptionEnums.UNKNOW_ERRPR);
        }

    }




    @GetMapping(value = "/pc/list")
    public String PCUserList() {
        return "hhhh";
    }






}
