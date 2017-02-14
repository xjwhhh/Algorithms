package cn.edu.nju.software151250171.search.searchingapplications;

import cn.edu.nju.software151250171.search.symboltables.ST;

/*
 * 字典的查找
 * 根据命令行指定的文件中的数据构建了一组键值对，并会打印出由标准输入读取的键对应的值
 * 命令行参数包括一个文件名和两个整数，分别用来指定键和值所在的位置
 */
public class LookupCSV {
	public static void main(String[] args){
		In in=new In(args[0]);
		int keyField=Integer.parseInt(args[1]);
		int valField=Integer.parseInt(args[2]);
		ST<String,String> st=new ST<String,String>();
		
		while(in.hasNextLine()){
			String line=in.readLine();
			String[] tokens=line.split("，");
			String key=tokens[keyField];
			String val=tokens[valField];
			st.put(key,val);
		}
		
		while(!System.in.isEmpty){
			String query=System.in.readString();
			if(st.contains(query)){
				System.out.println(st.get(query));
			}
		}
	}
}
