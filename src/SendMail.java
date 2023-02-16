/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.Properties;
import java.util.Random;

/**
 *
 * @author sagar
 */

import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message.RecipientType;

public class SendMail {

    SendMail() {
    }

    String d_host = "smtp.gmail.com",
            d_port = "465",
            d_email = "yash.radadiya1088@gmail.com",
            d_password = "7874895095",
            m_to,
            m_subject, m_text;

    InternetAddress[] recipientAddress = null;

    public String SendMail(String user_mail) {

        final int len = 8;
        String Small_chars = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";

        String add = "your varification code :";

        String values = Small_chars +
                numbers;

        Random rndm_method = new Random();

        char[] password = new char[len];
        for (int i = 0; i < len; i++) {

            password[i] = values.charAt(rndm_method.nextInt(values.length()));

        }

        String m_text1 = String.copyValueOf(password);
        System.out.println("----->" + add + m_text1);

        mailSender(user_mail, " OTP verification :", add, m_text1);
        return m_text1;
    }


    public void mailSender(String m_to, String m_subject, String add, String m_text) {

        this.m_subject = m_subject;
        this.m_text = add + "\n" + m_text;
        System.out.println(m_text);

        Properties props = new Properties();
        props.put("mail.smtp.user", d_email);
        props.put("mail.smtp.host", d_host);
        props.put("mail.smtp.port", d_port);
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.debug", "true");
        props.put("mail.smtp.socketFactory.port", d_port);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");

        SecurityManager security = System.getSecurityManager();

        try {
            Authenticator auth = new SMTPAuthenticator();
            Session session = Session.getInstance(props, auth);
            session.setDebug(true);
            MimeMessage msg = new MimeMessage(session);
            msg.setText(this.m_text);
            msg.setSubject(this.m_subject);
            msg.setFrom(new InternetAddress(d_email));

            InternetAddress recipientAddress = new InternetAddress(m_to);
//            int counter = 0;
//            for (int i = 0; i < m_to.length; i++) {
//                recipientAddress[i] = new InternetAddress(m_to[i]);
//            }

//message.addRecipients(Message.RecipientType.TO, mailAddress_TO);
            msg.setRecipient(Message.RecipientType.TO, recipientAddress);
            //  msg.setRecipients(Message.RecipientType.TO, address);

            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(m_text);
//            Multipart multipart = new MimeMultipart();
//            multipart.addBodyPart(messageBodyPart);
//            messageBodyPart = new MimeBodyPart();
//            DataSource source = new FileDataSource(filename);
//            messageBodyPart.setDataHandler(new DataHandler(source));
//            messageBodyPart.setFileName(filename);
//            multipart.addBodyPart(messageBodyPart);
//            msg.setContent(multipart);
            Transport transport = session.getTransport("smtp");
            transport.send(msg);
            //         Transport.send(msg);
        } catch (Exception mex) {
            mex.printStackTrace();
        }
    }

    private class SMTPAuthenticator extends javax.mail.Authenticator {

        public PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(d_email, d_password);
        }
    }
}

