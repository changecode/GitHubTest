package com.java.arch;

/**
 * 简单的栈示例
 * @author Tim
 *
 */
public class StackTest {

	public Node head;
	public Node current;

	public static void main(String[] args) {
		StackTest stack = new StackTest();
		stack.push(11);
		stack.push(12);
		stack.push(13);
		System.out.println(stack.pop().data);
		System.out.println(stack.pop().data);
		System.out.println(stack.pop().data);
	}

	public void push(int data) {
		if (head == null) {
			head = new Node(data);
			current = head;
		} else {
			Node node = new Node(data);
			node.pre = current;
			current = node;

		}
	}

	public Node pop() {
		if (current == null) {
			return null;
		}
		Node node = current;
		current = node.pre;
		return node;
	}
}


class Node {
	Node pre;
	int data;

	public Node(int data) {
		this.data = data;
	}
}