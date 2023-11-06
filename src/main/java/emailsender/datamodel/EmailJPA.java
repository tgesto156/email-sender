package emailsender.datamodel;

import java.util.List;

import emailsender.domain.email.EmailId;
import emailsender.domain.value.Value;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "email")
@EqualsAndHashCode
public class EmailJPA {

    @EmbeddedId
    private EmailId emailId;
    private String emailTemplate;
    private String clientEmail;
    private String timestamp;
    private boolean send;
    private String subject;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "email_values", joinColumns = @JoinColumn(name = "emailId"))
    @AttributeOverride(name = "email_values", column = @Column(name = "values"))
    private List<Value> values;

    public EmailJPA(EmailId emailId, String emailTemplate, String clientEmail, List<Value> values, String timestamp,
            boolean send, String subject) {
        this.emailId = emailId;
        this.emailTemplate = emailTemplate;
        this.clientEmail = clientEmail;
        this.timestamp = timestamp;
        this.send = send;
        this.values = values;
        this.subject = subject;
    }
}
