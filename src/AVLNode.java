class AVLNode implements Comparable<AVLNode> {
    private String key; // (ISBN number)
    private Book bookObject; // Title <space> Author’s last name, any other attribute
    private int height;
    private AVLNode leftPtr, rightPtr;

    AVLNode(String k, Book b) {
        key = k;
        bookObject = b;
        height = 0;
    }

    public String getKey() { return key; }
    public Book getBook() { return bookObject; }
    public int getHeight() { return height; }
    public AVLNode getLeftPtr() { return leftPtr; }
    public AVLNode getRightPtr() { return rightPtr; }

    public void setKey(String key) { this.key = key; }
    public void setBook(Book bookObject) { this.bookObject = bookObject; }
    public void setHeight(int height) { this.height = height; }
    public void setLeftPtr(AVLNode leftPtr) { this.leftPtr = leftPtr; }
    public void setRightPtr(AVLNode rightPtr) { this.rightPtr = rightPtr; }

    @Override
    public int compareTo(AVLNode other) {
        if(this.getKey() == other.getKey())
            return 0;
        else if(Long.parseLong(this.getKey()) < Long.parseLong(other.getKey()))
            return -1;
        else
            return 1;
    }
}