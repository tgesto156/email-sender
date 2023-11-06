package emailsender.controller;

import emailsender.dto.SendEmailDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import emailsender.services.EmailService;

@Controller
public class EmailController {
    

    private final EmailService emailService;

    @Autowired
    public EmailController(EmailService emailService){
        this.emailService = emailService;
    }


    /**
     * Handles a POST request to send an email using the provided SendEmailDTO.
     *
     * @param sendEmailDTO The SendEmailDTO containing the email details to be sent.
     * @return A ResponseEntity with HTTP status 200 (OK) upon successful email delivery.
     *         If an error occurs, a ResponseEntity with HTTP status 500 (Internal Server Error)
     *         and an error message is returned.
     */
    @PostMapping("/send")
    public ResponseEntity<Object> sendEmail(@RequestBody SendEmailDTO sendEmailDTO){
        try {
            this.emailService.sendMail(sendEmailDTO);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}
