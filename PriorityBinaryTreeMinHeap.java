package PriorityMinHeapBinaryTree;

import java.io.File;
import java.util.Random;
import java.util.Scanner;

/**
 * COSC 2P03 - Assignment 3
 * Name: Shubham N. Amrelia
 * Student ID: 6846877
 * Date: November 13th, 2020
 */

public class PriorityBinaryTreeMinHeap {
    private TreeNode root;
    static TreeNode targetNode;
    private static final int RANDOM_SEED = 87877;
    private static final Random randomNumber = new Random(RANDOM_SEED);
    private static final int min = 1;
    private static final int max = 1001;

    public PriorityBinaryTreeMinHeap() {
        root = null;
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

    //read file
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

    //get height of tree
    public int getTreeHeight(TreeNode root) {
        if (root == null) {
            return 0;
        } else {
            int leftSubTreeHeight = getTreeHeight(root.leftChild);
            int rightSubTreeHeight = getTreeHeight(root.rightChild);
            //add one for every recursive call, and the height will be calculated in this way.
            if (leftSubTreeHeight > rightSubTreeHeight) return (leftSubTreeHeight + 1);
            else return (rightSubTreeHeight + 1);
        }
    }


    //Shifts smaller values down
    private void shiftDown(TreeNode node) {
        while (node != null && !isLeaf(node)) {
            TreeNode temp = node.leftChild;
            if (node.rightChild != null && node.leftChild.item > node.rightChild.item) temp = node.rightChild;
            if (node.item < temp.item) return;
            swapValue(node, temp);
            node = temp;
        }
    }

    //Heapify the whole tree n times
    private void heapifyAll(int n) {
        for (int i = n; i >= 0; i--) heapify(root);
    }

    //Traverse the whole tree by the preorder way
    private void heapify(TreeNode node) {
        if (node == null) return;
        shiftDown(node);
        heapify(node.leftChild);
        heapify(node.rightChild);
    }

    // checks if the node is a leaf or not
    public boolean isLeaf(TreeNode node) {
        return (node.leftChild == null && node.rightChild == null);
    }


    //Setup the binary tree from array
    public TreeNode insertAllNodes(int[] arr, TreeNode root, int i) {
        if (i < arr.length) {
            TreeNode temp = new TreeNode(arr[i]);
            root = temp;
            //Insert value as left child
            root.leftChild = insertAllNodes(arr, root.leftChild, 2 * i + 1);
            //Insert value as right child
            root.rightChild = insertAllNodes(arr, root.rightChild, 2 * i + 2);
        }
        return root;
    }

    //delete the min value
    public void deleteMin() {
        //Get the total number of nodes.
        //Then the node with the same index represents the last node
        int numberOfNodes = getTreeSize(root);

        searchAll(numberOfNodes);
        TreeNode lastNode = targetNode;

        int indexOfLastElement = lastNode.index;
        //replace the value of root with value of the lastElement
        root.item = lastNode.item;

        //Then remove the last element of the min heap
        //remove left child if index is even, else remove right child
        if (indexOfLastElement % 2 == 0) {
            int targetIndex = getParentIndex(indexOfLastElement);
            searchAll(targetIndex);
            removeLeftChild(targetNode);
        } else {
            int targetIndex = getParentIndex(indexOfLastElement);
            searchAll(targetIndex);
            removeRightChild(targetNode);
        }
        if (numberOfNodes != 1) heapify(root);
    }


    private int getParentIndex(int childIndex) {
        return childIndex / 2;
    }

    //remove left child of given root
    private void removeLeftChild(TreeNode root) {
        if (root == null || isLeaf(root)) return;
        root.leftChild = null;
    }

    //remove right child of given root
    private void removeRightChild(TreeNode root) {
        if (root == null || isLeaf(root)) return;
        root.rightChild = null;
    }

    //return the total number of nodes of the tree
    public int getTreeSize(TreeNode root) {
        if (root == null) return 0;
        return 1 + getTreeSize(root.leftChild) + getTreeSize(root.rightChild);
    }

    //search node2
    private void searchAll(int targetIndex) {
        int h = getTreeHeight(root);
        for (int i = 1; i <= h; i++) {
            levelOrderSearch(root, i, targetIndex);
        }
    }

    private void levelOrderSearch(TreeNode root, int level, int targetIndex) {
        if (root == null) return;
        if (level == 1 && root.index == targetIndex) {
            targetNode = root;
        } else if (level > 1) {
            levelOrderSearch(root.leftChild, level - 1, targetIndex);
            levelOrderSearch(root.rightChild, level - 1, targetIndex);
        }
    }


    //Preorder traversal
    private void traverse(TreeNode node) {
        if (node == null) return;
        System.out.print(node.item + " ");
//        System.out.print(node.item + " ");
        traverse(node.leftChild);
        traverse(node.rightChild);
    }

    //Swap the values of two nodes.
    private void swapValue(TreeNode node1, TreeNode node2) {
        if (node1 == null || node2 == null) return;
        int tmp = node2.item;
        node2.item = node1.item;
        node1.item = tmp;
    }

    //Make the tree an indexed tree
    private void defineAllLevelIndex() {
        int h = getTreeHeight(root);
        for (int i = 1; i <= h; i++)
            defineGivenLevel(root, i, 1);
    }

    //index
    private void defineGivenLevel(TreeNode root, int level, int i) {
        if (root == null) return;
        if (level == 1) root.index = i;
        else if (level > 1) {
            defineGivenLevel(root.leftChild, level - 1, 2 * i);
            defineGivenLevel(root.rightChild, level - 1, 2 * i + 1);
        }
    }

    //Test the scenarios. In this case, we need to re-initialize the object/heap with dedicated size n.
    private static void test(int n) {
        int[] inputArray = getRanArr(n);

        PriorityBinaryTreeMinHeap binaryTreeHeap = new PriorityBinaryTreeMinHeap();
        System.out.println("Given n = " + n);
        long start = System.nanoTime();
        binaryTreeHeap.root = binaryTreeHeap.insertAllNodes(inputArray, binaryTreeHeap.root, 0);
        binaryTreeHeap.defineAllLevelIndex();
        binaryTreeHeap.heapifyAll(inputArray.length);
        //find the last element of the priority queue
        binaryTreeHeap.searchAll(inputArray.length);
        System.out.println("Priority of deletion:");
        for (int i = 0; i < inputArray.length; i++) {
            System.out.print(binaryTreeHeap.getTreeSize(binaryTreeHeap.root) + " ");
            binaryTreeHeap.deleteMin();
        }
        long end = System.nanoTime();
        System.out.println("\nTime: " + (end - start));
        System.out.println();
    }

    public static void main(String[] args) {

        int[] inputArray = getInputArray();
        PriorityBinaryTreeMinHeap binaryTreeHeap = new PriorityBinaryTreeMinHeap();

        //insert into the binary tree in level order
        binaryTreeHeap.root = binaryTreeHeap.insertAllNodes(inputArray, binaryTreeHeap.root, 0);

        //define tree index
        binaryTreeHeap.defineAllLevelIndex();
        binaryTreeHeap.heapifyAll(inputArray.length);

        //find the last element of the priority queue
        binaryTreeHeap.searchAll(inputArray.length);

        //Print the binary tree in preorder traversal
        System.out.println("\nPre-order Traversal:    ");

        binaryTreeHeap.traverse(binaryTreeHeap.root);
        System.out.println("\nPriority of deletion:");
        for (int i = 0; i < inputArray.length; i++) {
            System.out.print(binaryTreeHeap.getTreeSize(binaryTreeHeap.root) + " ");
            binaryTreeHeap.deleteMin();
        }
        System.out.println("\n");
        System.out.println("\n==================================================================");
        System.out.println("\nPart B (Tests):   \n");
        test(50);
        test(100);
        test(1000);
        test(5000);
        test(10000);
    }

}
