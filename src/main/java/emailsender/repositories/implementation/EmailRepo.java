package emailsender.repositories.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import emailsender.repositories.interfaces.IEmailRepo;
import emailsender.repositories.jpa.EmailJPARepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import emailsender.datamodel.EmailJPA;
import emailsender.datamodel.assemblers.EmailDomainAssembler;
import emailsender.domain.email.Email;
import emailsender.domain.email.EmailId;

@Repository
public class EmailRepo implements IEmailRepo {
    
    @Autowired
    private EmailJPARepo emailJPARepo;
    @Autowired
    private EmailDomainAssembler emailDomainAssembler;

    public EmailRepo(){
        //Empty Constructor
    }

    @Override
    public Optional<Email> findByID(EmailId id) {
        Optional<EmailJPA> emailJPA = this.emailJPARepo.findById(id);

        if(!emailJPA.isPresent()){
            return Optional.empty();
        }

        EmailJPA retrievedEmailJPA = emailJPA.get();

        Email email = this.emailDomainAssembler.toDomain(retrievedEmailJPA);

        return Optional.of(email);
    }

    @Override
    public List<Email> findAll() {
        List<EmailJPA> emailJPAList = (List<EmailJPA>) this.emailJPARepo.findAll();

        List<Email> emailList = new ArrayList<>();

        for (EmailJPA emailJPA : emailJPAList){
            emailList.add(this.emailDomainAssembler.toDomain(emailJPA));
        }
        return emailList;
    }

    @Override
    public Email save(Email savedEmailInput) {
        EmailJPA emailJPAtoSave = this.emailDomainAssembler.toData(savedEmailInput);
        EmailJPA savedEmail = this.emailJPARepo.save(emailJPAtoSave);

        return this.emailDomainAssembler.toDomain(savedEmail);
    }

    @Override
    public boolean existsByID(EmailId id) {
        return emailJPARepo.existsById(id);
    }



}
