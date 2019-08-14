package com.example.demo.repository;

import com.example.demo.entity.Event;
import com.example.demo.entity.TUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event,Long> {

    public  List<Event>findAllByTUser(TUser tUser);

}
