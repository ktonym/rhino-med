package ke.co.rhino.uw.entity;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.LocalDate;

@Converter(autoApply=true)
public class LocalDatePersistenceConverter implements AttributeConverter<LocalDate, java.sql.Date> {
	
	@Override
	public java.sql.Date convertToDatabaseColumn(LocalDate entityValue){
		return (entityValue == null ? null : java.sql.Date.valueOf(entityValue));
	}

	@Override
	public LocalDate convertToEntityAttribute(java.sql.Date databaseValue){
		return (databaseValue == null ? null : databaseValue.toLocalDate());
	}

}
