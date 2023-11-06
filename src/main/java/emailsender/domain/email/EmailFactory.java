package emailsender.domain.email;

import java.util.List;

import emailsender.domain.value.Value;
import emailsender.dto.SendEmailDTO;
import org.springframework.stereotype.Component;

import emailsender.utils.Timestamp;
import emailsender.utils.UUIDGenerator;

@Component
public class EmailFactory {

    public Email createEmail(SendEmailDTO emailDTO) {

        EmailId emailId = new EmailId(UUIDGenerator.generateUUID().toString());
        EmailTemplate emailTemplate = EmailTemplate.valueOf(emailDTO.getTemplate());
        ClientEmail clientEmail = new ClientEmail(emailDTO.getClientEmail());
        List<Value> values = emailDTO.getValues();
        Timestamp timestamp = null;
        if (emailDTO.getTimestamp() != null) {
            timestamp = new Timestamp(emailDTO.getTimestamp());
        } else {
            timestamp = new Timestamp();
        }
        boolean send = false;
        EmailSubject emailSubject = new EmailSubject(emailDTO.getSubject());

        return new Email(emailId, emailTemplate, clientEmail, values, timestamp, send, emailSubject);
    }

    public Email toDomain(String id, String template, String email, List<Value> values, String emailTimestamp,
            boolean send, String subject) {
        EmailId emailId = new EmailId(id);
        EmailTemplate emailTemplate = EmailTemplate.valueOf(template);
        ClientEmail clientEmail = new ClientEmail(email);
        Timestamp timestamp = new Timestamp(emailTimestamp);
        EmailSubject emailSubject = new EmailSubject(subject);

        return new Email(emailId, emailTemplate, clientEmail, values, timestamp, send, emailSubject);
    }

}
