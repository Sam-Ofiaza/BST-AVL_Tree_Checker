/* Class containing left and right child of current
node and key value*/
class BinaryNode implements Comparable<BinaryNode>
{
    private int key;
    private BinaryNode left, right, parent;

    public BinaryNode(int item)
    {
        key = item;
        left = right = parent = null;
    }

    public int getKey() { return key; }
    public BinaryNode getLeft() { return left; }
    public BinaryNode getRight() { return right; }
    public BinaryNode getParent() { return parent; }

    public void setKey(int k) { key = k; }
    public void setLeft(BinaryNode n) { left = n; }
    public void setRight(BinaryNode n) { right = n; }
    public void setParent(BinaryNode n) { parent = n; }

    public int compareTo(BinaryNode other) {
        if(this.key == other.key)
            return 0;
        else if(this.key < other.key)
            return -1;
        else
            return 1;
    }
}