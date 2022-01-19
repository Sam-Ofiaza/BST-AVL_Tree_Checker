import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;

// A Java program to introduce Binary Tree
class BinaryTree
{
    // Root of Binary Tree
    public BinaryNode root;

    // Constructors
    public BinaryTree() { root = null; }
    public BinaryTree(int key) { root = new BinaryNode(key); }

    // Place new node at random empty available node
    public void insert(BinaryNode node) {
        if(root == null) // Empty tree case
            root = node;
        else if(root.getLeft() == null && root.getRight() == null) { // Single node tree case
            int randChild = (int)(Math.random() * 2);
            if(randChild == 0) {
                root.setLeft(node);
                //System.out.println(node.getKey() + " is now the left child of " + root.getKey());
            }
            else {
                root.setRight(node);
                //System.out.println(node.getKey() + " is now the right child of " + root.getKey());
            }
        }
        else {
            // Find all parents with null nodes
            ArrayList<BinaryNode> parents = new ArrayList<BinaryNode>();
            parentsOfNullsFinder(parents, root);

            // Find all potential insertion points

            // The following 2 lists will have matching values based on indices
            ArrayList<BinaryNode> nulls = new ArrayList<BinaryNode>();
            ArrayList<Boolean> type = new ArrayList<Boolean>(); // false = left, true = right

            for(BinaryNode n : parents)
                if(n.getLeft() == null && n.getRight() == null) {
                    nulls.add(n);
                    type.add(false);
                    nulls.add(n);
                    type.add(true);
                }
                else if(n.getLeft() == null) {
                    nulls.add(n);
                    type.add(false);
                }
                else {
                    nulls.add(n);
                    type.add(true);
                }

            // Insert at random null spot

            int randNodeIndex = (int) (Math.random() * nulls.size());
            BinaryNode chosenParent = nulls.get(randNodeIndex);

            if (chosenParent.compareTo(nulls.get(nulls.indexOf(chosenParent)+1)) == 0) { // Both children are null, coin flip
                int randChild = (int) (Math.random() * 2);
                if (randChild == 0) {
                    chosenParent.setLeft(node);
                    //System.out.println(node.getKey() + " is now the left child of " + chosenParent.getKey());
                }
                else {
                    chosenParent.setRight(node);
                    //System.out.println(node.getKey() + " is now the right child of " + chosenParent.getKey());
                }
            }
            else if (!type.get(nulls.indexOf(chosenParent))) { // Left child is null
                chosenParent.setLeft(node);
                //System.out.println(node.getKey() + " is now the left child of " + chosenParent.getKey());
            }
            else { // Right child is null
                chosenParent.setRight(node);
                //System.out.println(node.getKey() + " is now the right child of " + chosenParent.getKey());
            }
        }
    }

    // Insert helper method; traverses tree and stores parents of null nodes; never actually calls with null node as parameter
    public void parentsOfNullsFinder (ArrayList<BinaryNode> spots, BinaryNode node) {
        if(node.getLeft() == null && !spots.contains(node)) {
            spots.add(node);
            if(node.getRight() != null)
                parentsOfNullsFinder(spots, node.getRight());
        }
        else if(node.getRight() == null && !spots.contains(node)) {
            spots.add(node);
            if(node.getLeft() != null)
                parentsOfNullsFinder(spots, node.getLeft());
        }
        else {
            parentsOfNullsFinder(spots, node.getLeft());
            parentsOfNullsFinder(spots, node.getRight());
        }
    }

    // BST Checker; returns count of BST inconsistencies (e.g. left child > parent and vice versa)
    // Toggleable 'desc' parameter for description of each node.
    public int bstChecker(BinaryNode node, boolean desc) {
        if(node.getLeft() != null && node.getRight() != null) { // Both left and right child exist
            if(node.getKey() < node.getLeft().getKey() && node.getKey() > node.getRight().getKey()) { // Both children have errors
                if(desc)
                    System.out.println(node.getKey() + " - Both children have errors, adding 2.");
                return bstChecker(node.getLeft(), desc) + bstChecker(node.getRight(), desc) + 2;
            }
            else if(node.getKey() < node.getLeft().getKey() || node.getKey() > node.getRight().getKey()) { // Only left or right child error
                if(desc)
                    System.out.println(node.getKey() + " - Only one child has an error, adding 1.");
                return bstChecker(node.getLeft(), desc) + bstChecker(node.getRight(), desc) + 1;
            }
            else { // No errors
                if(desc)
                    System.out.println(node.getKey() + " - No errors.");
                return bstChecker(node.getLeft(), desc) + bstChecker(node.getRight(), desc);
            }
        }
        else if(node.getLeft() != null) { // Only left child exists
            if(node.getKey() < node.getLeft().getKey()) {
                if(desc)
                    System.out.println(node.getKey() + " - Left child error, adding 1.");
                return bstChecker(node.getLeft(), desc) + 1;
            }
            else {
                if(desc)
                    System.out.println(node.getKey() + " - No errors.");
                return bstChecker(node.getLeft(), desc);
            }
        }
        else if(node.getRight() != null) { // Only right child exists
            if(node.getKey() > node.getRight().getKey()) {
                if(desc)
                    System.out.println(node.getKey() + " - Right child error, adding 1.");
                return bstChecker(node.getRight(), desc) + 1;
            }
            else {
                if(desc)
                    System.out.println(node.getKey() + " - No errors.");
                return bstChecker(node.getRight(), desc);
            }
        }
        else {
            if(desc)
                System.out.println(node.getKey() + " - No errors.");
            return 0;
        }
    }

    // BST maker; inserts equal nodes at left; assumes node is not null
    public void insertToBST(BinaryNode parent, BinaryNode node) {
        if(root == null)
            root = node;
        else if(node.getKey() <= parent.getKey()) {
            if(parent.getLeft() != null) {
                insertToBST(parent.getLeft(), node);
            }
            else {
                //System.out.println(node.getKey() + " is now the left child of " + parent.getKey());
                parent.setLeft(node);
            }
        }
        else if(node.getKey() > parent.getKey()) {
            if(parent.getRight() != null) {
                insertToBST(parent.getRight(), node);
            }
            else {
                //System.out.println(node.getKey() + " is now the right child of " + parent.getKey());
                parent.setRight(node);
            }
        }
    }

    // Prints tree in in-order
    public void printInOrder(BinaryNode node) {
        if(node != null) {
            printInOrder(node.getLeft());
            System.out.print(node.getKey() + " ");
            printInOrder(node.getRight());
        }
    }

    // Prints balances of nodes in in-order
    public void printBalancesInOrder(BinaryNode node) {
        if(node != null) {
            printBalancesInOrder(node.getLeft());
            System.out.println("Balance of " + node.getKey() + " - " + AVLTree.getBalanceBinaryNode(node) + ".");
            printBalancesInOrder(node.getRight());
        }
    }

    public static void main(String[] args)
    {
        // Make random list of 25 ints
        HashSet<Integer> set = new HashSet<Integer>();
        int[] list = new int[10];
        Integer rand = (int)((Math.random()*list.length)+1);
        for(int i = 0; i < list.length; i++) {
            while(set.contains(rand))
                rand = (int)((Math.random()*list.length)+1);
            set.add(rand);
            list[i] = rand;
        }

        // AVL tree test
        //int[] list = {5, 2, 7, 1, 3, 4, 6, 7, 8, 9};

        // Make tree; to test random BST, replace line in loop with tree.insertToBST(tree.root, new BinaryNode(x));
        BinaryTree tree = new BinaryTree();
        for(int x : list) {
            //System.out.print("\nCurrent tree: ");
            //tree.printInOrder(tree.root);
            //System.out.println("\nInserting " + x);
            tree.insert(new BinaryNode(x));
        }

        /*
        System.out.println();
        tree.printInOrder(tree.root);
        System.out.println("\n");

        tree.printBalancesInOrder(tree.root);
        System.out.println();*/

        // Check if BST
        int bstErrors = tree.bstChecker(tree.root, false);
        if(bstErrors > 0) {
            System.out.println("The tree is not a BST and has " + bstErrors + " instances of incorrect parent-left/right child relationships.");
        }
        else {
            System.out.println("The tree is a BST.\n");

            // If BST, check if AVL tree
            int avlErrors = AVLTree.avlTreeChecker(tree.root, false);
            if(avlErrors > 0)
                System.out.println("The tree is not an AVL tree and has " + avlErrors + " unbalanced heights.");
            else
                System.out.println("The tree is an AVL tree.");
        }

    }
}