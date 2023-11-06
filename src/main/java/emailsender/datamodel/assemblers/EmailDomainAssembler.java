package emailsender.datamodel.assemblers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import emailsender.datamodel.EmailJPA;
import emailsender.domain.email.Email;
import emailsender.domain.email.EmailFactory;
import emailsender.domain.email.EmailId;

@Service
public class EmailDomainAssembler {
    
    @Autowired
    EmailFactory emailFactory;

    public EmailJPA toData(Email email){
        return new EmailJPA(new EmailId(email.getId().toString()), email.getTemplate().toString(), email.getClientEmail().toString(), email.getValues(), email.getTimestamp().toString(), email.isSend(), email.getSubject().toString());
    }

    public Email toDomain(EmailJPA emailJPA){
        return emailFactory.toDomain(emailJPA.getEmailId().toString(), emailJPA.getEmailTemplate(), emailJPA.getClientEmail(), emailJPA.getValues(), emailJPA.getTimestamp(), emailJPA.isSend(), emailJPA.getSubject());
    }
}
