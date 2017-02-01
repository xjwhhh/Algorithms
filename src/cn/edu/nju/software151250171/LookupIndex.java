package cn.edu.nju.software151250171;

/**
 * 索引及反向索引的查找
 * 这段数据驱动的符号表用例会从一个文件中读取键值对并根据标准输入中的键打印出相应的值
 * 其中键为字符串，值为一列字符串，分隔符由命令行参数指定
 * @author xjwhhh
 *
 */
public class LookupIndex {
	public static void main(String args[]){
		In in=new In(args[0]);
		String sp=args[1];
		ST<String,Queue<String>> st=new ST<String,Queue<String>>();
		ST<String,Queue<String>> ts=new ST<String,Queue<String>>();
		
		while(in.hasNextLine()){
			String[] a=in.readLine().split(sp);
			String key=a[0];
			for(int i=1;i<a.length;i++){
				String val=a[i];
				if(!st.contains(key)){
					st.put(key,new Queue<String>());
				}
				if(!ts.contains(val)){
					ts.put(key,new Queue<String>());
				}
				st.get(key).enqueue(val);
				ts.get(key).enqueue(key);
			}
		}
		
		while(!System.in.isEmpty()){
			String query=System.in.readLine();
			if(st.contains(query)){
				for(String s:st.get(query)){
					System.out.println(" "+s);
				}
			}
			if(ts.contains(query)){
				for(String s:ts.get(query)){
					System.out.println(" "+s);
				}
			}
		}
	}
}
