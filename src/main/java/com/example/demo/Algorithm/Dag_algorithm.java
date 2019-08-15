package com.example.demo.Algorithm;

/**
 *
 * @author 于鑫舟 时光最优解算法部分
 */
public class Dag_algorithm {
    
    public static void main(String[] args) {
        IO e=new IO((short)10); //建了10个事件顶点，保存在nodedata.txt里
        Dag dag=new Dag(e.addVex());    //通过刚才那些事件顶点创建一个有向无环图
        dag.print();    //打印有向无环图的邻接表，保存在output.txt里
    }
    
}
