package com.ni.algorithm;

/**
 * @ClassName AlgorithmLinkedList
 * @Description 链表
 * @Author zihao.ni
 * @Date 2023/6/14 18:36
 * @Version 1.0
 **/
public class AlgorithmLinkedList {
    //「链表 Linked List」是一种线性数据结构，
    // 其每个元素都是一个节点对象，各个节点之间通过指针连接，从当前节点通过指针可以访问到下一个节点。
    // 由于指针记录了下个节点的内存地址，因此无需保证内存地址的连续性，从而可以将各个节点分散存储在内存各处。

    public static void main(String[] args) {
        ListNode headNode = initLinkedList();

        print(headNode);

        insert(headNode, new ListNode(10));
        print(headNode);

        remove(headNode);
        print(headNode);

        System.out.println(access(headNode, 2).val);

        System.out.println(find(headNode, 10));
    }


    /**
     * 初始化链表 1 -> 3 -> 2 -> 5 -> 4
     *
     * @return 返回头节点
     */
    static ListNode initLinkedList() {
        ListNode listNodeA = new ListNode(1);
        ListNode listNodeB = new ListNode(3);
        ListNode listNodeC = new ListNode(2);
        ListNode listNodeD = new ListNode(5);
        ListNode listNodeE = new ListNode(4);
        listNodeA.next = listNodeB;
        listNodeB.next = listNodeC;
        listNodeC.next = listNodeD;
        listNodeD.next = listNodeE;
        return listNodeA;
    }

    /**
     * 从链表头节点遍历
     */
    static void print(ListNode n0) {
        ListNode n = n0;
        while (n != null) {
            System.out.print(n.val + ",");
            n = n.next;
        }
        System.out.println();
    }


    /**
     * 在链表的节点 n0 之后插入节点 P
     */
    static void insert(ListNode n0, ListNode p) {
        ListNode before = n0.next;
        n0.next = p;
        p.next = before;
    }

    /**
     * 删除链表的节点 n0 之后的首个节点
     */
    static void remove(ListNode n0) {
        n0.next = n0.next.next;
    }


    /**
     * 访问链表中索引为 index 的节点
     */
    static ListNode access(ListNode head, int index) {
        ListNode n = head;
        for (int i = 0; i < index; i++) {
            n = n.next;
        }
        return n;
    }

    /**
     * 在链表中查找值为 target 的首个节点
     */
    static int find(ListNode head, int target) {
        ListNode n = head;
        int index = 0;
        while (n != null) {
            if (n.val == target) {
                return index;
            }
            n = n.next;
            index++;
        }

        return -1;
    }
}


/**
 * 链表节点类
 */
class ListNode {
    // 节点值
    int val;
    // 指向下一节点的指针（引用）
    ListNode next;

    ListNode(int x) {
        val = x;
    }  // 构造函数
}