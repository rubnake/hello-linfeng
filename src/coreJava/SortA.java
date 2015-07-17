package coreJava;

public class SortA {

	public static void SelectSort(int[] array){
		for (int i=0;i<array.length;i++){
			int pos = array[i];
			for (int j=i+1;j<array.length;j++){
				if (array[j]<pos){
					swap(array,i,j);
				}
			}
		}
	}
	public static int patition(int[] array,int p,int q){
		if (p>array.length-1||q>array.length-1||p>=q){
			return -1;
		}
		int pos = (p+q)/2;
		int key = array[pos];
		while (p<q){
			while (p<q&&array[q]>=key){
				q--;
			}
			swap(array,q,pos);
			pos = q;
			while (p<q&&array[p]<=key){
				p++;
			}
			swap(array,p,pos);
			pos=p;
		}
		return pos;
	}
	public static void quickSort(int[] array,int p,int q){
		if (p>array.length||q>array.length||p>q){
			return ;
		}
		int pos = patition(array, p, q);
		if (pos==-1){
			return ;
		}
		quickSort(array, pos+1, q);
		quickSort(array, p, pos-1);
		
	}
	public static void print(int array[]){
		for (int i=0;i<array.length;i++){
			System.out.print(array[i]+", ");
		}
		System.out.println();
	}
	
	public static void swap(int[] array,int i,int j){
		if (i>array.length-1||j>array.length-1){
			return ;
		}
		int p = array[i];
		array[i]=array[j];
		array[j]=p;
	}
	
	public static int BinarySearch(int[] array,int begin,int end,int m){
		if (begin>array.length-1||end>array.length-1||begin>end){
			return -1;
		}
		int pos = (begin+end)/2;
		if (array[pos]==m){
		}
		else if (array[pos]>m){
			return BinarySearch(array,begin,pos-1,m);
		}else{
			return BinarySearch(array,pos+1,end,m);
		}
		return pos;
	}
	
	public static void main(String[] args) {
		int[] array = {6,2,4,8,1,3,9};
		SortA.quickSort(array, 0, array.length-1);
		SortA.print(array);
		int pos =SortA.BinarySearch(array, 0, array.length-1, 5);
		System.out.println("pos is: "+pos);
	}
}
