package emailsender.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import emailsender.valueobjects.ValueObject;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode
@Getter
@Setter
public class Timestamp implements ValueObject<Timestamp> {

    private String timestamp;

    public Timestamp() {
        this.timestamp = generateTimestamp();
    }

    public Timestamp(String timestamp) {
        if (!isValidTimestampFormat(timestamp)) {
            throw new IllegalArgumentException("Invalid timestamp format. Use dd-MM-yyyy HH:mm:ss");
        }
        this.timestamp = timestamp;
    }

    public String generateTimestamp() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        java.util.Date date = new java.util.Date();
        return dateFormat.format(date);
    }

    @Override
    public String toString() {
        return timestamp;
    }

    @Override
    public boolean sameValueAs(Timestamp other) {
        return other != null && this.timestamp.equals(other.timestamp);
    }

    private boolean isValidTimestampFormat(String timestamp) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        dateFormat.setLenient(false); // This ensures strict format matching

        try {
            Date parsedDate = dateFormat.parse(timestamp);
            return timestamp.equals(dateFormat.format(parsedDate));
        } catch (ParseException e) {
            return false;
        }
    }

    public boolean isAfter(Timestamp other) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        try {
            Date thisDate = dateFormat.parse(this.timestamp);
            Date otherDate = dateFormat.parse(other.timestamp);
            return thisDate.after(otherDate);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid timestamp format");
        }
    }

    public boolean isBefore(Timestamp other) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        try {
            Date thisDate = dateFormat.parse(this.timestamp);
            Date otherDate = dateFormat.parse(other.timestamp);
            return thisDate.before(otherDate);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid timestamp format");
        }
    }
}
