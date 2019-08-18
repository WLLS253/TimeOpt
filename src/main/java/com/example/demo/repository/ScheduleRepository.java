package com.example.demo.repository;

import com.example.demo.entity.Schedule;
import com.example.demo.entity.TUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule,Long> {

    public List<Schedule>findAllByTUserAndAndDate(TUser tUser, Date date);


}
