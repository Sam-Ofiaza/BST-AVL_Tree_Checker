import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class AVLTree {

    AVLNode root;

    // A utility function to get the height of the tree
    public int height(AVLNode N) {
        if (N == null)
            return -1;

        return N.getHeight();
    }

    // A utility function to get maximum of two integers
    // Made static for use with getBalanceBinaryNode
    public static int max(int a, int b) {
        return (a > b) ? a : b;
    }

    // A utility function to right rotate subtree rooted with y
    // See the diagram given above.
    public AVLNode rightRotate(AVLNode y) {

        AVLNode x = y.getLeftPtr();
        AVLNode T2 = x.getRightPtr();

        // Perform rotation
        x.setRightPtr(y);
        y.setLeftPtr(T2);

        // Update heights
        y.setHeight(max(height(y.getLeftPtr()), height(y.getRightPtr())) + 1);
        x.setHeight(max(height(x.getLeftPtr()), height(x.getRightPtr())) + 1);

        // Return new root
        return x;
    }

    // A utility function to left rotate subtree rooted with x
    // See the diagram given above.
    public AVLNode leftRotate(AVLNode x) {

        AVLNode y = x.getRightPtr();
        AVLNode T2 = y.getLeftPtr();

        // Perform rotation
        y.setLeftPtr(x);
        x.setRightPtr(T2);

        // Update heights
        x.setHeight(max(height(x.getLeftPtr()), height(x.getRightPtr())) + 1);
        y.setHeight(max(height(y.getLeftPtr()), height(y.getRightPtr())) + 1);

        // Return new root
        return y;
    }

    // Get Balance factor of node N
    public int getBalance(AVLNode N) {
        if (N == null)
            return -1;

        return height(N.getLeftPtr()) - height(N.getRightPtr());
    }

    public AVLNode insert(AVLNode parent, AVLNode child) {

        long childKey = Long.parseLong(child.getKey());

        /* 1. Perform the normal BST insertion */
        if (parent == null) {
            return child;
        }

        long parentKey = Long.parseLong(parent.getKey());

        if (childKey < parentKey)
            parent.setLeftPtr(insert(parent.getLeftPtr(), child));
        else if (childKey > parentKey)
            parent.setRightPtr(insert(parent.getRightPtr(), child));
        else // Duplicate keys not allowed
            return parent;

        /* 2. Update height of this ancestor node */
        if(parent.getLeftPtr() == null & parent.getRightPtr() == null)
            parent.setHeight(0);
        else if(parent.getLeftPtr() == null)
            parent.setHeight(1 + parent.getRightPtr().getHeight());
        else if(parent.getRightPtr() == null)
            parent.setHeight(1 + parent.getLeftPtr().getHeight());
        else
            parent.setHeight(1 + max(parent.getLeftPtr().getHeight(), parent.getRightPtr().getHeight()));


		/* 3. Get the balance factor of this ancestor
			node to check whether this node became
			unbalanced */
        int balance = getBalance(parent);


        // If this node becomes unbalanced, then there
        // are 4 cases Left Left Case
        if (balance > 1 && childKey < parentKey) {
            System.out.println("Imbalance condition occurred at inserting ISBN " + child.getKey() + " fixed with LeftLeft rotation");
            return rightRotate(parent);
        }

        // Right Right Case
        if (balance < -1 && childKey > parentKey) {
            System.out.println("Imbalance condition occurred at inserting ISBN " + child.getKey() + " fixed with RightRight rotation");
            return leftRotate(parent);
        }

        // Left Right Case
        if (balance > 1 && childKey > parentKey) {
            System.out.println("Imbalance condition occurred at inserting ISBN " + child.getKey() + " fixed with LeftRight rotation");
            parent.setLeftPtr(leftRotate(parent.getLeftPtr()));
            return rightRotate(parent);
        }

        // Right Left Case
        if (balance < -1 && childKey < parentKey) {
            System.out.println("Imbalance condition occurred at inserting ISBN " + child.getKey() + " fixed with RightLeft rotation");
            parent.setRightPtr(rightRotate(parent.getRightPtr()));
            return leftRotate(parent);
        }

        /* return the (unchanged) node pointer */
        return parent;
    }

    // AVL tree checker; checks balances of each node in in-order; returns count of AVL tree inconsistencies (i.e. balance is off)
    // Toggleable 'desc' parameter for description of each node.
    public static int avlTreeChecker(BinaryNode node, boolean desc) {
        if(node.getLeft() != null && node.getRight() != null) { // Both children exist
            if(getBalanceBinaryNode(node) > 1 || getBalanceBinaryNode(node) < -1) {
                if(desc)
                    System.out.println(node.getKey() + " - Balance error, adding 1.");
                return avlTreeChecker(node.getLeft(), desc) + avlTreeChecker(node.getRight(), desc) + 1;
            }
            else {
                if(desc)
                    System.out.println(node.getKey() + " - No error.");
                return avlTreeChecker(node.getLeft(), desc) + avlTreeChecker(node.getRight(), desc);
            }
        }
        else if(node.getLeft() != null) { // Only left child exists
            if(getBalanceBinaryNode(node) > 1 || getBalanceBinaryNode(node) < -1) {
                if(desc)
                    System.out.println(node.getKey() + " - Balance error, adding 1.");
                return avlTreeChecker(node.getLeft(), desc) + 1;
            }
            else {
                if(desc)
                    System.out.println(node.getKey() + " - No error.");
                return avlTreeChecker(node.getLeft(), desc);
            }
        }
        else if(node.getRight() != null) { // Only right child exists
            if(getBalanceBinaryNode(node) > 1 || getBalanceBinaryNode(node) < -1) {
                if(desc)
                    System.out.println(node.getKey() + " - Balance error, adding 1.");
                return avlTreeChecker(node.getRight(), desc) + 1;
            }
            else {
                if(desc)
                    System.out.println(node.getKey() + " - No error.");
                return avlTreeChecker(node.getRight(), desc);
            }
        }
        else {
            if(desc)
                System.out.println(node.getKey() + " - No error.");
            return 0;
        }
    }

    // avlTreeChecker helper method; retrieves balance for a BinaryNode
    public static int getBalanceBinaryNode(BinaryNode node) {
        return getHeightBinaryNode(node.getLeft()) - getHeightBinaryNode(node.getRight());
    }

    // getBalanceBinaryNode helper method; retrieves height for a BinaryNode
    public static int getHeightBinaryNode(BinaryNode node) {
        if(node == null) // Node is a leaf with height 0
            return 0;
        else if(node.getLeft() != null && node.getRight() != null) // Both children exist
            return max(getHeightBinaryNode(node.getLeft()), getHeightBinaryNode(node.getRight())) + 1;
        else if(node.getLeft() != null) // Only left child exists
            return getHeightBinaryNode(node.getLeft()) + 1;
        else if(node.getRight() != null) // Only right child exists
            return getHeightBinaryNode(node.getRight()) + 1;
        else
            return 0;
    }

    // Print inOrder tree traversal
    public void inOrder(AVLNode node) {
        if (node != null) {
            inOrder(node.getLeftPtr());
            System.out.print(node.getKey() + " ");
            inOrder(node.getRightPtr());
        }
    }

    // Adds AVLNodes to an ArrayList in inOrder
    public void getInOrderList(ArrayList<AVLNode> list, AVLNode node) {
        if (node != null) {
            getInOrderList(list, node.getLeftPtr());
            list.add(node);
            getInOrderList(list, node.getRightPtr());
        }
    }

    // Make AVLNodes using txt file input
    public ArrayList<AVLNode> readBookRecords(String fileName) throws FileNotFoundException {
        ArrayList<AVLNode> list = new ArrayList<AVLNode>();
        Scanner sc = new Scanner(new File(fileName));
        sc.nextLine(); // Skip top line
        String isbn, title, author;
        Book bk;
        while(sc.hasNext()) {
            isbn = sc.next();
            title = sc.next();
            author = sc.next();
            bk = new Book(isbn, title, author);
            list.add(new AVLNode(isbn, bk));
        }
        return list;
    }

    public static void main(String[] args) throws FileNotFoundException {
        AVLTree tree = new AVLTree();

        // Read from file
        ArrayList<AVLNode> list = tree.readBookRecords("book data.txt");

        // Add to tree
        for(AVLNode n : list)
            tree.root = tree.insert(tree.root, n);
    }
}
// Parts of this code was contributed by Mayank Jaiswal