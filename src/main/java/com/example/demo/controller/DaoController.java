package com.example.demo.controller;


import com.example.demo.Algorithm.V;
import com.example.demo.Enums.ExceptionEnums;
import com.example.demo.Result.Result;
import com.example.demo.Serivce.DaoService;
import com.example.demo.Util.Util;
import com.example.demo.entity.Event;
import com.example.demo.entity.TUser;
import com.example.demo.repository.EventRepository;
import com.example.demo.repository.TUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.web.bind.annotation.*;

import javax.sound.midi.Soundbank;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@RestController
public class DaoController {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private TUserRepository tUserRepository;

    @Autowired
    private DaoService daoService;


    @PostMapping(value = "/event/add")
    public Result  addEvent(Event event, @RequestParam("tuser_id")Long tuser_id ){
        try {

            TUser tUser=tUserRepository.findById(tuser_id).get();
            event.setEname(event.getEname());
            event.setContent(event.getContent());
            event.setEndTime(event.getEndTime());
            event.setStartTime(event.getStartTime());
            event.setLevel(event.getLevel());
            event.setFinish(event.isFinish());
            List<Event> eventList=tUser.getEvents();
            eventList.add(event);
            event.settUser(tUser);
            tUser.setEvents(eventList);
            System.out.println(eventList);
            return Util.success(eventRepository.save(event));
        }catch (Exception e){
            e.printStackTrace();
            return Util.failure(ExceptionEnums.UNKNOW_ERRPR);
        }
    }

    @GetMapping(value = "/event/sort/{name}")
    public  Result eventSort(@PathVariable("name") String name){
    try {
            TUser tUser=tUserRepository.findByUsername(name).get(0);
            List<Event>eventList=tUser.getEvents();
            System.out.println(eventList);
            ArrayList<V>vArrayList=daoService.daoSort(eventList);
            return Util.success(vArrayList);
        }catch (Exception e){
            e.printStackTrace();
            return Util.failure(ExceptionEnums.UNKNOW_ERRPR);
        }


    }



}
