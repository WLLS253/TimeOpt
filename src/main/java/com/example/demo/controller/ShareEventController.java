package com.example.demo.controller;


import com.example.demo.Enums.ExceptionEnums;
import com.example.demo.Result.Result;
import com.example.demo.Serivce.UploadSerivce;
import com.example.demo.Util.Util;
import com.example.demo.entity.TShareEvent;
import com.example.demo.repository.TShareEventRepsitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
public class ShareEventController {

    @Autowired
    private TShareEventRepsitory tShareEventRepsitory;


    @Autowired
    private UploadSerivce uploadSerivce;

    @PostMapping(value = "shareEvent/add")
    public Result addshare(TShareEvent shareEvent, @RequestParam("file") MultipartFile file){

        try {
            shareEvent.setImageURL(uploadSerivce.upImageFire(file));
            return Util.success(shareEvent);
        }catch (Exception e){
            return Util.failure(ExceptionEnums.UNKNOW_ERRPR);
        }

    }

//
//    @GetMapping(value = "/shareEvent/title/{title}")
//    public  Result getShare(@PathVariable("title") String title, HttpServletRequest request){
//
//
//
//    }



}
