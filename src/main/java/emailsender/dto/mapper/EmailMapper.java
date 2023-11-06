package emailsender.dto.mapper;

import org.springframework.stereotype.Component;

import emailsender.domain.email.Email;
import emailsender.domain.email.EmailId;
import emailsender.dto.EmailDTO;

@Component
public class EmailMapper {

    public EmailId idToValueObject(String id) {
        return new EmailId(id);
    }

    public EmailDTO toDto(Email email) {

        EmailDTO emailDTO = new EmailDTO();

        emailDTO.setId(email.getId().toString());
        emailDTO.setClientEmail(email.getClientEmail().toString());
        emailDTO.setTemplate(email.getTemplate().toString());
        emailDTO.setValues(email.getValues());
        emailDTO.setSubject(email.getSubject().toString());

        return emailDTO;
    }
}
