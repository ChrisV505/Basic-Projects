//Import core dotenv class
import io.github.cdimascio.dotenv.Dotenv;

// Import Java utility properties for SMTP settings
import java.util.Properties;

// Import core JavaMail classes
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;

// Import Internet-specific classes to make email
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class App {
    public static void main(String[] args) throws Exception {
        //create new Dotenv instance
        Dotenv dotenv = Dotenv.load();

        // Your Gmail credentials (email + generated app password)
        final String username = dotenv.get("GMAIL_USERNAME"); //get file from .env file using Dotenv import
        final String password = dotenv.get("GMAIL_PASSWORD"); // get password from .env file 

        // Recipient email
        final String to = "christophervalle3339@gmail.com";

        // Email content setup
        String subject = "Testing .env file to hide credintials"; // Email subject
        String messageText = "This email was sent using JavaMail API"; // Email body

        // Configure SMTP settings using Gmail's SMTP server
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true"); // Enable authentication
        props.put("mail.smtp.starttls.enable", "true"); // Enable STARTTLS (for encryption)
        props.put("mail.smtp.host", "smtp.gmail.com"); // Gmail SMTP server
        props.put("mail.smtp.port", "587"); // Corrected key: use "port" not "Properties"

        // Create a mail session with authentication
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                // Provide username and password for authentication
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Create the email message
            Message message = new MimeMessage(session); // MimeMessage allows email headers, body, etc.
            message.setFrom(new InternetAddress(username)); // Set sender address
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to)); // Set recipient
            message.setSubject(subject); // Set subject line
            message.setText(messageText); // Set plain text body

            // Send the email
            Transport.send(message);
            System.out.println("Email sent successfully!");
        } catch (MessagingException e) {
            // Handle errors (bad connection, auth, etc.)
            e.printStackTrace();
        }
    }
}