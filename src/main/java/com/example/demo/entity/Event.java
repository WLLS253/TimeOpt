package com.example.demo.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Event extends BaseEntity {

    @Column(nullable = false)
    private String ename;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;

    @Column(nullable = false)
    private Short level;

    @Column(nullable = false)
    private String content;

    private boolean isFinish;

    @Column(nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date finishTime;

    @ManyToOne(cascade =CascadeType.REFRESH,targetEntity =TUser.class,fetch = FetchType.LAZY)
    @JoinColumn(name="tuser_id")
    private TUser tUser;

    @ManyToOne(cascade = CascadeType.REFRESH,targetEntity = Schedule.class,fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;



    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Short getLevel() {
        return level;
    }

    public void setLevel(Short level) {
        this.level = level;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isFinish() {
        return isFinish;
    }

    public void setFinish(boolean finish) {
        isFinish = finish;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    @JsonBackReference
    public TUser gettUser() {
        return tUser;
    }

    public void settUser(TUser tUser) {
        this.tUser = tUser;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }
}
