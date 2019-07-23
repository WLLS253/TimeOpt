package com.example.demo.entity;


import javax.persistence.*;
import java.util.List;

@Entity
public class Mission extends  BaseEntity {

    @Column(nullable = false)
    private  String missName;

    @Column(nullable = false)
    private  short mType;

    @Column(nullable = false)
    private boolean mIsFinish;

    @Column(nullable = false)
    private Integer mGrade;

    @ManyToMany(cascade = CascadeType.REFRESH,fetch = FetchType.LAZY,targetEntity = TUser.class)
    private List<TUser> tUsers;

    public String getMissName() {
        return missName;
    }

    public void setMissName(String missName) {
        this.missName = missName;
    }

    public short getmType() {
        return mType;
    }

    public void setmType(short mType) {
        this.mType = mType;
    }

    public boolean ismIsFinish() {
        return mIsFinish;
    }

    public void setmIsFinish(boolean mIsFinish) {
        this.mIsFinish = mIsFinish;
    }

    public Integer getmGrade() {
        return mGrade;
    }

    public void setmGrade(Integer mGrade) {
        this.mGrade = mGrade;
    }



}
