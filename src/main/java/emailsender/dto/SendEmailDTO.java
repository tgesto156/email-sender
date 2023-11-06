package emailsender.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

import emailsender.domain.value.Value;


public class SendEmailDTO {
    
    @Getter
    @Setter
    private String template;
    @Getter
    @Setter
    private String clientEmail;
    @Getter
    @Setter
    private List<Value> values;
    @Getter
    @Setter
    private String subject;
    @Getter
    @Setter
    private String secretKey;
    @Getter
    @Setter
    private String timestamp;
}
