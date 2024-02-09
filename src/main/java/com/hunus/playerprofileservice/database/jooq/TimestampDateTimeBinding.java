package com.hunus.playerprofileservice.database.jooq;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.jooq.Converter;

public class TimestampDateTimeBinding implements Converter<Timestamp, LocalDateTime> {

    @Override
    public LocalDateTime from(Timestamp timestamp) {
        return timestamp.toLocalDateTime();
    }

    @Override
    public Timestamp to(LocalDateTime dateTime) {
        return Timestamp.valueOf(dateTime);
    }

    @Override
    public Class<Timestamp> fromType() {
        return Timestamp.class;
    }

    @Override
    public Class<LocalDateTime> toType() {
        return LocalDateTime.class;
    }
}
