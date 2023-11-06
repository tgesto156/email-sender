package emailsender.utils;
import java.util.UUID;

public class UUIDGenerator {

    private UUIDGenerator() {
    }

    public static UUID generateUUID() {

        return UUID.randomUUID();
    }
}
