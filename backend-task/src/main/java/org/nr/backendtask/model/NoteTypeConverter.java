package org.nr.backendtask.model;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import org.springframework.stereotype.Component;

@Converter(autoApply = true)
@Component
public class NoteTypeConverter implements AttributeConverter<NoteType, String> {
    @Override
    public String convertToDatabaseColumn(NoteType attribute) {
        if (attribute != null) {
            return attribute.name().toLowerCase();
        }
        return null;
    }

    @Override
    public NoteType convertToEntityAttribute(String dbData) {
        return NoteType.fromKey(dbData);
    }
}
