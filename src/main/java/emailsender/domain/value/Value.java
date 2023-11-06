package emailsender.domain.value;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class Value{

    private ValueType valueType;
    private ValueString valueString;

    protected Value(ValueType valueType, ValueString valueString){
        this.valueType = valueType;
        this.valueString = valueString;
    }

    public ValueType getValueType() {
        return valueType;
    }

    public ValueString getValueString() {
        return valueString;
    }
}
