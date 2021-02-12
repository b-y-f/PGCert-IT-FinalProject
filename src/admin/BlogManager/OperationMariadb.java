package admin.BlogManager;

import java.sql.*;

public class OperationMariadb {
    private String dburl = null;
    private Connection conn = null;
    private PreparedStatement stmt = null;
    private ResultSet rs = null;
    private String username = null;
    private String password = null;
    private String dbdriver = null;

    public void setDburl(String dburl) {
        this.dburl = dburl;
    }

    public String getDburl() {
        return dburl;
    }

    public ResultSet getRs() {
        return rs;
    }

    public void setRs(ResultSet rs) {
        this.rs = rs;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setDbdriver(String dbdriver) {
        this.dbdriver = dbdriver;
    }

    public String getDbdriver() {
        return dbdriver;
    }

    Connection createConnection(String dburl, String username, String password) throws Exception {
        setDburl(dburl);
        setUsername(username);
        setPassword(password);
        Class.forName(getDbdriver());
        return DriverManager.getConnection(dburl, username, password);
    }

    public void CloseRS() {
        try {
            rs.close();
        } catch (SQLException e) {
            System.out.println("closing result set error！");
        }
    }

    public void CloseStmt() {
        try {
            stmt.close();
        } catch (SQLException e) {
            System.out.println("closing stmt error！");
        }
    }

    public void CloseConnection() {
        try {
            conn.close();
        } catch (SQLException e) {
            System.out.println("closing connection error！");
        }
    }

    // add user
    void executeInsert(String username, String password, String nickname, String dob, String description, String email) throws Exception {
        try {
            conn = createConnection(getDburl(), getUsername(), getPassword());
            stmt = conn.prepareStatement("insert into userinfo(username,password,name, dob, description, email) values(?,?,?,?,?,?)");
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, nickname);
            stmt.setString(4, dob);
            stmt.setString(5, description);
            stmt.setString(6, email);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    // delete user
    void executeDelete(int DeleteID) throws Exception {
        try {
            conn = createConnection(getDburl(), getUsername(), getPassword());
            stmt = conn.prepareStatement("delete from userinfo where id = ?");
            stmt.setString(1, String.valueOf(DeleteID));
            stmt.executeUpdate();
            CloseStmt();
            CloseConnection();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    ResultSet executeQuery() throws Exception {
        try {
            String sql = "select * from userinfo";
            conn = createConnection(getDburl(), getUsername(), getPassword());
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return rs;
    }


    ResultSet getAllPosts() throws Exception {
        try {
            String sql = "select * from post";
            conn = createConnection(getDburl(), getUsername(), getPassword());
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return rs;
    }

    ResultSet getAllComments() throws Exception {
        try {
            String sql = "select * from post_comment";
            conn = createConnection(getDburl(), getUsername(), getPassword());
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return rs;
    }



    ResultSet findUserById(int id) throws Exception {
        try {
            String sql = "select * from userinfo where id= ?";
            conn = createConnection(getDburl(), getUsername(), getPassword());
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, String.valueOf(id));
            rs = stmt.executeQuery();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return rs;
    }


    ResultSet getEmailByUsername(String username) throws Exception {
        try {
            String sql = "select email from userinfo where username = ?";
            conn = createConnection(getDburl(), getUsername(), getPassword());
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            rs = stmt.executeQuery();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return rs;
    }


    ResultSet getPostsBetween(Date from, Date to) throws Exception {
        try{
            String sql = "select * from post where created_at between ? and ?";
            conn = createConnection(getDburl(), getUsername(), getPassword());
            stmt = conn.prepareStatement(sql);
            stmt.setDate(1, from);
            stmt.setDate(2, to);
            rs = stmt.executeQuery();
        }catch (SQLException e){
            System.err.println(e.getMessage());

        }
        return rs;
    }


    void executeUpdate(int UpdateID, String UpdateItem, String UpdateContent) throws Exception {
        try {
            conn = createConnection(getDburl(), getUsername(), getPassword());
            String sql = "update userinfo set " + UpdateItem + " = ? where id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, UpdateContent);
            stmt.setString(2, String.valueOf(UpdateID));
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    void setInvisible(int id) throws Exception {
        try{
            conn = createConnection(getDburl(), getUsername(), getPassword());
            String sql = "update post set visible = false where id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1,String.valueOf(id));
            stmt.executeUpdate();

        }catch (SQLException e){
            System.err.println(e.getMessage());
        }
    }

    void setVisible(int id) throws Exception {
        try{
            conn = createConnection(getDburl(), getUsername(), getPassword());
            String sql = "update post set visible = true where id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1,String.valueOf(id));
            stmt.executeUpdate();

        }catch (SQLException e){
            System.err.println(e.getMessage());
        }
    }


}
