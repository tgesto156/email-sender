package emailsender.domain.email;

import java.util.Objects;

import emailsender.exceptions.ExceptionMessage;
import emailsender.valueobjects.ValueObject;
import emailsender.utils.ArgumentValidators;
import lombok.Getter;

public class EmailId implements ValueObject<EmailId> {

    @Getter
    private String id;

    public EmailId(String id){
        ArgumentValidators.validateNullOrEmptyString(id, ExceptionMessage.INVALID_EMAIL_ID);
        this.id = id;
    }

    public EmailId() {
        // Default constructor required for embeddable objects
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof EmailId)) {
            return false;
        }
        EmailId emailId = (EmailId) o;
        return Objects.equals(id, emailId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public boolean sameValueAs(EmailId other) {
        return other != null && this.id.equals(other.id);
    }

    @Override
    public String toString() {
        return id;
    }
}
