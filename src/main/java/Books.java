public class Books{

    private String title;
    private String author;
    long id_user;
    long id_books;

    public Books(String title, String author) {
        this.title = title;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public long getId_user() {
        return id_user;
    }

    public void setId_user(long id_user) {
        this.id_user = id_user;
    }

    public long getId_books() {
        return id_books;
    }

    public void setId_books(long id_books) {
        this.id_books = id_books;
    }

    @Override
    public String toString() {
        return "Books{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", id_user=" + id_user +
                ", id_books=" + id_books +
                '}';
    }
}
