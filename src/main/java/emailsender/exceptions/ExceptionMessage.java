package emailsender.exceptions;

//SupressWarnings allows to supress multiple kinds of warnings when they're not needed
@SuppressWarnings("squid:S2068")
public class ExceptionMessage {

    //VALUE
    public static final String INVALID_VALUE_STRING = "Invalid String for a Value";
    public static final String INVALID_VALUE_ID = "Invalid Value Id";


    //EMAIL
    public static final String INVALID_EMAIL_ID = "Invalid Id for an Email";

    public static final String INVALID_SECRET_KEY = "Invalid Secret Key";

    private ExceptionMessage() {
        /* Private constructor to hide implicit public one */
    }
}