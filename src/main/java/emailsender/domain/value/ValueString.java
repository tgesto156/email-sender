package emailsender.domain.value;

import java.util.Objects;

import emailsender.exceptions.ExceptionMessage;
import emailsender.valueobjects.ValueObject;
import emailsender.utils.ArgumentValidators;

public class ValueString implements ValueObject<ValueString> {
    
    private String valueString;

    public ValueString(String valueString){
        ArgumentValidators.validateNullObjects(valueString, ExceptionMessage.INVALID_VALUE_ID);
        this.valueString = valueString;
    }

    @Override
    public boolean sameValueAs(ValueString other) {
        return other != null && this.valueString.equals(other.valueString);
    }

    @Override
    public String toString() {
        return valueString;
    }


    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof ValueString)) {
            return false;
        }
        ValueString value = (ValueString) o;
        return Objects.equals(valueString, value.valueString);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(valueString);
    }

}
