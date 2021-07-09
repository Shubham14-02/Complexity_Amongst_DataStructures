package PriorityLinkedList;

import java.util.*;
import java.io.*;

/**
 * COSC 2P03 - Assignment 3
 * Name: Shubham N. Amrelia
 * Student ID: 6846877
 * Date: November 13th, 2020
 */

public class PriorityQueueLinkedList {

    //In this priority queue, we remove from the front and insert from the rear
    //The front node has the smallest elements and rear has the bigger ones.
    //A pointer that points to front of the linked list
    private Node front;
    private static final int RANDOM_SEED = 87877;
    private static final Random randomNumber = new Random(RANDOM_SEED);
    private static final int min = 1;
    private static final int max = 1001;

    public PriorityQueueLinkedList() {
        front = null;
    }

    //Create a array of random numbers between 1 to 1001
    public static int[] getRanArr(int n) {
        int[] r = new int[n];
        //Random numbers between 1 to 1001
        for (int i = 0; i < n; i++) {
            r[i] = randomNumber.nextInt(max - min) + min;
        }
        return r;
    }

    //The function takes input from the file and returns an array of integers.
    private static int[] getInputArray() {
        try {
            Scanner sc = new Scanner(new File("src/assn3in.txt"));
            //read the first number to be the size of queue
            int size = sc.nextInt();
            int[] inputArray = new int[size];
            //read the rest of the numbers to be the list of numbers
            while (sc.hasNext()) {
                for (int i = 0; i < inputArray.length; i++) {
                    int number = sc.nextInt();
                    inputArray[i] = number;
                }
            }
            return inputArray;
        } catch (Exception e) {
            System.out.println("'src/assn3in.txt' file not found! ");
        }
        return new int[0];
    }


    //A function that inserts numbers into priority queue in a sorted way
    private void insert(int number) {
        Node node = new Node(number);
        Node tempP;
        //if queue is empty or if the number to be inserted is smaller than the front element of queue,
        //insert into the front of the priority queue,
        if (queueIsEmpty() || isSmallerNumber(number)) {
            node.next = front;
            front = node;
        } else {
            //traverse the queue to find the position where new item <= old item
            tempP = front;
            while (tempP.next != null && number > tempP.next.item) {
                tempP = tempP.next;
            }
            //then insert to that position
            node.next = tempP.next;
            tempP.next = node;
        }
    }

    //print all elements from front to rear
    private void traverse() {
        System.out.print("\nTraversal (front to rear): ");
        Node tempP = front;
        while (tempP != null) {
            System.out.print(tempP.item + " ");
            tempP = tempP.next;
        }
        System.out.println();
    }

    private int getCurrentSize() {
        int size = 0;
        Node tempP = front;
        while (tempP.next != null) {
            size++;
            tempP = tempP.next;
        }
        return size;
    }

    //Delete n integers from the priority queue with recursion, and print the priority of items
    private void deleteMin() {
        if ((!queueIsEmpty())) {
            //print priority
            System.out.print(getCurrentSize() + 1 + "  ");
            //System.out.print(frontNode.item + ((frontNode.next == null)?"":", ") );
            //front pointer is moved away from the first node, so that the first node is treated as removed.
            front = front.next;

        }
    }

    //Test the actual time it takes
    private void test(int n) {

        int[] array = getRanArr(n);

        System.out.println("\nGiven n = " + n);
        long start = System.nanoTime();
        for (int i = 0; i < n; i++) {
            insert(array[i]);
        }
        int initialLength = getCurrentSize() + 1;
        System.out.print("Priority of deletion: ");
        for (int i = 0; i < initialLength; i++) {
            deleteMin();
        }
        long end = System.nanoTime();
        System.out.println("\nTime: " + (end - start));
    }

    //Check if the queue is empty
    private boolean queueIsEmpty() {
        return (front == null);
    }

    //Check if the given number is smaller than the front element of queue
    private boolean isSmallerNumber(int number) {
        return (number < front.item);
    }


    public static void main(String[] args) {
        PriorityQueueLinkedList priorityQueueLinkedList = new PriorityQueueLinkedList();
        int[] inputArray = getInputArray();
        for (int element : inputArray) {
            priorityQueueLinkedList.insert(element);
        }

        //traverse the queue after insertion
        priorityQueueLinkedList.traverse();

        //delete all nodes from small to large and show priority
        int initialLength = priorityQueueLinkedList.getCurrentSize();
        System.out.print("Priority of deletion: ");
        for (int i = 0; i < initialLength; i++) {
            priorityQueueLinkedList.deleteMin();
        }
        priorityQueueLinkedList.deleteMin();

        System.out.println("\n");
        System.out.println("\n==================================================================");
        System.out.println("Part B (Tests): ");
        priorityQueueLinkedList.test(50);
        priorityQueueLinkedList.test(100);
        priorityQueueLinkedList.test(1000);
        priorityQueueLinkedList.test(5000);
        priorityQueueLinkedList.test(10000);

        //Conclusion:

        /*minHeap priority queue is less sensitive to large iterations than linkedList priority queue so the variation
        between LinkedList.test(10000); and and MinHeap.test(10000); is a lot i.e. MinHeap is faster when test big values*/
    }

}
