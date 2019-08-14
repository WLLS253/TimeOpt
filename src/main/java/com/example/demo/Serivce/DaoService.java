package com.example.demo.Serivce;


import com.example.demo.Algorithm.Dag;
import com.example.demo.Algorithm.IO;
import com.example.demo.Algorithm.V;
import com.example.demo.entity.Event;
import com.example.demo.repository.EventRepository;
import com.example.demo.repository.TUserRepository;
import org.apache.coyote.OutputBuffer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DaoService {
    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private TUserRepository tUserRepository;


    @Autowired
    private DaoIOService daoIOService;

    public Dag dag;

    public  ArrayList<V> daoSort(List<Event> eventList){
        daoIOService.addEventList(eventList);
        dag=new Dag(daoIOService.addVex());
        ArrayList<V> vList=dag.print();
        System.out.println(vList);

        return vList;

    }





}
