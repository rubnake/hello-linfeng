package coreJava.test;

import coreJava.algorithm.MyTree;

public class TreeTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MyTree<Integer> tree = new MyTree<Integer>();
		tree.init();
		System.out.println("先序遍历");
		tree.nrPreOrderTraverse();
		System.out.println("中序遍历");
		tree.nrInOrderTraverse();
		System.out.println("后续遍历");
		tree.nrPostOrderTraverse();
		
//		System.out.println("中序遍历");
//		tree.inOrderTraverse();
//		tree.nrInOrderTraverse();
//		System.out.println("后续遍历");
//		tree.postOrderTraverse();
//		tree.nrPostOrderTraverse();
//		System.out.println("先序遍历");
//		tree.preOrderTraverse();
//		tree.nrPreOrderTraverse();

		//
	}

}
