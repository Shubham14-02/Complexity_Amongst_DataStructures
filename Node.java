package PriorityLinkedList;

/**
 * COSC 2P03 - Assignment 3
 * Name: Shubham N. Amrelia
 * Student ID: 6846877
 * Date: November 13th, 2020
 */

/**
 * Node for priority queue linked list.
 */

public class Node {
    //Node stores integer from the input file.
    public int item;
    Node next;

    public Node(int number) {
        this.item = number;
        next = null;
    }
}
