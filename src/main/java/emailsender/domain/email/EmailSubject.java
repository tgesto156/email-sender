package emailsender.domain.email;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailSubject {
    private String subject;

    public EmailSubject(String subject) {
        if (subject != null && subject.length() <= 60) {
            this.subject = subject;
        } else {
            throw new IllegalArgumentException("Email subject must be a string with a maximum of 60 characters.");
        }
    }

    public String toString(){
        return subject;
    }
}