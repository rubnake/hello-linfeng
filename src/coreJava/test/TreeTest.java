package coreJava.test;

import coreJava.algorithm.MyTree;

public class TreeTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MyTree<Integer> tree = new MyTree<Integer>();
		tree.init();
		System.out.println("�������");
		tree.nrPreOrderTraverse();
		System.out.println("�������");
		tree.nrInOrderTraverse();
		System.out.println("��������");
		tree.nrPostOrderTraverse();
		
//		System.out.println("�������");
//		tree.inOrderTraverse();
//		tree.nrInOrderTraverse();
//		System.out.println("��������");
//		tree.postOrderTraverse();
//		tree.nrPostOrderTraverse();
//		System.out.println("�������");
//		tree.preOrderTraverse();
//		tree.nrPreOrderTraverse();

		//
	}

}
