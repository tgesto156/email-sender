package emailsender.services;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

import emailsender.domain.email.EmailFactory;
import emailsender.domain.value.Value;
import emailsender.dto.SendEmailDTO;
import emailsender.repositories.implementation.EmailRepo;
import emailsender.utils.Timestamp;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import emailsender.domain.email.Email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class EmailService {

    EmailFactory emailFactory;
    EmailRepo emailRepo;
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    public EmailService(EmailFactory emailFactory, EmailRepo emailRepo) {
        this.emailFactory = emailFactory;
        this.emailRepo = emailRepo;
    }

    /**
     * Generates the email content by replacing placeholders in an email template with
     * the provided values in the SendEmailDTO.
     *
     * @param sendEmailDTO The SendEmailDTO containing the template and values.
     * @return A string representing the email content with placeholders replaced by values.
     */
    private String generateEmailContent(SendEmailDTO sendEmailDTO) {
        String path = "src\\main\\java\\emailsender\\templates\\".concat(sendEmailDTO.getTemplate())
                .concat(".html");
        String htmlContent = loadHtmlFile(path);
        List<Value> values = sendEmailDTO.getValues();

        for (Value value : values) {
            String placeholder = "{" + value.getValueType().toString() + "}";
            String replacement = value.getValueString().toString();
            htmlContent = htmlContent.replace(placeholder, replacement);
        }

        return htmlContent;
    }

    /**
     * Generates the email content by replacing placeholders in an email template with
     * the provided values in the Email object.
     *
     * @param email The Email object containing the template and values.
     * @return A string representing the email content with placeholders replaced by values.
     */
    private String generateEmailContent(Email email) {
        String path = "src\\main\\java\\emailsender\\templates\\".concat(email.getTemplate().toString())
                .concat(".html");
        String htmlContent = loadHtmlFile(path);
        List<Value> values = email.getValues();

        for (Value value : values) {
            String placeholder = "{" + value.getValueType().toString() + "}";
            String replacement = value.getValueString().toString();
            htmlContent = htmlContent.replace(placeholder, replacement);
        }

        return htmlContent;
    }

    /**
     * Loads and reads the configuration properties from the "config.properties" file.
     *
     * @return A Properties object containing the configuration properties.
     */
    private Properties loadConfigProperties() {
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream("config.properties")) {
            properties.load(input);
        } catch (IOException e) {
            // Handle file loading exception
            e.printStackTrace();
        }
        return properties;
    }

    /**
     * Loads and reads the content of an HTML file specified by the given file path.
     *
     * @param filePath The path to the HTML file to be loaded.
     * @return A string containing the content of the HTML file.
     */
    private String loadHtmlFile(String filePath) {
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                contentBuilder.append(line);
            }
        } catch (IOException e) {
            // Handle file loading exception
        }
        return contentBuilder.toString();
    }

    /**
     * Sends an email using the provided SendEmailDTO object and the AWS SES service
     *
     * @param sendEmailDTO The SendEmailDTO object containing email details.
     *                     It should include client email, subject, template, values, secret key, and optional timestamp.
     */
    public void sendMail(SendEmailDTO sendEmailDTO) {
        if (sendEmailDTO.getTimestamp() == null) {
            Properties configProperties = loadConfigProperties();
            String access_key = configProperties.getProperty("access_key");
            String password = configProperties.getProperty("password");
            String email_from = configProperties.getProperty("email_from");
            String server = configProperties.getProperty("server");
            String secret = configProperties.getProperty("secret");
            if (secret.equals(sendEmailDTO.getSecretKey())) {
                try {
                    Properties props = new Properties();
                    props.put("mail.smtp.auth", "true");
                    props.put("mail.smtp.starttls.enable", "true");
                    props.put("mail.smtp.host", server);
                    props.put("mail.smtp.port", "587");

                    Session session = Session.getInstance(props, new Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(access_key, password);
                        }
                    });

                    MimeMessage message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(email_from));
                    message.setRecipients(Message.RecipientType.TO,
                            InternetAddress.parse(sendEmailDTO.getClientEmail()));
                    message.setSubject(sendEmailDTO.getSubject(), "UTF-8");

                    String htmlContent = generateEmailContent(sendEmailDTO);
                    message.setContent(htmlContent, "text/html; charset=UTF-8");

                    Transport.send(message);

                    logger.info("Email sent successfully");
                    saveEmail(sendEmailDTO);
                } catch (AuthenticationFailedException e) {
                    logger.error("Authentication failed. Please check your username and password.", e);
                } catch (MessagingException e) {
                    logger.error("An error occurred while sending the email.", e);
                }
            } else {
                logger.error("Exception occurred while sending email: Wrong Secret");
            }
        } else {
            saveScheduledEmails(sendEmailDTO);
            logger.error("Email will be sent at: " + sendEmailDTO.getTimestamp());
        }
    }

    /**
     * Sends a scheduled email using the provided Email object at the specified timestamp.
     *
     * @param email The Email object containing email details, including client email, subject,
     *              template, values, and timestamp for scheduling.
     */
    public void sendScheduledEmails(Email email) {
        Timestamp timestamp = new Timestamp(email.getTimestamp().getTimestamp());
        String log = "Email will be sent at: " + timestamp.getTimestamp();
        logger.info(log);
        Properties configProperties = loadConfigProperties();
        String access_key = configProperties.getProperty("access_key");
        String password = configProperties.getProperty("password");
        String email_from = configProperties.getProperty("email_from");
        String server = configProperties.getProperty("server");
        try {
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", server);
            props.put("mail.smtp.port", "587");

            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(access_key, password);
                }
            });

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(email_from));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(email.getClientEmail().getEmail()));
            message.setSubject(email.getSubject().getSubject(), "UTF-8");

            String htmlContent = generateEmailContent(email);
            message.setContent(htmlContent, "text/html; charset=UTF-8");

            Transport.send(message);

            logger.info("Email sent successfully");
            SendEmailDTO sendEmailDTO = new SendEmailDTO();
            sendEmailDTO.setClientEmail(htmlContent);
            saveScheduledEmailsAfterSending(email);
        } catch (AuthenticationFailedException e) {
            logger.error("Authentication failed. Please check your username and password.", e);
        } catch (MessagingException e) {
            logger.error("An error occurred while sending the email.", e);
        }
    }

    /**
     * Saves the email details from the provided SendEmailDTO as a new Email entity.
     *
     * @param sendEmailDTO The SendEmailDTO object containing email details to be saved.
     */
    public void saveEmail(SendEmailDTO sendEmailDTO) {
        Email email = emailFactory.createEmail(sendEmailDTO);
        email.setSend(true);
        emailRepo.save(email);
    }

    /**
     * Saves the email details from the provided SendEmailDTO as a new Email entity for scheduling.
     *
     * @param sendEmailDTO The SendEmailDTO object containing email details to be saved for future scheduling.
     */
    public void saveScheduledEmails(SendEmailDTO sendEmailDTO) {
        Email email = emailFactory.createEmail(sendEmailDTO);
        emailRepo.save(email);
    }

    /**
     * Marks the scheduled email, specified by the provided Email object, as sent and updates its status.
     *
     * @param email The Email object representing the scheduled email to be marked as sent.
     */
    public void saveScheduledEmailsAfterSending(Email email) {
        email.setSend(true);
        emailRepo.save(email);
    }

    /**
     * Periodically checks and sends scheduled emails based on the specified fixed rate.
     * This method retrieves all scheduled emails from the repository, checks if they are due
     * for sending, and sends them if conditions are met.
     */
    @Scheduled(fixedRate = 30000)
    public void scheduledEmailSender() {
        List<Email> allEmails = emailRepo.findAll();
        System.out.println(allEmails.size());
        for (Email email : allEmails) {
            if (email.getTimestamp().isBefore(new Timestamp()) && !email.isSend()) {
                sendScheduledEmails(email);
            }
        }
    }
}