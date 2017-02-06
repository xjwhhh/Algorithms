package cn.edu.nju.software151250171.search;

/*
 * 矩阵与向量的乘法，计算所需空间非常巨大
 * 幸好，矩阵通常是稀疏的，可以将矩阵表示为由稀疏向量组成的一个数组
 * 使用a[i].put(j,val)来表示矩阵中的值并使用a[i].get(j)来获取它
 */
public class SparseVector {
	private ST<Integer, Double> st;  //本来是用HashST的
	
	public SparseVector(){
		st = new ST<Integer, Double>();
	}
	
	public int size(){
		return st.size();
	}
	
	public void put(int i, double x){
		st.put(i,x);
	}
	
	public double get(int i){
		if(!st.contains(i)){
			return 0.0;
		}
		else{
			return st.get(i);
		}
	}
	
	public double dot(double[] that){
		double sum=0.0;
		for(int i: st.keys()){
			sum+=that[i]*this.get(i);
		}
		return sum;
	}
}
