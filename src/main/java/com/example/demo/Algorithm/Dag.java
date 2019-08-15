package com.example.demo.Algorithm;
import java.util.List;
import java.util.regex.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.Math;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

/**
 *
 * @author 于鑫舟
 * 有向无环图的各种操作，整个图的存储结构是邻接表的结构
 */
public class Dag {
    public V[] v;  //顶点数组

    public ArrayList<V>vList=new ArrayList<>();
    public short vexnum;   //当前顶点数组的长度
    private short max_lvl;  //当前所有顶点最大的path_level(V.PATHMAX不算在内) 如果全是孤立点就默认为-1
    private File output;    //装有邻接表的文件
    public final static short VMAX=100;    //顶点数组的总大小
    public final static String EARLIST_TIME="00:00";    //默认的最早时间 
    public final static String LATEST_TIME="24:00";    //默认的最晚时间    
    //默认构造函数
    public Dag(){   
        super();
        this.output=new File("output.txt"); 
        //开辟一个V[VMAX]的顶点数组空间(数组大小可以看情况改)
        this.v=new V[VMAX];
        this.vexnum=0;
        this.max_lvl=-1;
    }    
    
    public Dag(V[] v){
        super();
        this.output=new File("output.txt"); 
        //开辟一个V[VMAX]的顶点数组空间(数组大小可以看情况改)
        this.v=new V[VMAX];
        this.vexnum=(short)v.length;
        //将准备好的事件顶点依次填进顶点数组准备初始化
        for(short u=0;u<vexnum;u++){
            this.v[u]=v[u];
        }      
        //遍历顶点数组，每一次循环都会建立顶点v[u]的出边(不主动建立v[u]的入边)
        for(short u=0;u<vexnum;u++){
            String min_end_time=EARLIST_TIME;    //v[u]当前所有出边邻接点的最早的结束时间
            for(short i=0;i<vexnum;i++){
                if(i==u)  continue;    //不跟自己比
                if(noLater(v[u].end_time,v[i].start_time)){   //建立v[u]的出边
                    //搜索当前v[u]的所有出边,只有在所有邻接点的时间均不早于v[i]的时间时才会插入(v[u],v[i])
                    boolean flag=true;  //代表是否插入这条边的标志
                    //只有在所有出边邻接点的时间均不早于v[i]的时间时才会插入(v[u],v[i])
                    if(v[u].out_degree==0){ //如果v[u]当前出度为零
                        min_end_time=v[i].end_time;
                    }else{
                        //只要有一个邻接顶点时间早于v[i]，就不插入(v[u],v[i])
                        if(noLater(min_end_time,v[i].start_time))   flag=false;
                    }
                    if(flag){
                        insertArc(u,i);
                        //根据v[i]来更新min_end_time
                        if(noLater(v[i].end_time,min_end_time)) min_end_time=v[i].end_time;
                    }
                }
            }
            //下面遍历v[u]当前的所有邻接点，如果发现有邻接点的开始时间不早于min_end_time，则把这个邻接点删去
            ArcNode arc=v[u].firstedge, prearc=null;
            while(arc!=null){
                if(noLater(min_end_time,v[arc.adjvex].start_time)){
                    if(arc==v[u].firstedge) v[u].firstedge=arc.next;
                    else    prearc.next=arc.next;
                    v[u].out_degree--; v[arc.adjvex].in_degree--;   //更新入度和出度
                }else   prearc=arc; arc=arc.next;
            }
        }
        //现在所有顶点的path_level均为最大值，现在开始依次为每个顶点的path_level赋值
        bfsPath();
    }
    //在有向边(v[i],v[k])存在的基础上插入有向边(v[i],v[j])和(v[j],v[k])
    private void insertArc(short i,short j,short k){
        ArcNode arc=v[i].firstedge; //arc是顶点v[i]的第一个边结点
        ArcNode prearc=new ArcNode();
        //将(v[i],v[k])拆成(v[i],v[j])和(v[j],v[k])，完成插入新的边
        //先删去(v[i],v[k])
        while(arc!=null){
            if(arc.adjvex==k){
                if(v[i].firstedge==arc) v[i].firstedge=arc.next;
                else   prearc.next=arc.next;
                v[i].out_degree--;v[k].in_degree--;//更新v[i]的出度，v[k]的入度
                break;
            }else{
                prearc=arc; arc=arc.next;
            }
        }
        insertArc(i,j);  //增加(v[i],v[j])
        insertArc(j,k);  //增加(v[j],v[k]);
    }
    //插入有向边(v[i],v[j])的函数
    private void insertArc(short i,short j){
        /*
            先检查一下v[i]的邻接点有没有v[j]
            如果邻接点有v[j]，则直接退出；没有则完成后续插入
        */
        ArcNode arc=v[i].firstedge,prearc=null;
        boolean flag=true;
        //先完成上述注释的搜索，找到相应边并删除
        while(arc!=null && flag){     
            if(arc.adjvex==j){
                flag=false;
            }else{
                arc=arc.next;
            }
        }
        if(!flag) return;//如果不用插边，就直接退出函数
        //现在开始找到一个合适的位置来添加边，遵循从链表头到尾顶点的结束时间越来越晚的原则来排序
        arc=v[i].firstedge; flag=true;
        short k;
        while(arc!=null && flag){
            k=arc.adjvex;
            //这样的优先标准是否合理，还需细想
            if(!noLater(v[k].end_time,v[j].end_time)){
                flag=false;
            }else if(v[k].end_time.equals(v[j].end_time) && !noLater(v[k].start_time,v[j].start_time)){
                flag=false;
            }else{
                prearc=arc; arc=arc.next;
            }
        }
        //开始添加(v[i],v[j])
        ArcNode newarc=new ArcNode(j,null); //生成一个v[j]的新边结点
        if(prearc==null){
            newarc.next=v[i].firstedge;
            v[i].firstedge=newarc;
        }else{
            newarc.next=prearc.next;
            prearc.next=newarc;
        }
        v[i].out_degree++; v[j].in_degree++;    //更新v[i]的入度，v[j]的出度
    }  
    /*
        删除一个事件顶点，随之调整Dag图的结构
        传进一个事件顶点的ID，返回删除的顶点，若指定ID找不到对应的顶点，则返回null
    */
    public V deleteVertex(String id){
        V target=null;
        short temp=0; 
        //找到id对应的事件顶点
        for(short i=0;i<vexnum;i++){
            if(id.equals(v[i].eId)){
                target=v[i]; temp=i; break;
            }
        }
        if(target==null)  return null;      
        //更新相应顶点的入度和出度，若有顶点出边的邻接点是target的话还要删除这个边结点
        for(short i=0;i<vexnum;i++){
            ArcNode arc=v[i].firstedge;
            ArcNode prearc=null;
            boolean flag=false; //v[i]的邻接点是否有target
            if(i==temp){    //此时v[i]为target           
                while(arc!=null){
                    v[arc.adjvex].in_degree--;
                    arc=arc.next;
                }
            }else{
                while(arc!=null&&!flag){
                    if(v[i].firstedge==arc){
                        if(arc.adjvex==temp){
                            v[i].firstedge=arc.next;
                            v[i].out_degree--;
                            flag=true;  //因为一个顶点不会向另外一个顶点同时引两条边
                        }
                    }else{
                        if(arc.adjvex==temp){
                            prearc.next=arc.next;
                            v[i].out_degree--;
                            flag=true;  //因为一个顶点不会向另外一个顶点同时引两条边
                        }
                    }
                    prearc=arc; arc=arc.next;
                }
                //如果v[i]的邻接点里有target，就更新v[i]的出边，看看是否会将target的出边添加进v[i]里
                if(flag){
                    ArcNode tar_arc=target.firstedge;
                    /*
                        开始遍历target的邻接点，只要这个邻接点的start_time比v[i]当前所有邻接点的end_time都要早，
                        就把这个邻接点插进v[i]的边表里
                        只需跟v[i]的firstedge比，因为v[i]的第一个邻接点就是end_time最早的那个邻接点
                    */
                    while(tar_arc!=null){
                        if(!noLater(v[v[i].firstedge.adjvex].end_time,v[tar_arc.adjvex].start_time)){
                            //现在开始找到一个合适的位置来添加边，遵循从链表头到尾顶点的结束时间越来越晚的原则来排序
                            //不用insertArc函数的原因是无需再检查v[i]是否本身有v[tar_arc.adjvex]的邻接点了
                            arc=v[i].firstedge; prearc=null; flag=true;
                            short j=tar_arc.adjvex,k;
                            while(arc!=null && flag){
                                k=arc.adjvex;
                                //这样的优先标准是否合理，还需细想
                                if(!noLater(v[k].end_time,v[j].end_time)){
                                    flag=false;
                                }else if(v[k].end_time.equals(v[j].end_time) && !noLater(v[k].start_time,v[j].start_time)){
                                    flag=false;
                                }else{
                                    prearc=arc; arc=arc.next;
                                }
                            }
                            //开始添加(v[i],v[j])
                            ArcNode newarc=new ArcNode(j,null); //生成一个v[j]的新边结点
                            if(prearc==null){
                                newarc.next=v[i].firstedge;
                                v[i].firstedge=newarc;
                            }else{
                                newarc.next=prearc.next;
                                prearc.next=newarc;
                            }
                            v[i].out_degree++; v[j].in_degree++;    //更新v[i]的入度，v[j]的出度
                        }
                        tar_arc=tar_arc.next;
                    }                 
                }               
            }
        }      
        //删除target，更新相关邻接边的顶点下标
        for(short i=temp;i<vexnum-1;i++)  v[i]=v[i+1];
        v[vexnum-1]=null;
        vexnum--;
        for(short i=0;i<vexnum;i++){
            ArcNode arc=v[i].firstedge;
            while(arc!=null){
                if(arc.adjvex>temp) arc.adjvex--;
                arc=arc.next;
            }
        }
        //最后广度优先遍历来重新确定各个顶点的路径
        for(short i=0;i<vexnum;i++) v[i].path_level=V.PATHMAX;  //初始化
        bfsPath();
        return target;     
    }
    /*
        插入一个时间顶点，随即调整Dag图的结构
        如果插入顶点失败，则返回false；成功则返回true
    */
    public boolean addVertex(V target){
        if(VMAX < vexnum+1) return false;   //插入顶点后会超出数组的范围，返回false
        //插入顶点
        v[vexnum]=target;
        vexnum++;
        //先建立target的出边，vexnum减1的目的是因为不和target自身比
        String min_end_time=EARLIST_TIME;    //最早的结束时间
        short target_index=(short)(vexnum-1);   //target在顶点数组的下标
        for(short i=0;i<vexnum-1;i++){
            if(noLater(target.end_time,v[i].start_time)){   //建立v[u]的出边
                //搜索当前target的所有出边,只有在所有邻接点的时间均不早于v[i]的时间时才会插入(target,v[i])
                boolean flag=true;  //代表是否插入这条边的标志
                //只有在所有出边邻接点的时间均不早于v[i]的时间时才会插入(v[u],v[i])
                if(target.out_degree==0){ //如果v[u]当前出度为零
                    min_end_time=v[i].end_time;
                }else{
                    //只要有一个邻接顶点时间早于v[i]，就不插入(v[u],v[i])
                    if(noLater(min_end_time,v[i].start_time))   flag=false;
                }
                if(flag){
                    insertArc(target_index,i); //vexnum-1即是target在顶点数组中的下标
                    //根据v[i]来更新min_end_time
                    if(noLater(v[i].end_time,min_end_time)) min_end_time=v[i].end_time;
                }
            }
        }
        //下面遍历target当前的所有邻接点，如果发现有邻接点的开始时间不早于min_end_time，则把这个邻接点删去
        ArcNode arc=target.firstedge, prearc=null;
        while(arc!=null){
            if(noLater(min_end_time,v[arc.adjvex].start_time)){
                if(arc==target.firstedge) target.firstedge=arc.next;
                else    prearc.next=arc.next;
                target.out_degree--; v[arc.adjvex].in_degree--;   //更新入度和出度
            }else   prearc=arc; arc=arc.next;            
        }
        //target的出边建立完毕，现在访问其他顶点去建立target的入边
        //不访问v[vexnum-1]，因为v[vexnum-1]即是target
        for(short i=0;i<vexnum-1;i++){
            arc=v[i].firstedge;
            if(noLater(v[i].end_time,target.start_time)){
                if(arc==null){  //直接插边
                    v[i].firstedge=new ArcNode(target_index,null);
                    v[i].out_degree++;  target.in_degree++; //更新入度和出度
                }else{
                    min_end_time=v[arc.adjvex].end_time;    //v[i]的第一个邻接点的end_time肯定是最小的那个，根据插边原则
                    if(!noLater(min_end_time,target.start_time)){   //满足这个条件则可以插边
                        //插边之前先根据情况删去v[i]本身的一些邻接边
                        while(arc!=null){
                            if(noLater(target.end_time,v[arc.adjvex].start_time)){
                                if(arc==v[i].firstedge) v[i].firstedge=arc.next;
                                else    prearc.next=arc.next;
                                v[i].out_degree--; v[arc.adjvex].in_degree--;   //更新入度和出度
                            }else   prearc=arc; arc=arc.next;            
                        }
                        insertArc(i,target_index);  //插边
                    }
                }
            }
        }
        //所有的边都调整好以后，最后重新确定各个顶点的path_level
        for(short i=0;i<vexnum;i++) v[i].path_level=V.PATHMAX;  //初始化
        bfsPath();
        return true;
    }
    /*
        修改一个事件顶点的时间，传进的参数有目标事件顶点的id,修改后的开始时间及结束时间
        返回修改之后的这个事件顶点，若目标id不存在则返回null
    */
    public V modifyTime(String id,String start_time,String end_time){
        //首先是要从顶点数组里移除这个即将修改的顶点，操作跟deleteVertex方法是一样的
        //但是省去了一步广度优先遍历，因为重新插入顶点之后还要再广度优先遍历，避免重复
        if(noLater(end_time,start_time))  return null;  //修改后的结束时间肯定要比开始时间晚
        V target=null;
        short temp=0; 
        for(short i=0;i<vexnum;i++){
            if(id.equals(v[i].eId)){
                target=v[i]; temp=i; break;
            }
        }
        if(target==null)  return null; 
        for(short i=0;i<vexnum;i++){
            ArcNode arc=v[i].firstedge;
            ArcNode prearc=null;
            boolean flag=false; 
            if(i==temp){              
                while(arc!=null){
                    v[arc.adjvex].in_degree--;
                    arc=arc.next;
                }
            }else{
                while(arc!=null&&!flag){
                    if(v[i].firstedge==arc){
                        if(arc.adjvex==temp){
                            v[i].firstedge=arc.next;
                            v[i].out_degree--;
                            flag=true;  
                        }
                    }else{
                        if(arc.adjvex==temp){
                            prearc.next=arc.next;
                            v[i].out_degree--;
                            flag=true;  
                        }
                    }
                    prearc=arc; arc=arc.next;
                }
                if(flag){
                    ArcNode tar_arc=target.firstedge;
                    while(tar_arc!=null){
                        if(!noLater(v[v[i].firstedge.adjvex].end_time,v[tar_arc.adjvex].start_time)){
                            arc=v[i].firstedge; prearc=null; flag=true;
                            short j=tar_arc.adjvex,k;
                            while(arc!=null && flag){
                                k=arc.adjvex;
                                if(!noLater(v[k].end_time,v[j].end_time)){
                                    flag=false;
                                }else if(v[k].end_time.equals(v[j].end_time) && !noLater(v[k].start_time,v[j].start_time)){
                                    flag=false;
                                }else{
                                    prearc=arc; arc=arc.next;
                                }
                            }
                            ArcNode newarc=new ArcNode(j,null); 
                            if(prearc==null){
                                newarc.next=v[i].firstedge;
                                v[i].firstedge=newarc;
                            }else{
                                newarc.next=prearc.next;
                                prearc.next=newarc;
                            }
                            v[i].out_degree++; v[j].in_degree++;    
                        }
                        tar_arc=tar_arc.next;
                    }                 
                }               
            }
        }      
        for(short i=temp;i<vexnum-1;i++)  v[i]=v[i+1];
        v[vexnum-1]=null;
        vexnum--;
        for(short i=0;i<vexnum;i++){
            ArcNode arc=v[i].firstedge;
            while(arc!=null){
                if(arc.adjvex>temp) arc.adjvex--;
                arc=arc.next;
            }
        }     
        //现在要改target的开始时间和结束时间了
        target.start_time=start_time;   target.end_time=end_time;
        //把target事件顶点的入度和出度置为零
        target.in_degree=0; target.out_degree=0;
        target.firstedge=null;
        //开始重新插入该顶点，这个顶点会放置在顶点数组的末尾
        addVertex(target);
        return target;
    }
    //通过广度优先遍历来确定Dag图中各个顶点的路径
    private void bfsPath(){
        this.max_lvl=-1;    //初始化
        boolean[] visited=new boolean[vexnum];  //visited[i]代表v[i]是否被访问了
        for(short i=0;i<vexnum;i++) visited[i]=false;
        /*
            Dag图中空闲的地方，可以让顶点插进去(不包括最右边界)
            由二维动态数组构成，为简要说明用idle_boundary[i][j]的形式来说
            i的最大长度代表Dag图中最大的路径
            idle_boundary[i][j]和idle_boundary[i][j+1](j为偶数)构成了path_level=i的路径上其中一个范围，可以让顶点插进去
            每个idle_boundary[i][j]都存放对应的事件顶点的下标
        */
        ArrayList<ArrayList<Short>> idle_boundary=new ArrayList<>();
        //当前所有路径的最右边界，right_path_borders[i]代表的是v[right_path_borders[i]]的end_time是path_level=i的路径的最右边界
        short[] right_path_borders=new short[vexnum]; 
        //现在找出所有入度为0的非孤立点(起点)，来初步定一下它们的path_level
        for(short i=0;i<vexnum;i++){
            if(v[i].in_degree==0&&v[i].out_degree>0){
                max_lvl++; right_path_borders[max_lvl]=i; 
                idle_boundary.add(new ArrayList<Short>());
            }
        }
        //给这些起点根据插边的时间原则冒泡排序(本身起点的个数就少的可怜，用冒泡也无所谓)
        for(short i=0;i<max_lvl;i++){
            short temp,a,b;
            for(short j=0;j<max_lvl-i;j++){
              a=right_path_borders[j]; b=right_path_borders[j+1];
              //这样的优先标准是否合理，还需细想
              if(!noLater(v[a].end_time,v[b].end_time)){
                  temp=a; right_path_borders[j]=b; right_path_borders[j+1]=temp;
              }else if(v[a].end_time.equals(v[b].end_time) && !noLater(v[a].start_time,v[b].start_time)){
                  temp=a; right_path_borders[j]=b; right_path_borders[j+1]=temp;
              }
            }
        }
        LinkedList<Short> vertexes=new LinkedList();  //声明一个队列，存放即将遍历的顶点的下标
        for(short i=0;i<=max_lvl;i++){
            v[right_path_borders[i]].path_level=i;    //直接赋path_level
            vertexes.add(right_path_borders[i]);  //加进队列里
        }
        //广度优先遍历，给顶点path_level
        Short bfs=vertexes.poll();
        while(bfs!=null){
            if(!visited[bfs]){  //这个顶点尚未被访问
                visited[bfs]=true;  //代表已经被访问了
                ArcNode arc=v[bfs].firstedge;
                while(arc!=null){   //把v[bfs]的所有邻接点有顺序地放进队列里
                    vertexes.add(arc.adjvex);
                    arc=arc.next;
                }
                if(v[bfs].in_degree==0) {
                    bfs=vertexes.poll();    //接着从队列弹出一个顶点
                    continue;   //这个点为起点，不用再赋path_level
                }   
                short temp;
                for(temp=0;temp<=max_lvl;temp++){
                    ArrayList<Short> temp_boundary=idle_boundary.get(temp);
                    if(noLater(v[right_path_borders[temp]].end_time,v[bfs].start_time)){  //在这个path_level上没有与最右边界有交集
                        //增加一组范围
                        temp_boundary.add(right_path_borders[temp]);
                        temp_boundary.add(bfs);
                        right_path_borders[temp]=bfs; //重新定义最右边界
                        v[bfs].path_level=temp; 
                        break;
                    }else{
                        //开始依次遍历path_level=temp中空闲的地方，看看这个顶点能不能插进去
                        boolean flag=false;
                        int temp_index=-1;
                        for(int i=0;i<temp_boundary.size();i+=2){
                            //若能插进去
                            if(noLater(v[temp_boundary.get(i)].end_time,v[bfs].start_time)&&noLater(v[bfs].end_time,v[temp_boundary.get(i+1)].start_time)){
                                flag=true;
                                temp_index=i;   break;
                            }
                        }
                        if(flag){   
                            //更新空闲的范围
                            temp_boundary.set(temp_index, bfs);
                            temp_boundary.add(bfs); temp_boundary.add(temp_boundary.get(temp_index+1));
                            v[bfs].path_level=temp; 
                            break;
                        }
                    }
                }
                if(temp>max_lvl){ 
                    v[bfs].path_level=temp;
                    right_path_borders[temp]=bfs;   //重新定义最右边界
                    idle_boundary.add(new ArrayList<Short>());  //增加path_level=temp的空闲范围集合
                    max_lvl++;
                }
            }
            bfs=vertexes.poll();    //接着从队列弹出一个顶点
        }        
    }
    /*  比较a时间和b时间的早晚，如果a时间<=b时间，
        则返回true，反之返回false
        暂且令时间格式为HH:HH 如11:30 23:59
    */
    public static boolean noLater(String a,String b){ 
        int ah=0,am=0,bh=0,bm=0;
        String pattern="(\\d*)\\:(\\d*)";
        Pattern r=Pattern.compile(pattern);
        Matcher m;
        m=r.matcher(a);
        if(m.find()){
            ah=Integer.parseInt(m.group(1));    //a的小时
            am=Integer.parseInt(m.group(2));    //a的分钟
        }
        m=r.matcher(b);
        if(m.find()){
            bh=Integer.parseInt(m.group(1));    //b的小时
            bm=Integer.parseInt(m.group(2));    //b的分钟
        }
        if(ah<bh)   return true;
        else if(ah>bh)  return false;
        else{
            if(am<=bm)  return true;
            else return false;
        }
    }
    //打印当前Dag的邻接表
    public ArrayList<V> print(){
       try{
            ArcNode arc;
            if(this.output.exists())  this.output.delete();//若有该文件则删除
            this.output.createNewFile();
            BufferedWriter writer=new BufferedWriter(new FileWriter(this.output));
            writer.write("顶点表: 顶点ID  开始时间  结束时间  入度  出度  哪条路径");
            writer.newLine();
            writer.write("边表: 邻接点ID");
            writer.newLine();
            for(int i=0;i<vexnum;i++){
                vList.add(v[i]);
                writer.write(v[i].eId+" "+v[i].start_time+" "+v[i].end_time+" "+v[i].in_degree+" "+v[i].out_degree+" "+v[i].path_level);
                arc=v[i].firstedge;
                while(arc!=null){
                    writer.write("  ->  "+v[arc.adjvex].eId);
                    arc=arc.next;
                }
                writer.newLine();
            }
            writer.flush();
            writer.close();
            return  vList;
       }catch(IOException e){
           e.printStackTrace();
           return vList;
       }
    }
}
