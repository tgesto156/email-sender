package emailsender.dto;

import java.util.List;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonProperty;

import emailsender.domain.value.Value;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class EmailDTO extends RepresentationModel<EmailDTO>{

    @Getter
    @Setter
    @JsonProperty("id")
    private String id;
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
    private String timestamp;
    @Getter
    @Setter
    private boolean send;
    @Getter
    @Setter
    private String subject;
}
