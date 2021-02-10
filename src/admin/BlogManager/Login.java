package admin.BlogManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class Login implements ActionListener {
    private final JFrame jf;
    private final JLabel inputUserName;
    private final JLabel inputPassWord;
    private final JTextField userName;
    private final JPasswordField passWord;
    private final JButton login;
    private final JButton cancel;
    private final JCheckBox rememberMe;

    Login() {
        // set instance
        jf = new JFrame("Login");
        inputUserName = new JLabel("          user:");
        inputPassWord = new JLabel("password:");
        userName = new JTextField();
        passWord = new JPasswordField();
        login = new JButton("Login");
        cancel = new JButton("Cancel");
        rememberMe = new JCheckBox("Remember Me");

        //read admin user&pass
        try(BufferedReader br = new BufferedReader (new FileReader("user.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] a = line.split(",");
                userName.setText(a[0]);
                passWord.setText(a[1]);
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        // adjust GUI
        jf.setSize(400, 150);
        jf.setLocation(600, 400);

        jf.setLayout(new FlowLayout());

        userName.setPreferredSize(new Dimension(300, 30));
        passWord.setPreferredSize(new Dimension(300, 30));

        jf.getContentPane().add(inputUserName);
        jf.getContentPane().add(userName);
        jf.getContentPane().add(inputPassWord);
        jf.getContentPane().add(passWord);
        jf.getContentPane().add(login);
        jf.getContentPane().add(cancel);
        jf.getContentPane().add(rememberMe);

        jf.setResizable(false);

        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        login.addActionListener(this);
        cancel.addActionListener(this);

        jf.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(cancel)) {
            System.exit(0);
        }
        else if (e.getSource().equals(login)) {

            if (rememberMe.isSelected()){
                //if checked remember me , then save pass to txt
                File f=new File("user.txt");
                try(FileWriter txt=new FileWriter(f)) {
                    txt.write(userName.getText()+",");
                    txt.write(String.valueOf(passWord.getPassword()));
                } catch (IOException err) {
                    err.printStackTrace();
                }
            }

            if (userName.getText().equals("admin") && String.valueOf(passWord.getPassword()).equals("123")) {
                MySQLGUI myS = new MySQLGUI();
                myS.initial();
                jf.setVisible(false);
                jf.dispose();
            }
            else {
                JOptionPane.showOptionDialog(jf, "Incorrect username/password", "Failed",
                        JOptionPane.CLOSED_OPTION,
                        JOptionPane.ERROR_MESSAGE, null, null, null);
            }
        }
    }
}
