package emailsender.domain.email;

import lombok.Getter;

@Getter
public enum EmailTemplate {

    MB_REFERENCE,
    MBW_REFERENCE,
    PAYSHOP_REFERENCE,
    CC_REDIRECT_URL,
    CODIFIS_REDIRECT_URL,
    SANTANDER_REDIRECT_URL,
    TEMPLATE,
    RESETPASSWORD,
}