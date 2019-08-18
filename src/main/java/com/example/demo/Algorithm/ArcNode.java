package com.example.demo.Algorithm;

/**
 *
 * @author 于鑫舟
 * 边11结点的实现
 */
public class ArcNode {
    public short adjvex;    //邻接点
    public ArcNode next;    //下一个边结点
    
    ArcNode(){
        super();
    }
    
    ArcNode(short adjvex,ArcNode next){
        super();
        this.adjvex=adjvex;
        this.next=next;
    }
}
