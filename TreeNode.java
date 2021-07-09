package PriorityMinHeapBinaryTree;

/**
 * COSC 2P03 - Assignment 3
 * Name: Shubham N. Amrelia
 * Student ID: 6846877
 * Date: November 13th, 2020
 */

public class TreeNode {
    int item;
    int counter;
    int index;
    TreeNode leftChild;
    TreeNode rightChild;

    public TreeNode(int item) {
        this.item = item;
        counter = 0;
        index = 1;
        leftChild = null;
        rightChild = null;
    }


}
