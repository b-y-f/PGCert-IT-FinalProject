package admin.BlogManager.resetPassword;


import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class SendEmail {
    //my email
    private final String USERNAME = "bbbbb8bbbbb@gmail.com";
    private final String PASSWORD = "muytgajdjjawtxcs";

    private Message msg;
    private String resetLink;


    public SendEmail(String toWho) throws MessagingException {

        Properties props = new Properties();
        props.setProperty("mail.debug", "true");
        props.setProperty("mail.smtp.auth", "true");
        props.setProperty("mail.smtp.port", "587");
        props.setProperty("mail.host", "smtp.gmail.com");
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.smtp.starttls.enable", "true");//TLS crypto
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSWORD);
            }
        });

        this.msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("bbbbb8bbbbb@gmail.com"));
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(toWho));


    }

    //for crypto :secret code(sc)
    public void setResetLink() {

    }

    public void send(String username) {
        try {
            msg.setSubject("Reset password");
            msg.setContent(
                    "Dear " + username + " , hello: <br/><br/>"
                            + "You request reset password at " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "<br/><br/>"
                            + "Please open the link to reset：<br/><br/>"
                            + "<a href=http://localhost:3000/" + "resetPwd?sc=" + username + ">" +
                            "http://localhost:3000/resetPwd?sc=" + username +
                            "</a><br/><br/>"
                            + "Thank you !" + "<br/><br/>"
                            + "If you did not request a password reset you can safely ignore this email.", "text/html"
            );
            Transport.send(msg);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }


}
