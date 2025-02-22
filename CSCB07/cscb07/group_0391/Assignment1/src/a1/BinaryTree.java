package a1;

import java.util.ArrayList;
import java.util.LinkedList;
// **********************************************************
// Assignment1:
// Student1:
// UTORID user_name: yaojesse
// UT Student #: 1006312269
// Author: Jesse Yao
//
// Student2:
// UTORID user_name: wuyulu10
// UT Student #: 1004912785
// Author: Yulun Wu
//
//
// Honor Code: I pledge that this program represents my own
// program code and that I have coded on my own. I received
// help from no one in designing and debugging my program.
// I have also read the plagiarism section in the course info
// sheet of CSC B07 and understand the consequences.
// *********************************************************

/*
 * the provided starter code may contain warnings of raw type. This is OK, because we have'nt yet
 * learned generics. After having learnt generics, we will revisit this code, and remove the
 * warnings out.
 */
public class BinaryTree {

  private Node root;
  private ArrayList dataList;

  /*
   * adds data inside a binary tree level by level starting from left to right.
   */
  public BinaryTree() {
    dataList = new ArrayList();
  }

  public void addData(int d) {
    Node toAdd = new Node(d);
    if (root == null) {
      root = toAdd;
    } else {
      LinkedList ll = new LinkedList();
      ll.add(root);
      while (!(ll.isEmpty())) {
        Node currentNode = (Node) ll.poll();
        if (currentNode.getLeftNode() == null) {
          currentNode.setLeftNode(toAdd);
          break;
        } else if (currentNode.getRightNode() == null) {
          currentNode.setRightNode(toAdd);
          break;
        } else {
          /*
           * remember, the queue is FIFO, and due to this we add first the left node
           * followed by the right node.
           */
          ll.add(currentNode.getLeftNode());
          ll.add(currentNode.getRightNode());
        }
      }
    }
  }

  /**
   * Returns a textual representation of the binary tree, containing all the ints of the
   * binary tree level by level and moving from left to right.
   * 
   * @see java.lang.Object#toString()
   */
  public String toString() {
    // queue of tree nodes to explore during breadth-first traversal
    LinkedList queue = new LinkedList();
    String output = "";
    // if tree is nonempty, set root as first node to be explored
    if (root != null) {
      queue.offer(root);
    }

    // perform breadth-first traversal on the tree
    while (!queue.isEmpty()) {
      // get and append the current node's value to the output string
      Node node = (Node) queue.poll();
      output += node.getData() + " ";

      // set left and right children of current node as nodes to be explored later
      if (node.getLeftNode() != null) {
        queue.offer(node.getLeftNode());
      }
      if (node.getRightNode() != null) {
        queue.offer(node.getRightNode());
      }
    }

    return output;
  }

  /**
   * Append data in each node of binary tree to ArrayList dataList.
   * 
   * @return dataList Contains data in binary tree.
   */
  public ArrayList toList() {
    // If binary tree is not empty, call addSubTree
    if (root != null) {
      addSubTree(root, dataList);
    }
    return dataList;
  }

  /**
   * Use recursion to populate the ArrayList using inOrder traversal of the binary tree.
   * 
   * @param temp The root node to start with.
   * @param values The ArrayList to store data.
   */
  private void addSubTree(Node temp, ArrayList values) {
    // Recursively call left branch of tree
    if (temp.getLeftNode() != null) {
      addSubTree(temp.getLeftNode(), values);
    }
    // Add data in node to ArrayList
    values.add(temp.getData());
    // Recursively call right branch of tree
    if (temp.getRightNode() != null) {
      addSubTree(temp.getRightNode(), values);
    }
  }

  /*
   * You can modify the main function in any way you like we will not mark your main function. We
   * have provided some sample code inside the main function that may make it easier for you to
   * debug your code and know what the expected output is.
   */
  public static void main(String[] args) {
    BinaryTree bt = new BinaryTree();
    /*
     * adding the following ints in a binary tree. Remember, the addData adds the ints level by
     * level and from left to right. I will first ask you to run the starter code and debug out the
     * addData so that you are familiar with how it works and trace the creation of the tree using
     * pen and paper.
     */

    bt.addData(1);
    bt.addData(2);
    bt.addData(3);
    bt.addData(4);
    bt.addData(5);
    bt.addData(6);
    bt.addData(7);


    System.out.println(bt); // must print 1 2 3 4 5 6 7

    /*
     * Printing the list of the binary tree. Remember the list of the binary tree must contain the
     * ints in inOrder traversal. The for loop below will give you back a null pointer exception
     * when trying to run the starer code, this is because toList() method inside the BinaryTree
     * returns back a null.
     */
    for (Object d : bt.toList()) {
      System.out.println((int) d);
    }
    /*
     * the above loop will print the following: 4 2 5 1 6 3 7
     */
  }
}
