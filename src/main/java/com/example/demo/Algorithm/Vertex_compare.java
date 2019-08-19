package com.example.demo.Algorithm;

import java.util.Arrays;
import java.util.Comparator;

public class Vertex_compare implements Comparator<Short> {
    private V[] v;  //待排序的事件顶点
    //第一个参数v是Dag的事件顶点数组，第二个参数是这些事件顶点的个数
    public Vertex_compare(V[] v,short vexnum){
        this.v= Arrays.copyOf(v, vexnum);
    }
    @Override
    public int compare(Short o1, Short o2){
        /*
            参数是待排序事件顶点的下标
            排序规则是按事件的结束时间升序(结束时间较晚的放在后面)排序
            若两者的结束时间相等，则谁的开始时间更晚就放在后面
            若两者的开始时间和结束时间均相等，则二者相等
            注意：此 比较器 强行进行与 equals 不一致的排序。
        */
        if(!Dag.noLater(v[o2].end_time, v[o1].end_time)){
            return -1;
        }else if(v[o1].end_time.equals(v[o2].end_time)&&!Dag.noLater(v[o2].start_time, v[o1].start_time)){
            return -1;
        }else if(v[o1].end_time.equals(v[o2].end_time)&&v[o1].start_time.equals(v[o2].start_time)){
            return 0;
        }else   return 1;
    }
}
