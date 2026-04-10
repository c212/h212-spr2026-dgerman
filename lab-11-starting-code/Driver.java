public class Driver {
   public static void main(String[] args)
   {
      Visitor visitor = new Reporter();
      RedBlackTree t = new RedBlackTree();
      RedBlackTree.Node a = new RedBlackTree.Node();
      RedBlackTree.Node b = new RedBlackTree.Node();
      RedBlackTree.Node c = new RedBlackTree.Node();
      RedBlackTree.Node d = new RedBlackTree.Node();
      RedBlackTree.Node e = new RedBlackTree.Node();
      e.data = 70; e.left = null; e.right = null; e.color = RedBlackTree.BLACK; e.parent = c;
      d.data = 90; d.left = null; d.right = null; d.color = RedBlackTree.BLACK; d.parent = c;
      c.data = 80; c.left =    e; c.right =    d; c.color = RedBlackTree.RED;   c.parent = a;
      b.data = 20; b.left = null; b.right = null; b.color = RedBlackTree.BLACK; b.parent = a;
      a.data = 40; a.left =    b; a.right =    c; a.color = RedBlackTree.BLACK; a.parent = null;
      t.root = a;
      t.root.accept(visitor);
      System.out.println("Now we remove: 20");
      System.out.println("The tree becomes: ");
      t.remove(20); // [1]
      t.root.accept(visitor);


   }
}
