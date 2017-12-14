//package cn.edu.nju.software151250171.search.searchingapplications;
//
//import java.util.HashSet;
//
///**
// * 白名单，文件中的键被定义为好键，用例可以选择将所有不在白名单上的键传递到标准输出并忽略白名单上的所有键
// * 也可以选择只将所有在白名单上的键传递到标准输出并忽略所有不在白名单上的键
// * 黑名单则与之相反，名单上的所有键都被定义为坏键，也有两种自然的应用
// * @author xjwhhh
// *
// */
//public class WhiteFilter {
//	public static void main(String args[]){
//		HashSet<String> set;
//		set=new HashSet<String>();
//		In in=new In(args[0]);
//		while(!in.isEmpty()){
//			set.add(in.readingString());
//		}
//		while(!System.in.isEmpty()){
//			String word=System.in.readString();
//			if(set.contains(word)){
//				System.out.print(word+" ");
//			}
//		}
//	}
//}
