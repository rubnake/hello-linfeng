package algorithm.greedy.demo.entity;

import java.util.ArrayDeque;
import java.util.List;

/*
 * �������ù�ϵTree
 * 
 */
public class FunNode {

	public FunNode(String name,String argsIn,String argsOut) {
		this.funName = name;
		this.funcInArgs = argsIn;
		this.funcOutArgs = argsOut;
	}
	
	private String funName;
	private String funcInArgs;
	private String funcOutArgs;
	
	public List<FunNode> children;
	public FunNode parent;
	
	// ������ȱ��� depthOrder
	public void depthOrderVisit(FunNode root) {
		if (root==null){
			return ;
		}
		visitCurredNode(root);
		for (FunNode one : root.getChildren()) {
			depthOrderVisit(one);
		}
	}
	
	// ������ȱ��� depthOrder
	/*
	 * ʹ�� ջ�ṹ
	 * 
	 */
		public void depthOrderVisit2(FunNode root) {
			if (root==null){
				return ;
			}
			ArrayDeque stack = new ArrayDeque();
			stack.push(root);
			while (stack.size()>0){
				FunNode node = (FunNode) stack.pop();
				visitCurredNode(node);
				for (FunNode one : node.getChildren()) {
					stack.push(one);
				}
			}
			
		}

	// ������ȱ��� depthOrder
	/*
	 * ��Ҫһ����������  ArrayDeque{˳��}��LinkedList{����}
	 */
	public void levelOrderVisit(FunNode root) {
		if (root==null){
			return ;
		}
		ArrayDeque deque = new ArrayDeque();
		deque.add(root);
		while (deque.size()>0){
			FunNode node = (FunNode)deque.remove();
			visitCurredNode(node);
			for (FunNode one : node.getChildren()) {
				deque.add(one);
			}
		}
		
	}
	
	public void visitCurredNode(FunNode node){
		System.out.println(node);
	}
	
	public String toString(){
		return this.parent.getFunName()+"\t"+this.getFunName() + "\tinArgs\t" + this.getFuncInArgs()
				+ "\toutArgs\t" + this.funcOutArgs;
	}

	public void addChild(FunNode child){
		this.children.add(child);
	}
	
	public List<FunNode> getChildren() {
		return children;
	}
	public void setChildren(List<FunNode> children) {
		this.children = children;
	}
	public FunNode getParent() {
		return parent;
	}
	public void setParent(FunNode parent) {
		this.parent = parent;
	}

	public String getFunName() {
		return funName;
	}

	public String getFuncInArgs() {
		return funcInArgs;
	}

	public String getFuncOutArgs() {
		return funcOutArgs;
	}

	
	
	

}
