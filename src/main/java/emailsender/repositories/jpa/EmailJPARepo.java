package emailsender.repositories.jpa;

import org.springframework.stereotype.Repository;

import emailsender.datamodel.EmailJPA;
import emailsender.domain.email.EmailId;

import org.springframework.data.repository.CrudRepository;


@Repository
public interface EmailJPARepo extends CrudRepository<EmailJPA, EmailId>{
    
}
