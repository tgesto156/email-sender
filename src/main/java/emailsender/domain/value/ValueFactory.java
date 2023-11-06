package emailsender.domain.value;

import org.springframework.stereotype.Component;

@Component
public class ValueFactory {
    

    public Value createValue(ValueType valueType, ValueString valueString){        
        return new Value(valueType, valueString);
    }

    public Value toDomain(String valueType, String valueString){
        ValueType valueTypeVO = ValueType.valueOf(valueType);
        ValueString valueValueVO = new ValueString(valueString);

        return new Value(valueTypeVO, valueValueVO);
    }
}
