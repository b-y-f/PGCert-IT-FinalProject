package admin.BlogManager.blogData;

public class Post {

    private int id;
    private int authorid;
    private String title;
    private String created_at;
    private String editted_at;
    private String content;
    private boolean visible;

    Post(){

    };


    public int getId() {
        return id;
    }

    public int getAuthorid() {
        return authorid;
    }

    public String getTitle() {
        return title;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getEditted_at() {
        return editted_at;
    }

    public String getContent() {
        return content;
    }

    public boolean isVisible() {
        return visible;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", authorId=" + authorid +
                ", title='" + title + '\'' +
                ", created_at='" + created_at + '\'' +
                ", editted_at='" + editted_at + '\'' +
                ", content='" + content + '\'' +
                ", visible=" + visible +
                '}';
    }
}
