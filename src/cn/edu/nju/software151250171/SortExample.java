package cn.edu.nju.software151250171;

public class SortExample {
	//默认排序的数据结构都是实现了comparable接口的	
	public static void sort(Comparable[] a){
		
	}
	
	//比较大小
	private static boolean less(Comparable v,Comparable w){
		return v.compareTo(w)<0;
	}
	
	//交换位置
	private static void exch(Comparable[] a,int i,int j){
		Comparable t=a[i];
		a[i]=a[j];
		a[j]=t;
	}
	
	//打印在一行里
	private static void show(Comparable[] a){
		for(int i=0;i<a.length;i++){
			System.out.print(a[i]+" ");
		}
		System.out.println(" ");
	}
	
	//是否排序
	public static boolean isSorted(Comparable[] a){
		for(int i=0;i<a.length;i++){
			if(less(a[i], a[i-1])){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 选择排序
	 * 找到数组中最小的元素，将它与数组的第一个元素交换位置（如果第一个元素就是最小元素那么他与自己交换）
	 * 在剩下的元素中找到最小元素，将它与数组的第二个元素交换位置
	 * 如此往复，直到将整个数组排序
	 * 运行时间与输入无关；数据移动是最少的
	 */
	public void selectSort(Comparable[] a){
		int n=a.length;
		for(int i=0;i<n;i++){
			int min=i;
			for(int j=i;j<n;j++){
				if(less(a[j], a[min])){
					min=j;//最小元素的下标
				}
			}
			exch(a, i, min);
		}
	}
	
	/**
	 * 插入排序
	 * 为了给要插入的元素腾出空间，我们需要将其余所有元素在插入之前都向右移动一位
	 * 在索引i由左向右变化的的过程中，它左侧的元素总是有序的
	 * 插入排序所需的时间取决于输入中元素的初始顺序
	 */
	public void insertSort(Comparable[] a){
		int n=a.length;
		//升序排序
		//将a[i]插入到之前的i个元素中
		for(int i=1;i<n;i++){
			for(int j=i;j>0&&less(a[j],a[j-1]);j--){
				exch(a, j, j-1);
			}
		}
	}

	/**
	 * 希尔排序
	 * 改进了插入排序，交换不相邻的元素以对数组的局部进行排序，并最终用插入排序将局部有序的数组排序
	 * 使数组中任意间隔为h的元素都是有序的，h有序数组
	 * 权衡了子数组的规模和有序性，可用于大型数组
	 */
	public void shellSort(Comparable[] a){
		//升序排列
		int n=a.length;
		int h=1;
		while(h<n/3){
			h=3*h+1;//找到最大的间隔h，递增序列的选择是不一定的
		}
		while(h>=1){
			//数组变为h有序
			for(int i=h;i<n;i++){
				//将a[i]插入到a[i-h],a[i-2h],a[i-3h]...之中
				for(int j=i;j>=h&&less(a[j],a[j-h]);j-=h){
					exch(a, j, j-h);
				}
			}
			h/=3;
		}
	}
	
	/**
	 * 归并排序
	 * 要将一个数组排序，可以先（递归地）将它分成两半分别排序，然后将结果归并起来
	 * 能够保证任意长度为n的数组排序时间与nlogn成正比
	 * 它所需的额外空间与n成正比
	 */
	//原地归并
	public void mergeSort1(Comparable[] a,int low,int mid,int high){
		//将a[low...mid]与a[mid...high]归并
		
		//辅助数组，可以是在调用这个方法的函数中一次性创建，避免每次创建，节约空间
		Comparable[] aux=new Comparable[high-low+1];
		int i=low;
		int j=mid+1;
		
		for(int k=low;k<=high;k++){
			aux[k]=a[k];
		}
		
		for(int k=low;k<=high;k++){
			//左半边元素用尽（取右半边元素）
			if(i>mid){
				a[k]=aux[j++];
			}
			//右半边元素用尽（取左半边元素）
			else if(j>high){
				a[k]=aux[i++];
			}
			//右半边当前元素小于左半边当前元素（取右半边元素）
			else if(less(aux[j],aux[i])){
				a[k]=aux[j++];
			}
			//右半边当前元素大于等于左半边当前元素（取左半边元素）
			else{
				a[k]=aux[i++];
			}
		}
	}
	
	/**
	 * 自顶向下的归并排序
	 * 分治思想
	 */
	public void mergeSort2(Comparable[] a,int low,int high){
		if(low>=high){
			return;
		}
		int mid=low+(high-low)/2;
		mergeSort2(a, low, mid);//左半边排序
		mergeSort2(a, mid+1, high);//右半边排序
		mergeSort1(a, low, mid, high);
	}
	
	/**
	 * 自底向上的归并排序
	 * 先归并微型数组，然后再成对归并得到的子数组
	 * 多次遍历数组
	 * 最后一个子数组的大小只有在数组大小是sz的偶数倍的时候才会等于sz，否则比sz小
	 * 适合用链表组织的数据，只需要重新组织链表链接就能将链表原地排序（不需要创建任何新的链表节点）
	 */
	public void mergeSort3(Comparable[] a){
		int n=a.length;
		for(int sz=1;sz<n;sz*=2){//sz为数组大小，每次加倍
			for(int low=0;low<n-sz;low+=sz*2){//low为子数组索引
				mergeSort1(a, low, low+sz-1, Math.min(low+2*sz-1, n-1));
				//最后一次归并的第二个子数组可能比第一个子数组要小，如果不是的话所有的归并中两个数组大小相同，而在下一轮中子数组的大小会翻倍
			}
		}
	}
	/*当数组长度为2的幂时，自顶向下和自底向上的归并排序所用的比较次数和数组访问次数正好
	相同，只是顺序不同。其他时候，这两种方法的比较和数组访问的次序会有所不同*/
	
	/**
	 * 快速排序
	 * 原地排序（只需要一个很小的辅助栈），且将长度为n的数组排序所需的时间和nlogn成正比
	 * 非常脆弱，在实现中要非常小心才能避免低劣的性能
	 * 当两个子数组分别有序时整个数组也就自然有序了
	 */
	public void quickSort(Comparable[] a,int low,int high){
		if(high<=low){
			return;
		}
		int j=partition(a,low,high);//快速排序的切分
		quickSort(a, low, j-1);//左半部分快速排序
		quickSort(a, j+1, high);//右半部分快速排序
	}
	
	public int partition(Comparable[] a,int low,int high){
		//将数组切分为a[low...i-1],a[i],a[i+1...high]
		int i=low;
		int j=high+1;//左右扫描指针
		Comparable v=a[low];//切分元素
		
		/*从数组左端开始向右扫描直到找到一个大于等于切分元素的元素，再从数组右端开始向左扫描直到找到一个小于等于切分元素的元素
		这两个元素显然没有排定，交换它们的位置。如此继续，我们可以保证左指针i的左侧元素都不大于切分元素，右指针j的右侧元素都不小于切分元素。*/
		while(true){
			while(less(a[++i], v)){
				if(i==high){
					break;
				}
			}
			while(less(v, a[--j])){
				if(j==low){
					break;
				}
			}
			if(i>=j){
				break;
			}
			exch(a, i, j);
		}

		//当两个指针相遇时，我们只需要将切分元素和左子数组最右侧的元素交换然后返回j即可
		exch(a, low, j);
		
		return j;
	}
	
	/**
	 * 三向切分的快速排序
	 * 将数组分为三部分，分别对应小鱼，等于和大于切分元素的数组元素
	 * 将和切分元素相等的元素归位，不会被包含在递归调用处理的子数组之中
	 * 三向切分是信息量最优的
	 */
	public void quick3Way(Comparable[] a,int low,int high){
		if(high<=low){
			return;
		}
		int lt=low,i=low+1,gt=high;
		Comparable v=a[low];
		while(i<=gt){
			int cmp=a[i].compareTo(v);
			if(cmp<0){
				exch(a, lt++, i++);
			}
			else if(cmp>0){
				exch(a, i, gt--);
			}
			else{
				i++;
			}
		}//保证a[low...lt-1]<v=a[lt...gt]<a[gt+1...high]
		quick3Way(a, low, lt-1);
		quick3Way(a, gt+1, high);
	}
	
}
