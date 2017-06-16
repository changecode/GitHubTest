package com.java.arch;

/**
 * 简单的队列示例
 * 
 * @author Tim
 *
 */
public class QueueTest {

	public Node1 head;
	public Node1 curent;

	public void add(int data) {
		if (head == null) {
			head = new Node1(data);
			curent = head;
		} else {
			curent.next = new Node1(data);
			curent = curent.next;
		}
	}

	// 方法：出队操作
	public int pop() throws Exception {
		if (head == null) {
			throw new Exception("队列为空");
		}

		Node1 node = head; // node结点就是我们要出队的结点
		head = head.next; // 出队之后，head指针向下移
		return node.data;

	}

}

class Node1 {
	Node1 next;
	int data;

	public Node1(int data) {
		this.data = data;
	}
}
