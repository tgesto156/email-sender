package emailsender.utils;

@SuppressWarnings("squid:S2068")
public class ArgumentValidators {

    public static void validateNullOrEmptyString(String input, String exceptionMessage){
        if (input != null) {
            input = input.trim();
            if (!input.isEmpty()) return;
        }
        throw new IllegalArgumentException(exceptionMessage);
    }

    public static void validateNullObjects(Object obj, String exceptionMessage){
        if (obj == null)
            throw new IllegalArgumentException(exceptionMessage);
    }

    public static void validateNumberSmallerOrEqualThanZero(int input, String exceptionMessage){
        if (input <= 0){
            throw new IllegalArgumentException(exceptionMessage);
        }
    }

    public static void validateNumberSmallerThanZero(double input, String exceptionMessage){
        if (input < 0){
            throw new IllegalArgumentException(exceptionMessage);
        }
    }

    private ArgumentValidators() {
        /* Private constructor to hide implicit public one */
    }
}
