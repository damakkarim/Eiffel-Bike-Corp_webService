package Class;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;


public class EmailAlert {

    public static void sendEmail(String recipient, String subject, String body) {
        // Sender's email and app password
        final String senderEmail = "mahmoud.09092001@gmail.com";
        final String senderPassword = "mahmoudazizammar";

        // SMTP Server Properties
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
        properties.put("mail.debug", "true");

        // Create a session with the SMTP server
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            // Create the email message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject(subject);
            message.setText(body);

            // Send the email
            Transport.send(message);
            System.out.println("Email sent successfully to " + recipient);

        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Failed to send email: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        // Example usage
        sendEmail("siwar.nj1998@gmail.com ", "Macdooo", "salary 10$");
    }
}