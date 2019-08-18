package com.example.demo.controller;


import com.example.demo.Algorithm.V;
import com.example.demo.Enums.ExceptionEnums;
import com.example.demo.Result.Result;
import com.example.demo.Serivce.DaoService;
import com.example.demo.Util.Util;
import com.example.demo.entity.Event;
import com.example.demo.entity.Schedule;
import com.example.demo.entity.TUser;
import com.example.demo.repository.EventRepository;
import com.example.demo.repository.ScheduleRepository;
import com.example.demo.repository.TUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.web.bind.annotation.*;

import javax.sound.midi.Soundbank;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
public class DaoController {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private TUserRepository tUserRepository;

    @Autowired
    private DaoService daoService;

    @Autowired
    private ScheduleRepository scheduleRepository;


    @PostMapping(value = "/event/add")
    public Result  addEvent(Event event, @RequestParam("tuser_id")Long tuser_id ){
        try {
            TUser tUser=tUserRepository.findById(tuser_id).get();
            Schedule schedule=new Schedule();
            Date date=event.getStartTime();
            schedule.setDate(date);
            schedule.settUser(tUser);
            scheduleRepository.save(schedule);
            Schedule schedule1=scheduleRepository.findAllByTUserAndAndDate(tUser,date).get(0);
            List<Event> eventList1=schedule1.getEventList();
            System.out.println(schedule1);
            event.setEname(event.getEname());
            event.setContent(event.getContent());
            event.setEndTime(event.getEndTime());
            event.setStartTime(event.getStartTime());
            event.setLevel(event.getLevel());
            event.setFinish(event.isFinish());
            List<Event> eventList=tUser.getEvents();
            System.out.println(eventList);
            System.out.println(eventList1);
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


    //长期安排，根据姓名找到最优安排
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

    //精确到天yyyy-MM-dd，找到某人某一天的安排
    @GetMapping(value = "/sche/find/{name}/{date}")
    public Result findSche(@PathVariable("name") String name,@PathVariable("date") String date){
        try {
            TUser tUser=tUserRepository.findByUsername(name).get(0);
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
            Date date1=simpleDateFormat.parse(date);
            List<Schedule> scheduleList=scheduleRepository.findAllByTUserAndAndDate(tUser,date1);
            if(scheduleList.size()==0){
                return Util.failure(ExceptionEnums.UNFIND_ERROR);
            }else {
                Schedule schedule=scheduleList.get(0);
                List<Event>eventList=schedule.getEventList();
                ArrayList<V>vArrayList=daoService.daoSort(eventList);
                return Util.success(vArrayList);
            }
        }catch (Exception e){
            e.printStackTrace();
            return Util.failure(ExceptionEnums.UNKNOW_ERRPR);
        }
    }

    @PostMapping(value = "/test/")
    public Result testSch(Event event , @RequestParam("test_id") Long id){
        Schedule schedule=scheduleRepository.findById(id).get();
        System.out.println(schedule);
        List<Event>eventList=schedule.getEventList();
        System.out.println(eventList);
        eventList.add(event);
        schedule.setEventList(eventList);

        return  Util.success(eventRepository.save(event));

    }




}
