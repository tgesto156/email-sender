package emailsender.repositories.interfaces;

import java.util.List;
import java.util.Optional;

import emailsender.domain.email.Email;
import emailsender.domain.email.EmailId;

public interface IEmailRepo {

    Optional<Email> findByID(EmailId id);

    List<Email> findAll();

    Email save(Email savedEmail);

    boolean existsByID(EmailId id);
}
