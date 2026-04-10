import java.util.*;

public class RedBlackTree {
   Node root;

   static final int BLACK = 1, RED = 0;

   private static final int NEGATIVE_RED = -1;
   private static final int DOUBLE_BLACK = 2;

   public RedBlackTree() { root = null; }

   /**
      Tries to remove an object from the tree. Does nothing
      if the object is not contained in the tree.
      @param obj the object to remove
   */
   public void remove(Comparable obj) // [1]
   {
      // Find node to be removed

      Node toBeRemoved = root;
      boolean found = false;
      while (!found && toBeRemoved != null)
      {
         int d = toBeRemoved.data.compareTo(obj);
         if (d == 0) { found = true; }
         else
         {
            if (d > 0) { toBeRemoved = toBeRemoved.left; }
            else { toBeRemoved = toBeRemoved.right; }
         }
      }

      if (!found) { return; }

      // toBeRemoved contains obj

      // If one of the children is empty, use the other

      if (toBeRemoved.left == null || toBeRemoved.right == null)
      {
         Node newChild;
         if (toBeRemoved.left == null) { newChild = toBeRemoved.right; }
         else { newChild = toBeRemoved.left; }

         fixBeforeRemove(toBeRemoved); // [2]
         replaceWith(toBeRemoved, newChild); // [3]
         return;
      }

      // Neither subtree is empty

      // Find smallest element of the right subtree

      Node smallest = toBeRemoved.right;
      while (smallest.left != null)
      {
         smallest = smallest.left;
      }

      // smallest contains smallest child in right subtree

      // Move contents, unlink child

      toBeRemoved.data = smallest.data;
      fixBeforeRemove(smallest);
      replaceWith(smallest, smallest.right);
   }

   /**
     Fixes the tree so that it is a red-black tree after a node has been removed.
     @param toBeRemoved the node that is to be removed
   */
   private void fixBeforeRemove(Node toBeRemoved) // [2]
   {
      if (toBeRemoved.color == RED) { return; }

      if (toBeRemoved.left != null || toBeRemoved.right != null) // It is not a leaf
      {
         // Color the child black
         if (toBeRemoved.left == null) { toBeRemoved.right.color = BLACK; }
         else { toBeRemoved.left.color = BLACK; }
      }
      else { bubbleUp(toBeRemoved.parent); } // [4]
   }

   /**
      Move a charge from two children of a parent
      @param parent a node with two children, or null (in which case nothing is done)
   */
   private void bubbleUp(Node parent) // [4]
   {
      if (parent == null) { return; }
      parent.color++;
      parent.left.color--;
      parent.right.color--;

      if (bubbleUpFix(parent.left)) { return; } // [5]
      if (bubbleUpFix(parent.right)) { return; }

      if (parent.color == DOUBLE_BLACK)
      {
         if (parent.parent == null) { parent.color = BLACK; }
         else { bubbleUp(parent.parent); }
      }
   }

   /**
      Fixes a negative-red or double-red violation introduced by bubbling up.
      @param child the child to check for negative-red or double-red violations
      @return true if the tree was fixed
   */
   private boolean bubbleUpFix(Node child) // [5]
   {
      if (child.color == NEGATIVE_RED) { fixNegativeRed(child); return true; }
      else if (child.color == RED)
      {
         if (child.left != null && child.left.color == RED)
         {
            fixDoubleRed(child.left); return true;
         }
         if (child.right != null && child.right.color == RED)
         {
            fixDoubleRed(child.right); return true;
         }
      }
      return false;
   }

   /**
      Updates the parent's and replacement node's links when this node is replaced.
      Also updates the root reference if it is replaced.
      @param toBeReplaced the node that is to be replaced
      @param replacement the node that replaces that node
   */
   private void replaceWith(Node toBeReplaced, Node replacement) // [3]
   {
      if (toBeReplaced.parent == null)
      {
         replacement.parent = null;
         root = replacement;
      }
      else if (toBeReplaced == toBeReplaced.parent.left)
      {
         toBeReplaced.parent.setLeftChild(replacement);
      }
      else
      {
         toBeReplaced.parent.setRightChild(replacement);
      }
   }

   /**
      Fixes a "negative red" violation.
      @param negRed the negative red node
   */
   private void fixNegativeRed(Node negRed)
   {
      Node parent = negRed.parent;
      Node child;
      if (parent.left == negRed)
      {
         Node n1 = negRed.left;
         Node n2 = negRed;
         Node n3 = negRed.right;
         Node n4 = parent;
         Node t1 = n3.left;
         Node t2 = n3.right;
         Node t3 = n4.right;
         n1.color = RED;
         n2.color = BLACK;
         n4.color = BLACK;

         replaceWith(n4, n3);
         n3.setLeftChild(n2);
         n3.setRightChild(n4);
         n2.setLeftChild(n1);
         n2.setRightChild(t1);
         n4.setLeftChild(t2);
         n4.setRightChild(t3);

         child = n1;
      }
      else // Mirror image
      {
         Node n4 = negRed.right;
         Node n3 = negRed;
         Node n2 = negRed.left;
         Node n1 = parent;
         Node t3 = n2.right;
         Node t2 = n2.left;
         Node t1 = n1.left;
         n4.color = RED;
         n3.color = BLACK;
         n1.color = BLACK;

         replaceWith(n1, n2);
         n2.setRightChild(n3);
         n2.setLeftChild(n1);
         n3.setRightChild(n4);
         n3.setLeftChild(t3);
         n1.setRightChild(t2);
         n1.setLeftChild(t1);

         child = n4;
      }

      if (child.left != null && child.left.color == RED)
      {
         fixDoubleRed(child.left);
      }
      else if (child.right != null && child.right.color == RED)
      {
         fixDoubleRed(child.right);
      }
   }

   /**
      Fixes a "double red" violation.
      @param child the child with a red parent
   */
   private void fixDoubleRed(Node child)
   {
      Node parent = child.parent;
      Node grandParent = parent.parent;
      if (grandParent == null) { parent.color = BLACK; return; }
      Node n1, n2, n3, t1, t2, t3, t4;
      if (parent == grandParent.left)
      {
         n3 = grandParent; t4 = grandParent.right;
         if (child == parent.left)
         {
            n1 = child; n2 = parent;
            t1 = child.left; t2 = child.right; t3 = parent.right;
         }
         else
         {
            n1 = parent; n2 = child;
            t1 = parent.left; t2 = child.left; t3 = child.right;
         }
      }
      else
      {
         n1 = grandParent; t1 = grandParent.left;
         if (child == parent.left)
         {
            n2 = child; n3 = parent;
            t2 = child.left; t3 = child.right; t4 = parent.right;
         }
         else
         {
            n2 = parent; n3 = child;
            t2 = parent.left; t3 = child.left; t4 = child.right;
         }
      }

      replaceWith(grandParent, n2);
      n1.setLeftChild(t1);
      n1.setRightChild(t2);
      n2.setLeftChild(n1);
      n2.setRightChild(n3);
      n3.setLeftChild(t3);
      n3.setRightChild(t4);
      n2.color = grandParent.color - 1;
      n1.color = BLACK;
      n3.color = BLACK;

      if (n2 == root)
      {
         root.color = BLACK;
      }
      else if (n2.color == RED && n2.parent.color == RED)
      {
         fixDoubleRed(n2);
      }
   }


   static class Node {
      public void accept(Visitor v) { v.visitNode(this); }
      public String toString() { return (this.color == BLACK) ? (" " + this.data + " ") : ("[" + this.data + "]"); }
      public Comparable data;
      public Node left, right, parent;
      public int color;
      public Node() {}

      /**
         Sets the left child and updates its parent reference.
         @param child the new left child
      */
      public void setLeftChild(Node child)
      {
         left = child;
         if (child != null) { child.parent = this; }
      }

      /**
         Sets the right child and updates its parent reference.
         @param child the new right child
      */
      public void setRightChild(Node child)
      {
         right = child;
         if (child != null) { child.parent = this; }
      }


   }
}

// --
