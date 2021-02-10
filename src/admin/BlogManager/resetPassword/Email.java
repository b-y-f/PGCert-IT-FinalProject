package admin.BlogManager.resetPassword;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


class SendEmail {
    public static void main(String [] args) throws MessagingException
    {
        Properties props = new Properties();
        props.setProperty("mail.debug", "true");
        props.setProperty("mail.smtp.auth", "true");
        props.setProperty("mail.smtp.port", "587");
        props.setProperty("mail.host", "smtp.gmail.com");
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.smtp.starttls.enable", "true");
        Session session = Session.getInstance(props,null);

        Message msg = new MimeMessage(session);
        try {
            msg.setSubject("Reset password");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        msg.setText("link to reset password");
        msg.setFrom(new InternetAddress("bbbbb8bbbbb@gmail.com"));

        Transport transport = session.getTransport();
        transport.connect("bbbbb8bbbbb@gmail.com", "muytgajdjjawtxcs");
        transport.sendMessage(msg, new Address[] {new InternetAddress("bbbbb8bbbbb@gmail.com")});
        transport.close();
    }
}
