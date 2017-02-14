package cn.edu.nju.software151250171.graph.minimumspanningtrees;

/*
 * 带权重的边的数据类型
 */
public class Edge implements Comparable<Edge>{
	private int v;            //顶点一
	private int w;			  //顶点二
	private double weight;    //边的权重
	
	public Edge(int v,int w,double weight){
		this.v=v;
		this.w=w;
		this.weight=weight;
	}
	
	public double weight(){
		return weight;
	}
	
	public int either(){
		return v;
	}
	
	public int other(int vertex){
		if(vertex==v){
			return w;
		}
		else if(vertex==w){
			return v;
		}
		else{
			throw new RuntimeException("Inconsistent edge");
		}
	}
	
	public int compareTo(Edge that){
		if(this.weight()<that.weight){
			return -1;
		}
		else if(this.weight()>that.weight){
			return +1;
		}
		else{
			return 0;
		}
	}
	
	//对象的字符串表示
	public String toString(){
		return String.format("%d-%d %2f",v,w,weight());
	}
}
