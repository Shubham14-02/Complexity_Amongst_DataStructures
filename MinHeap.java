package PriorityMinHeapArray;

import java.io.File;
import java.util.Random;
import java.util.Scanner;

/**
 * COSC 2P03 - Assignment 3
 * Name: Shubham N. Amrelia
 * Student ID: 6846877
 * Date: November 13th, 2020
 */

/**
 * Min heap array version of priority queue
 * */
public class MinHeap<E extends Comparable<? super E>> {
    private final E[] heap;
    private final int size;
    private int n;

    private static final int RANDOM_SEED = 87877;
    private static final Random randomGenerator = new Random(RANDOM_SEED);
    private static final Integer min = 1;
    private static final Integer max = 1001;


    public MinHeap(E[] h, int num, int max) {
        heap = h;
        n = num;
        size = max;
        buildHeap();
    }

    //Create a array of random numbers between 1 to 1001
    public static Integer[] getRanArr(int n) {
        Integer[] r = new Integer[n];
        //Random numbers between 1 to 1001
        for (int i = 0; i < n; i++) {
            r[i] = randomGenerator.nextInt(max - min) + min;
        }
        return r;
    }

    //read numbers from the file
    private static Integer[] getInputArray() {
        try {
            Scanner sc = new Scanner(new File("src/assn3in.txt"));
            //read the first number to be the size of queue
            int size = sc.nextInt();
            Integer[] inputArray = new Integer[size];
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
        return new Integer[0];
    }


    //return true if the element is a leaf
    public boolean isLeaf(int pos) {
        return (pos >= n / 2) && (pos < n);
    }


    //return the index of left child
    public int getLeftChildIndex(int pos) {
        assert pos < n / 2 : "Position has no left child";
        return 2 * pos + 1;
    }

    //return the index of the right child
    public int getRightChildIndex(int pos) {
        assert pos < (n - 1) / 2 : "Position has no right child";
        return 2 * pos + 2;
    }


    //get the index of parent
    public int getParentIndex(int pos) {
        assert pos > 0 : "Position has no parent";
        return (pos - 1) / 2;
    }

    //insert value into heap
    public void insert(E val) {
        assert n < size : "Heap is full";
        int curr = n++;
        heap[curr] = val;
        while ((curr != 0) && (heap[curr].compareTo(heap[getParentIndex(curr)]) < 0)) {
            swap(heap, curr, getParentIndex(curr));
            curr = getParentIndex(curr);
        }
    }

    private void swap(E[] heap, int index1, int index2) {
        E temp;
        temp = heap[index1];
        heap[index1] = heap[index2];
        heap[index2] = temp;
    }

    //build heap by traversing all non-leaf nodes with shift down function
    public void buildHeap() {
        for (int i = n / 2 - 1; i >= 0; i--) shiftDown(i);
    }

    //Shifts smaller values down
    private void shiftDown(int pos) {
        assert (pos >= 0) && (pos < n) : "Illegal heap position";
        while (!isLeaf(pos)) {
            int leftChildIndex = getLeftChildIndex(pos);
            int rightChildIndex = getRightChildIndex(pos);
            if ((leftChildIndex < (n - 1)) && (heap[leftChildIndex].compareTo(heap[rightChildIndex]) > 0))
                leftChildIndex++;
            if (heap[pos].compareTo(heap[leftChildIndex]) < 0) return;
            swap(heap, pos, leftChildIndex);
            pos = leftChildIndex;
        }
    }

    //delete the minimum value
    public void deleteMin() {
        assert n > 0 : "Removing from empty heap";
        // Swap root value(smallest value) with the last value
        swap(heap, 0, --n);
        // If not last element yet, keep shifting down
        if (n != 0) shiftDown(0);
        //print priority
        System.out.print(n + 1 + "  ");
    }

    //Preorder traversal
    public void traverse(int i) {
        if (i >= heap.length) return;
        System.out.print(heap[i] + "  ");
        traverse(2 * i + 1);
        traverse(2 * i + 2);
    }

    //Test the scenarios. In this case, we need to re-initialize the object/heap with dedicated size n.
    private static void test(int n) {
        Integer[] inputArray = getRanArr(n);

        MinHeap<Integer> minHeap = new MinHeap<>(inputArray, 0, inputArray.length);
        System.out.println("\nGiven n = " + n);
        long start = System.nanoTime();
        for (int i = 0; i < n; i++) {
            minHeap.insert(inputArray[i]);
        }
        System.out.print("Priority of deletion: ");
        for (int i = 0; i < minHeap.heap.length; i++) {
            minHeap.deleteMin();
        }
        long end = System.nanoTime();
        System.out.println("\nTime: " + (end - start));
    }


    public static void main(String[] args) {
        Integer[] inputArray = getInputArray();
        MinHeap<Integer> minHeap = new MinHeap<>(inputArray, 0, inputArray.length);
        for (int element : inputArray) {
            minHeap.insert(element);
        }

        //preorder traversal
        System.out.print("\nPreorder traversal:    ");
        minHeap.traverse(0);

        System.out.print("\nPriority of deletion:  ");
        //Print order to delete
        for (int i = 0; i < minHeap.heap.length; i++) {
            minHeap.deleteMin();
        }

        System.out.println("\n");
        System.out.println("\n==================================================================");
        System.out.println("Part B (Tests): ");
        test(50);
        test(100);
        test(1000);
        test(5000);
        test(10000);
    }

}
