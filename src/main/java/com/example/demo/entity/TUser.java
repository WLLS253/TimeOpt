package com.example.demo.entity;


import com.example.demo.plugins.UserPasswordEncrypt;
import com.mysql.cj.x.protobuf.MysqlxDatatypes;

import javax.persistence.*;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

@Entity
public class TUser extends  BaseEntity{


    @Column(unique = true,nullable = false)
    private String username;


    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String tel;

    @Column(unique = true)
    private String wechat;

    @Column(unique = true)
    private String qq;

    @Column(nullable = false,unique = true)
    private String email;

    private String image;


    private Integer grade;

    @ManyToMany
    @JoinTable(
            name = "UserMissions",
            joinColumns = {@JoinColumn(name = "user_id",referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "mission_id",referencedColumnName = "id")}
    )
    private List<Mission> missions;



    @OneToMany(targetEntity = Schedule.class)
    private List<Schedule> scheduleList;

    @OneToMany(targetEntity = TShareEvent.class)
    private List<TShareEvent> tShareEvents;


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

//    public void setPassword(String password) {
//        this.password = password;
//    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public void setPasswordT(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        this.password = UserPasswordEncrypt.encrypt(password, "abcdef" + this.username + "abcdef" + this.username, 233);
    }

    public  void  setPassword(String password){
        this.password=password;
    }
    public boolean checkPassword(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return this.password.equals(UserPasswordEncrypt.encrypt(password, "abcdef" + this.username + "abcdef" + this.username, 233));
    }

    @Override
    public String toString() {
        return "TUser{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", tel='" + tel + '\'' +
                ", wechat='" + wechat + '\'' +
                ", qq='" + qq + '\'' +
                ", email='" + email + '\'' +
                ", image='" + image + '\'' +
                ", grade=" + grade +
                ", missions=" + missions +
                ", scheduleList=" + scheduleList +
                ", tShareEvents=" + tShareEvents +
                '}';
    }
}
