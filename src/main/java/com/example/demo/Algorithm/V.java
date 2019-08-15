package com.example.demo.Algorithm;

/**
 *
 * @author 于鑫舟
 * V代表一个事件，也是Dag图中的顶点
 */
public class V {
    public String eName; //事件名称
    public String start_time; //开始时间
    public String end_time; //结束时间
    public short level; //优先级
    public String content;  //内容
    public String eId;  //ID
    public boolean finish;  //是否完成
    public String finish_time;  //实际完成时间
    //接下来是算法部分添加的额外属性:
    public short path_level; //在图中处在哪条路径(0为主路径，path_level越高优先级越低)
    public byte in_degree;  //顶点的入度
    public byte out_degree; //顶点的出度
    public ArcNode firstedge;   //指向边表的第一个边结点
    public final static short PATHMAX=1000;  //顶点初始的path_value
    
    public V(){
        super();
        this.finish=false;
        this.finish_time="00:00";
        this.path_level=V.PATHMAX;
        this.in_degree=0;
        this.out_degree=0;
        this.firstedge=null;
    }

    @Override
    public String toString() {
        return "V{" +
                "eName='" + eName + '\'' +
                ", start_time='" + start_time + '\'' +
                ", end_time='" + end_time + '\'' +
                ", level=" + level +
                ", content='" + content + '\'' +
                ", eId='" + eId + '\'' +
                ", finish=" + finish +
                ", finish_time='" + finish_time + '\'' +
                ", path_level=" + path_level +
                ", in_degree=" + in_degree +
                ", out_degree=" + out_degree +
                ", firstedge=" + firstedge +
                '}';
    }
}
