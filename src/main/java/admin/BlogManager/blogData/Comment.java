package admin.BlogManager.blogData;

public class Comment {
    private int id;
    private int post_id;
    private int poster_id;
    private String created_at;
    private String content;
    Comment(){ };


    public int getId() {
        return id;
    }

    public int getPost_id() {
        return post_id;
    }

    public int getPoster_id() {
        return poster_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", post_id=" + post_id +
                ", poster_id=" + poster_id +
                ", created_at='" + created_at + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}

