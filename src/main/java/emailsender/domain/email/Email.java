package emailsender.domain.email;

import java.util.ArrayList;
import java.util.List;

import emailsender.domain.value.Value;
import emailsender.utils.Timestamp;
import emailsender.valueobjects.Entity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode
@Getter
@Setter
public class Email implements Entity<Email> {
    
    private final EmailId id;
    private final EmailTemplate template;
    private final ClientEmail clientEmail;
    private final List<Value> values;
    private final Timestamp timestamp;
    private boolean send;
    private final EmailSubject subject;
    
    protected Email(EmailId id, EmailTemplate template, ClientEmail clientEmail, List<Value> values, Timestamp timestamp, boolean send, EmailSubject subject){
        this.id = id;
        this.template = template;
        this.clientEmail = clientEmail;
        this.values = values;
        this.timestamp = timestamp;
        this.send = send;
        this.subject = subject;
    }

    @Override
    public boolean sameIdentityAs(Email other) {
        return other !=null && this.id.equals(other.id);
    }

    public List<String> getValuesAsStringList() {
        List<String> stringList = new ArrayList<>();
        for (Value value : values) {
            stringList.add(value.toString());
        }
        return stringList;
    }
}
