public class Book {
    private String isbn, title, author;

    Book(String i, String t, String a) {
        isbn = i;
        title = t;
        author = a;
    }

    public String getIsbn() { return isbn; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }

    public void setIsbn(String i) { isbn = i; }
    public void setTitle(String t) { title = t; }
    public void setAuthor(String a) { author = a; }

    public String toString() { return isbn + " " + title + " " + author + "\n"; }
}
