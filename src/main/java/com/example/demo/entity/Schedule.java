package com.example.demo.entity;


import org.springframework.format.annotation.DateTimeFormat;

import javax.naming.Name;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Schedule extends BaseEntity{


    @Column(unique = true,nullable = false)
    @Temporal(TemporalType.DATE)
    private Date  date;



    @ManyToOne(cascade = CascadeType.REFRESH,targetEntity = TUser.class,fetch = FetchType.LAZY)
    @JoinColumn(name = "TuserSched")
    private  TUser tUser;



    @OneToMany(targetEntity = Event.class)
    private List<Event> eventList;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public TUser gettUser() {
        return tUser;
    }

    public void settUser(TUser tUser) {
        this.tUser = tUser;
    }

    public List<Event> getEventList() {
        return eventList;
    }

    public void setEventList(List<Event> eventList) {
        this.eventList = eventList;
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "date=" + date +
                ", tUser=" + tUser +
                ", eventList=" + eventList +
                ", id=" + id +
                ", createdTime=" + createdTime +
                ", updatedTime=" + updatedTime +
                '}';
    }
}
