package admin.BlogManager;

import admin.BlogManager.resetPassword.SendEmail;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MySQLGUI extends JFrame implements MouseListener {

    private JTabbedPane Base;
    private JPanel jp1, jp2, jp3, jp4, jp5,jp6,jp7;

    // set buttons
    private JButton InsertRecord, InsertReset, DeleteRecord, DeleteReset,
            queryPosts, UpdateRecord, UpdateReset,
            hidePostBtn, showPostBtn,
            getEmailByUsernameBtn, resetPasswordBtn,
            getPostJsonBtn, getCommentJsonBtn, importJsonBtn,
            jp7QueryPostsBtn;

    private JLabel insertUsername, insertPassword, insertNickname, insertDob, insertDiscretion,
            insertEmail, DeleteID1, UpdateID1, jp5UsernameLabel, jp5EmailLabel,jp7SelectDateInBetween,
            jp7FromDate,jp7ToDate;

    private JTextField insertUsernameText, insertPasswordText, insertNicknameText, insertDiscretionText, insertEmailText,
            DeleteID2, UpdateID2, UpdateContent, inputUsername, inputEmail;

    private JFormattedTextField insertDobText , jp7FromText, jp7ToText ;
    private ResultSetTable rsTable;


    private OperationMariadb db = null;
    private JScrollPane scroll = null , dateSelectPostScroll = null;
    private JComboBox<String> UpdateItem = null;

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");



    MySQLGUI() {
        setButton();
        setLabel();
        setTextField();
        setPanel();
        setLayout();
        setBase();
        setThis();
        setDB();
    }

    private void setButton() {
        // jp1 btn
        InsertRecord = new JButton("ADD");
        InsertRecord.setFont(new Font("Serif", 1, 20));
        InsertRecord.setBackground(Color.CYAN);
        InsertRecord.setBounds(150, 400, 100, 45);
        InsertRecord.setMargin(new Insets(0, 0, 0, 0));
        InsertReset = new JButton("RESET");
        InsertReset.setFont(new Font("Serif", 1, 20));
        InsertReset.setBackground(Color.CYAN);
        InsertReset.setBounds(300, 400, 100, 45);
        InsertReset.setMargin(new Insets(0, 0, 0, 0));
        // jp2 btn
        DeleteRecord = new JButton("DELETE");
        DeleteRecord.setFont(new Font("Serif", 1, 20));
        DeleteRecord.setBackground(Color.CYAN);
        DeleteRecord.setBounds(150, 350, 100, 45);
        DeleteRecord.setMargin(new Insets(0, 0, 0, 0));
        DeleteReset = new JButton("RESET");
        DeleteReset.setFont(new Font("Serif", 1, 20));
        DeleteReset.setBackground(Color.CYAN);
        DeleteReset.setBounds(300, 350, 100, 45);
        DeleteReset.setMargin(new Insets(0, 0, 0, 0));
        // jp3 btn
        UpdateRecord = new JButton("UPDATE");
        UpdateRecord.setFont(new Font("Serif", 1, 20));
        UpdateRecord.setBackground(Color.CYAN);
        UpdateRecord.setBounds(250, 400, 120, 45);
        UpdateReset = new JButton("RESET");
        UpdateReset.setFont(new Font("Serif", 1, 20));
        UpdateReset.setBackground(Color.CYAN);
        UpdateReset.setBounds(400, 400, 100, 45);
        // jp4 btn
        queryPosts = new JButton("Get Posts");
        queryPosts.setFont(new Font("Serif", 1, 20));
        queryPosts.setBackground(Color.GREEN);
        queryPosts.setBounds(600, 300, 150, 45);
        hidePostBtn = new JButton("Hide");
        hidePostBtn.setFont(new Font("Serif", 1, 20));
        hidePostBtn.setBackground(Color.CYAN);
        hidePostBtn.setBounds(600, 350, 150, 45);


        showPostBtn = new JButton("Show");
        showPostBtn.setFont(new Font("Serif", 1, 20));
        showPostBtn.setBackground(Color.CYAN);
        showPostBtn.setBounds(600, 400, 150, 45);

        //5 btn
        getEmailByUsernameBtn = new JButton("Get Email");
        getEmailByUsernameBtn.setFont(new Font("Serif", 1, 20));
        getEmailByUsernameBtn.setBackground(Color.CYAN);
        getEmailByUsernameBtn.setBounds(600, 30, 150, 45);

        resetPasswordBtn = new JButton("Reset Password");
        resetPasswordBtn.setFont(new Font("Serif", 1, 16));
        resetPasswordBtn.setBackground(Color.CYAN);
        resetPasswordBtn.setBounds(600, 90, 150, 45);

        //6 btn
        getPostJsonBtn = new JButton("post => json");
        getPostJsonBtn.setFont(new Font("Serif", 0, 20));
        getPostJsonBtn.setBackground(Color.orange);
        getPostJsonBtn.setBounds(250, 30, 200, 45);

        getCommentJsonBtn = new JButton("comment => json");
        getCommentJsonBtn.setFont(new Font("Serif", 0, 20));
        getCommentJsonBtn.setBackground(Color.orange);
        getCommentJsonBtn.setBounds(250, 90, 200, 45);

        importJsonBtn = new JButton("import");
        importJsonBtn.setFont(new Font("Serif", 0, 20));
        importJsonBtn.setBackground(Color.PINK);
        importJsonBtn.setBounds(250, 150, 200, 45);

        //7 btn
        jp7QueryPostsBtn = new JButton("Search");
        jp7QueryPostsBtn.setFont(new Font("Serif", 0, 20));
        jp7QueryPostsBtn.setBackground(Color.cyan);
        jp7QueryPostsBtn.setBounds(100, 200, 100, 45);


    }

    private void setLabel() {
        // jp1 tag
        insertUsername = new JLabel("Username: ");
        insertUsername.setFont(new Font("Dialog", 1, 20));
        insertUsername.setBounds(80, 25, 150, 50);

        insertPassword = new JLabel("Password: ");
        insertPassword.setFont(new Font("Dialog", 1, 20));
        insertPassword.setBounds(80, 75, 150, 50);

        insertNickname = new JLabel("Nickname: ");
        insertNickname.setFont(new Font("Dialog", 1, 20));
        insertNickname.setBounds(80, 125, 150, 50);

        insertDob = new JLabel("Date of Birth: ");
        insertDob.setFont(new Font("Dialog", 1, 20));
        insertDob.setBounds(80, 175, 150, 50);

        insertDiscretion = new JLabel("Description: ");
        insertDiscretion.setFont(new Font("Dialog", 1, 20));
        insertDiscretion.setBounds(80, 225, 150, 50);

        insertEmail = new JLabel("E-mail: ");
        insertEmail.setFont(new Font("Dialog", 1, 20));
        insertEmail.setBackground(Color.GREEN);
        insertEmail.setBounds(80, 275, 150, 50);


        // jp2 label
        DeleteID1 = new JLabel("User ID：");
        DeleteID1.setBounds(100, 100, 100, 50);
        DeleteID1.setFont(new Font("Dialog", 1, 20));


        // jp3 label dropdown
        UpdateID1 = new JLabel("User ID：");
        UpdateID1.setFont(new Font("Dialog", 1, 20));
        UpdateID1.setBounds(200, 60, 120, 50);
        UpdateItem = new JComboBox<>();
        UpdateItem.setFont(new Font("Dialog", 1, 20));
        UpdateItem.setBounds(150, 200, 150, 45);
        UpdateItem.addItem("Username");
        UpdateItem.addItem("Nickname");
        UpdateItem.addItem("Email");
        UpdateItem.addItem("Description");

        // jp4 label


        // jp5 label
        jp5UsernameLabel = new JLabel("Username: ");
        jp5UsernameLabel.setFont(new Font("Dialog", 1, 20));
        jp5UsernameLabel.setBounds(80, 25, 150, 50);

        jp5EmailLabel = new JLabel("Email: ");
        jp5EmailLabel.setFont(new Font("Dialog", 1, 20));
        jp5EmailLabel.setBounds(80, 85, 150, 50);

        //jp7 label
        jp7SelectDateInBetween = new JLabel("Please select a date in between");
        jp7SelectDateInBetween.setFont(new Font("Dialog", 1, 20));
        jp7SelectDateInBetween.setBounds(25, 10, 300, 50);

        jp7FromDate = new JLabel("From");
        jp7FromDate.setFont(new Font("Dialog", 0, 20));
        jp7FromDate.setBounds(25, 60, 300, 50);

        jp7ToDate = new JLabel("To");
        jp7ToDate.setFont(new Font("Dialog", 0, 20));
        jp7ToDate.setBounds(25, 120, 300, 50);


    }

    private void setTextField() {

        //tag1 add user information
        insertUsernameText = new JTextField();
        insertUsernameText.setFont(new Font("Serif", 1, 23));
        insertUsernameText.setBounds(210, 30, 250, 35);

        insertPasswordText = new JTextField();
        insertPasswordText.setFont(new Font("Serif", 1, 23));
        insertPasswordText.setBounds(210, 80, 250, 35);

        insertNicknameText = new JTextField();
        insertNicknameText.setFont(new Font("Serif", 1, 23));
        insertNicknameText.setBounds(210, 130, 250, 35);


        //date selector
        insertDobText = new JFormattedTextField(dateFormat);
        insertDobText.setFont(new Font("Serif", 1, 23));
        insertDobText.setBounds(210, 180, 250, 35);
        insertDobText.setValue(new Date());

        insertDiscretionText = new JTextField();
        insertDiscretionText.setFont(new Font("Serif", 1, 23));
        insertDiscretionText.setBounds(210, 230, 250, 35);

        insertEmailText = new JTextField();
        insertEmailText.setFont(new Font("Serif", 1, 23));
        insertEmailText.setBounds(210, 280, 250, 35);


        //tag 2 delete


        DeleteID2 = new JTextField("Pls input user id you want delete");
        DeleteID2.setFont(new Font("Dialog", 1, 20));
        DeleteID2.setBounds(210, 100, 350, 50);

        UpdateID2 = new JTextField();
        UpdateID2.setFont(new Font("Dialog", 1, 20));
        UpdateID2.setBounds(310, 60, 200, 45);

        UpdateContent = new JTextField("Pls input user id you want update");
        UpdateContent.setFont(new Font("Dialog", 0, 13));
        UpdateContent.setBounds(310, 200, 200, 45);

        //tag 3


        JTextArea queryRecordResult = new JTextArea("Result：");
        queryRecordResult.setFont(new Font("Dialog", 1, 20));

        queryRecordResult.setEditable(false);
        queryRecordResult.setLineWrap(true);
        scroll = new JScrollPane(queryRecordResult);
        scroll.setBounds(30, 30, 560, 260);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        //tag 5
        inputUsername = new JTextField();
        inputUsername.setFont(new Font("Serif", 1, 18));
        inputUsername.setBounds(210, 30, 250, 35);

        inputEmail = new JTextField();
        inputEmail.setFont(new Font("Serif", 1, 18));
        inputEmail.setBounds(210, 90, 250, 35);

        //tag 7
        jp7FromText = new JFormattedTextField(dateFormat);
        jp7FromText.setFont(new Font("Serif", 1, 23));
        jp7FromText.setBounds(100, 60, 220, 35);
        jp7FromText.setValue(new Date());

        jp7ToText = new JFormattedTextField(dateFormat);
        jp7ToText.setFont(new Font("Serif", 1, 23));
        jp7ToText.setBounds(100, 140, 220, 35);
        jp7ToText.setValue(new Date());

        JTextArea queryPostsResult = new JTextArea("Result：");
        queryPostsResult.setFont(new Font("Dialog", 1, 20));
        queryPostsResult.setEditable(false);
        queryPostsResult.setLineWrap(true);

        dateSelectPostScroll = new JScrollPane(queryPostsResult);
        dateSelectPostScroll.setBounds(350, 30, 380, 420);
        dateSelectPostScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        dateSelectPostScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);


    }

    private void setPanel() {
        jp1 = new JPanel();
        jp2 = new JPanel();
        jp3 = new JPanel();
        jp4 = new JPanel();
        jp5 = new JPanel();
        jp6 = new JPanel();
        jp7 = new JPanel();
    }

    private void setLayout() {
        // jp1
        jp1.setLayout(null);
        jp1.add(InsertRecord);
        jp1.add(InsertReset);

        //add label to Jpanel
        jp1.add(insertUsername);
        jp1.add(insertPassword);
        jp1.add(insertNickname);
        jp1.add(insertDob);
        jp1.add(insertDiscretion);
        jp1.add(insertEmail);

        //add text feild to Jpanel
        jp1.add(insertUsernameText);
        jp1.add(insertPasswordText);
        jp1.add(insertNicknameText);
        jp1.add(insertDobText);
        jp1.add(insertDiscretionText);
        jp1.add(insertEmailText);

        // jp2
        jp2.setLayout(null);
        jp2.add(DeleteID1);
        jp2.add(DeleteID2);
        jp2.add(DeleteRecord);
        jp2.add(DeleteReset);

        //  jp3
        jp3.setLayout(null);
        jp3.add(UpdateID1);
        jp3.add(UpdateID2);
        jp3.add(UpdateItem);
        jp3.add(UpdateContent);
        jp3.add(UpdateRecord);
        jp3.add(UpdateReset);


        //  jp4 paneladd
        jp4.setLayout(null);
        jp4.add(scroll);
        jp4.add(queryPosts);
        jp4.add(hidePostBtn);
        jp4.add(showPostBtn);

        //jp5
        jp5.setLayout(null);
        jp5.add(jp5UsernameLabel);
        jp5.add(inputUsername);

        jp5.add(jp5EmailLabel);
        jp5.add(inputEmail);

        jp5.add(getEmailByUsernameBtn);
        jp5.add(resetPasswordBtn);

        jp6.setLayout(null);
        jp6.add(getPostJsonBtn);
        jp6.add(getCommentJsonBtn);
        jp6.add(importJsonBtn);

        //jp7
        jp7.setLayout(null);
        jp7.add(jp7SelectDateInBetween);
        jp7.add(jp7FromDate);
        jp7.add(jp7FromText);
        jp7.add(jp7ToDate);
        jp7.add(jp7ToText);
        jp7.add(jp7QueryPostsBtn);
        jp7.add(dateSelectPostScroll);


    }

    private void setBase() {
        Base = new JTabbedPane(JTabbedPane.TOP);
        Base.addTab("Add User", jp1);
        Base.addTab("Remove User", jp2);
        Base.addTab("Update User", jp3);
        Base.addTab("Article Hide", jp4);
        Base.addTab("Reset Password", jp5);
        Base.addTab("Ex/Import with Jackson",jp6);
        Base.addTab("Select between Dates",jp7);
    }

    private void setThis() {
        this.add(Base);
        this.setTitle("Blog Management System");
        this.setLocation(300, 200);
        this.setSize(800, 550);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
    }

    private void setDB() {
        db = new OperationMariadb();
        db.setDburl("jdbc:mariadb://db.trex-sandwich.com:3306/yb55");
        db.setDbdriver("org.mariadb.jdbc.Driver");
        db.setUsername("yb55");
        db.setPassword("3219159");
    }


    void initial() {
        InsertRecord.addMouseListener(this);
        InsertReset.addMouseListener(this);
        DeleteRecord.addMouseListener(this);
        DeleteReset.addMouseListener(this);
        queryPosts.addMouseListener(this);
        UpdateRecord.addMouseListener(this);
        UpdateReset.addMouseListener(this);

        hidePostBtn.addMouseListener(this);
        showPostBtn.addMouseListener(this);

        getEmailByUsernameBtn.addMouseListener(this);
        resetPasswordBtn.addMouseListener(this);

        getPostJsonBtn.addMouseListener(this);
        getCommentJsonBtn.addMouseListener(this);

        importJsonBtn.addMouseListener(this);

        jp7QueryPostsBtn.addMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        if (e.getSource().equals(InsertReset)) {
            insertUsernameText.setText("");
            insertPasswordText.setText("");
            insertNicknameText.setText("");
            insertDobText.setText("");
            insertDiscretionText.setText("");
            insertEmailText.setText("");
        } else if (e.getSource().equals(InsertRecord)) {

            String inUsername = insertUsernameText.getText();
            String inPassword = insertPasswordText.getText();
            //convert to hashed passwrd
            String passwordHashed = BCrypt.hashpw(inPassword, BCrypt.gensalt(10));
            String inNickname = insertNicknameText.getText();
            String inDob = insertDobText.getText();
            String inDescription = insertDiscretionText.getText();
            String inEmail = insertEmailText.getText();


            try {
                java.sql.Date sqlDob = new java.sql.Date(dateFormat.parse(inDob).getTime());
                db.setRs(db.executeQuery());
                if (db.getRs().next()) {
                    db.executeInsert(inUsername, passwordHashed, inNickname, sqlDob, inDescription, inEmail);
                    JOptionPane.showOptionDialog(this, "Added Successful！", "Database Notice",
                            JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
                } else JOptionPane.showOptionDialog(this, "Add failed", "Database Notice",
                        -1, 1, null, null, null);
            } catch (Exception exception) {
                exception.printStackTrace();
            } finally {
                db.CloseRS();
                db.CloseStmt();
                db.CloseConnection();
            }
        } else if (e.getSource().equals(DeleteReset)) {

            DeleteID2.setText("");
            DeleteID2.setFont(new Font("Dialog", 1, 25));

        } else if (e.getSource().equals(DeleteRecord)) {

            int deleteUserId = Integer.parseInt(DeleteID2.getText());
            try {
                if (db.findUserById(deleteUserId).first()) {
                    db.setRs(db.findUserById(deleteUserId));
                    db.executeDelete(deleteUserId);
                    JOptionPane.showOptionDialog(this, "Deleted！", "Database Notice",
                            -1, 1, null, null, null);
                } else JOptionPane.showOptionDialog(this, "Delete Failed, Invalid userID", "Database Notice",
                        -1, 1, null, null, null);
            } catch (Exception exception) {
                exception.printStackTrace();
            }

        } else if (e.getSource().equals(UpdateReset)) {

            UpdateID2.setText("");
            UpdateID2.setFont(new Font("Serif", 1, 20));
            UpdateContent.setText("");
            UpdateContent.setFont(new Font("Serif", 1, 20));

            //update DB with selected column;
        } else if (e.getSource().equals(UpdateRecord)) {

            int updateUserId = Integer.parseInt(UpdateID2.getText());
            try {
                db.setRs(db.executeQuery());
                if (!db.findUserById(updateUserId).first()) {
                    JOptionPane.showOptionDialog(this, "No record cant update!!",
                            "Database Notice", JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE,
                            null, null, null);
                } else {
                    //update single column
                    String updateItem = null;
                    if (UpdateItem.getSelectedItem().toString().equals("Username")) {
                        updateItem = "username";
                    } else if (UpdateItem.getSelectedItem().toString().equals("Nickname")) {
                        updateItem = "name";
                    } else if (UpdateItem.getSelectedItem().toString().equals("Email")) {
                        updateItem = "email";
                    } else if (UpdateItem.getSelectedItem().toString().equals("Description")) {
                        updateItem = "description";
                    }
                    db.executeUpdate(updateUserId, updateItem, UpdateContent.getText());
                    JOptionPane.showOptionDialog(this, "Update successful！", "Database Notice",
                            -1, 1, null, null, null);
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            } finally {
                db.CloseRS();
                db.CloseStmt();
                db.CloseConnection();
            }
        } else if (e.getSource().equals(queryPosts)) {
            try {
                //generate Jtable and pop data to it
                if (rsTable != null) {
                    jp4.remove(rsTable);

                }
                db.setRs(db.getAllPosts());

                rsTable = new ResultSetTable(db.getRs());
                rsTable.setFillsViewportHeight(true);
                scroll.setViewportView(rsTable);

                jp4.revalidate();
                jp4.repaint();

                JOptionPane.showOptionDialog(this, "Got data", "Database Notice",
                        -1, 1, null, null, null);


            } catch (Exception e1) {
                e1.printStackTrace();
            } finally {
                db.CloseRS();
                db.CloseStmt();
                db.CloseConnection();
            }
        } else if (e.getSource().equals(hidePostBtn)) {
            //update selected post to invisible in table and database
            try {
                //generate Jtable and pop data to it
                for (int i = 0; i < rsTable.getRowCount(); i++) {
                    boolean isChecked = Boolean.parseBoolean(rsTable.getValueAt(i, 6).toString());
                    int id = (int) rsTable.getValueAt(i, 0);

                    if (isChecked) {
                        //set value to false
                        rsTable.setValueAt(Boolean.FALSE, i, 6);
                        //update to db
                        db.setInvisible(id);
                    }
                }
                jp4.revalidate();
                jp4.repaint();


                JOptionPane.showOptionDialog(this, "Done ", "Database Notice",
                        -1, 1, null, null, null);

            } catch (Exception e1) {
                e1.printStackTrace();
            } finally {
                db.CloseRS();
                db.CloseStmt();
                db.CloseConnection();
            }
        } else if (e.getSource().equals(showPostBtn)) {
            try {
                for (int i = 0; i < rsTable.getRowCount(); i++) {
                    boolean isChecked = Boolean.parseBoolean(rsTable.getValueAt(i, 6).toString());
                    int id = (int) rsTable.getValueAt(i, 0);

                    if (isChecked) {
                        //set value to false
                        rsTable.setValueAt(Boolean.TRUE, i, 6);
                        //update to db
                        db.setVisible(id);
                    }
                }
                jp4.revalidate();
                jp4.repaint();

                JOptionPane.showOptionDialog(this, "Done!", "Database Notice",
                        -1, 1, null, null, null);

            } catch (Exception e1) {
                e1.printStackTrace();
            } finally {
                db.CloseRS();
                db.CloseStmt();
                db.CloseConnection();
            }
        } else if (e.getSource().equals(getEmailByUsernameBtn)) {
            try {
                ResultSet emailRs = db.getEmailByUsername(inputUsername.getText());

                if (emailRs.next()) {
                    String email = emailRs.getString("email");
                    inputEmail.setText(email);
                } else {
                    JOptionPane.showOptionDialog(this, "Fail, wrong username!", "Database Notice",
                            -1, 1, null, null, null);
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            } finally {
                db.CloseRS();
                db.CloseStmt();
                db.CloseConnection();
            }
        }else if (e.getSource().equals(resetPasswordBtn)) {
            try {
                String receiver = inputEmail.getText();
                String receiverUsername = inputUsername.getText();
                SendEmail resetEmail = new SendEmail(receiver);
                resetEmail.send(receiverUsername);


            } catch (Exception e1) {
                e1.printStackTrace();
            } finally {
                db.CloseRS();
                db.CloseStmt();
                db.CloseConnection();
            }
        }else if (e.getSource().equals(getPostJsonBtn)) {
            try {
                ResultSet posts = db.getAllPosts();
                JsonConverter jc = new JsonConverter();
                jc.exportToJson("post",posts);
                JOptionPane.showOptionDialog(this, "Good,file can be found in post-files folder!", "Export Notice",
                        -1, 1, null, null, null);

            } catch (Exception e1) {
                e1.printStackTrace();
            } finally {
                db.CloseRS();
                db.CloseStmt();
                db.CloseConnection();
            }
        }else if (e.getSource().equals(getCommentJsonBtn)) {
            try {
                ResultSet comments = db.getAllComments();
                JsonConverter jc = new JsonConverter();
                jc.exportToJson("comments",comments);
                JOptionPane.showOptionDialog(this, "Good,file can be found in post-files folder!", "Export Notice",
                        -1, 1, null, null, null);

            } catch (Exception e1) {
                e1.printStackTrace();
            } finally {
                db.CloseRS();
                db.CloseStmt();
                db.CloseConnection();
            }
        }else if (e.getSource().equals(importJsonBtn)) {
            try {

                File fc = new MyFileChooser().getFile();
                if (fc!=null){
                    new JsonConverter().importFromJson(fc);
                    JOptionPane.showOptionDialog(this, "Json converted and will print in console!", "Export Notice",
                            -1, 1, null, null, null);
                }

            } catch (Exception e1) {
                e1.printStackTrace();
            }
        } else if (e.getSource().equals(jp7QueryPostsBtn)) {
            try {
                //generate Jtable and pop data to it
                if (rsTable != null) {
                    jp7.remove(rsTable);

                }

                Date parsedFrom = dateFormat.parse(jp7FromText.getText());
                Date parsedTo = dateFormat.parse(jp7ToText.getText());

                java.sql.Date sqlFrom = new java.sql.Date(parsedFrom.getTime());
                java.sql.Date sqlTo = new java.sql.Date(parsedTo.getTime());

                db.setRs(db.getPostsBetween(sqlFrom,sqlTo));
                rsTable = new ResultSetTable(db.getRs());
                rsTable.setFillsViewportHeight(true);
                dateSelectPostScroll.setViewportView(rsTable);

                jp7.revalidate();
                jp7.repaint();

                JOptionPane.showOptionDialog(this, "Got data", "Database Notice",
                        -1, 1, null, null, null);


            } catch (Exception e1) {
                e1.printStackTrace();
            } finally {
                db.CloseRS();
                db.CloseStmt();
                db.CloseConnection();
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }


}
