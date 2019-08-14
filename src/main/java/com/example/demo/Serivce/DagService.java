package com.example.demo.Serivce;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Service;

@Service
public class DagService {

    @Autowired
    private DaoIOService daoIOService;


}
