package com.example.demo.Serivce;


import com.example.demo.Algorithm.Dag;
import com.example.demo.Algorithm.V;
import com.example.demo.entity.Event;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class DaoIOService {
    private short vnum;//想要创建的顶点数量
    private ArrayList<String> eNames;
    private ArrayList<String> start_times;
    private ArrayList<String> end_times;
    private ArrayList<Short>  levels;
    private ArrayList<String> contents;
    private ArrayList<String> eIds;
    private File data;  //一个文件，里面有事件的顶点数据

    public void addEventList(List<Event> eventList){
        this.vnum=(short)eventList.size();
        try{
            this.data=new File("nodedata.txt");
            this.eNames=new ArrayList<>();
            this.start_times=new ArrayList<>();
            this.end_times=new ArrayList<>();
            this.levels=new ArrayList<>();
            this.contents=new ArrayList<>();
            this.eIds=new ArrayList<>();
            if(this.data.exists())  this.data.delete();//若有该文件则删除
            this.data.createNewFile();
            BufferedWriter writer=new BufferedWriter(new FileWriter(this.data));
            writer.write("事件名称  开始时间  结束时间  优先级  内容  id");
            writer.newLine();
            for(short i=0;i<vnum;i++){
                eNames.add(eventList.get(i).getEname());
                writer.write(eventList.get(i).getEname());
                start_times.add(eventList.get(i).getStartTime().toString());
                writer.write(start_times.get(i)+"  ");
                end_times.add(eventList.get(i).getEndTime().toString());
                writer.write(end_times.get(i)+"  ");
                levels.add(eventList.get(i).getLevel());
                writer.write(i+"  ");
                contents.add(eventList.get(i).getContent());
                writer.write(eventList.get(i).getContent());
                eIds.add(eventList.get(i).getId().toString());
                writer.write(eventList.get(i).getId().toString());
                writer.newLine();   //换行
            }
            writer.flush(); //刷新缓冲区
            writer.close(); //关闭写入
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public V[] addVex(){    //根据初始化好的数据创建顶点，返回一个顶点数组
        V[] v=new V[vnum];
        for(short i=0;i<vnum;i++){
            v[i]=new V();
            v[i].eName=eNames.get(i);
            v[i].start_time=start_times.get(i);
            v[i].end_time=end_times.get(i);
            v[i].level=levels.get(i);
            v[i].content=contents.get(i);
            v[i].eId=eIds.get(i);
        }
        return v;
    }

    public String randomTime(){
        Random r=new Random();
        Byte m=0,h=0; //m为分钟，h为小时
        m=(byte)r.nextInt(60);
        h=(byte)r.nextInt(24);
        String mm,hh;
        mm=(m<10?"0"+m.toString():m.toString());
        hh=(h<10?"0"+h.toString():h.toString());
        return hh+":"+mm;
    }

    public String randomTime(String st){
        Byte m=0,h=0; //m为分钟，h为小时
        String mm,hh;
        Random r=new Random();
        do{            //这儿有死循环，可能是由于伪随机数导致的，不用管
            m=(byte)r.nextInt(60);
            h=(byte)r.nextInt(24);
            mm=(m<10?"0"+m.toString():m.toString());
            hh=(h<10?"0"+h.toString():h.toString());
        }while(Dag.noLater(hh+":"+mm, st)); //end_time>start_time
        return hh+":"+mm;
    }


}
