package com.ni.algorithm;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @ClassName AlgorithmTree
 * @Description 二叉树
 * @Author zihao.ni
 * @Date 2023/7/2 11:05
 * @Version 1.0
 **/
public class AlgorithmTree {
    /**
     * 遍历顺序
     */
    private static List<Integer> list = new ArrayList<Integer>();

    // 从物理结构的角度来看，树是一种基于链表的数据结构，因此其遍历方式是通过指针逐个访问节点。
    // 然而，树是一种非线性数据结构，这使得遍历树比遍历链表更加复杂，需要借助搜索算法来实现。
    // 二叉树常见的遍历方式包括层序遍历、前序遍历、中序遍历和后序遍历等。
    public static void main(String[] args) {
        AlgorithmTree algorithmTree = new AlgorithmTree();
        TreeNode treeNode = algorithmTree.create();

        System.out.println(level_order(treeNode));

        preOrder(treeNode);
        System.out.println(list);

        list.clear();
        inOrder(treeNode);
        System.out.println(list);

        list.clear();
        postOrder(treeNode);
        System.out.println(list);

    }

    /**
     * 创建二叉树
     */
    public TreeNode create() {
        TreeNode treeNode1 = new TreeNode(1);
        TreeNode treeNode2 = new TreeNode(2);
        TreeNode treeNode3 = new TreeNode(3);
        TreeNode treeNode4 = new TreeNode(4);
        TreeNode treeNode5 = new TreeNode(5);
        TreeNode treeNode6 = new TreeNode(6);
        TreeNode treeNode7 = new TreeNode(7);

        treeNode1.setLeft(treeNode2);
        treeNode1.setRight(treeNode3);
        treeNode2.setLeft(treeNode4);
        treeNode2.setRight(treeNode5);
        treeNode3.setLeft(treeNode6);
        treeNode3.setRight(treeNode7);

        return treeNode1;
    }

    /**
     * 层序遍历
     * 从顶部到底部逐层遍历二叉树，并在每一层按照从左到右的顺序访问节点。
     *
     * @param
     */
    public static List<Integer> level_order(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList() {{
            add(root);
        }};
        ArrayList<Integer> list = new ArrayList<>();
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll(); // 队列出队
            list.add(node.getValue());           // 保存节点值
            if (node.hasLeft())
                queue.offer(node.getLeft());   // 左子节点入队
            if (node.hasRight())
                queue.offer(node.getRight());  // 右子节点入队
        }
        return list;
    }

    /**
     * 前序优先遍历, root -> left -> right
     *
     * @param node
     * @return
     */
    public static void preOrder(TreeNode node) {
        list.add(node.getValue());
        if (node.hasLeft()) preOrder(node.getLeft());
        if (node.hasRight()) preOrder(node.getRight());
    }

    /**
     * 中序遍历 , left -> root -> right
     *
     * @param node
     */
    public static void inOrder(TreeNode node) {
        if (node.hasLeft()) inOrder(node.getLeft());
        list.add(node.getValue());
        if (node.hasRight()) inOrder(node.getRight());
    }

    /**
     * 后序遍历, right -> left -> root
     */
    public static void postOrder(TreeNode node) {
        if (node.hasLeft()) postOrder(node.getLeft());
        if (node.hasRight()) postOrder(node.getRight());
        list.add(node.getValue());
    }
}


class TreeNode {
    private int value;
    private TreeNode left;
    private TreeNode right;

    public TreeNode(int value, TreeNode left, TreeNode right) {
        this.value = value;
        this.left = left;
        this.right = right;
    }

    public TreeNode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public TreeNode getLeft() {
        return left;
    }

    public void setLeft(TreeNode left) {
        this.left = left;
    }

    public TreeNode getRight() {
        return right;
    }

    public void setRight(TreeNode right) {
        this.right = right;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    public boolean hasLeft() {
        return left != null;
    }

    public boolean hasRight() {
        return right != null;
    }
}