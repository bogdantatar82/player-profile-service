/*
 * This file is generated by jOOQ.
 */
package com.hunus.playerprofileservice.database.jooq.model.tables.records;


import com.hunus.playerprofileservice.database.jooq.model.tables.Clans;

import java.time.LocalDateTime;
import java.util.UUID;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record4;
import org.jooq.Row4;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ClanRecord extends UpdatableRecordImpl<ClanRecord> implements Record4<UUID, String, LocalDateTime, LocalDateTime> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.clan.id</code>.
     */
    public void setId(UUID value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.clan.id</code>.
     */
    public UUID getId() {
        return (UUID) get(0);
    }

    /**
     * Setter for <code>public.clan.name</code>.
     */
    public void setName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.clan.name</code>.
     */
    public String getName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.clan.created</code>.
     */
    public void setCreated(LocalDateTime value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.clan.created</code>.
     */
    public LocalDateTime getCreated() {
        return (LocalDateTime) get(2);
    }

    /**
     * Setter for <code>public.clan.modified</code>.
     */
    public void setModified(LocalDateTime value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.clan.modified</code>.
     */
    public LocalDateTime getModified() {
        return (LocalDateTime) get(3);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<UUID> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record4 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row4<UUID, String, LocalDateTime, LocalDateTime> fieldsRow() {
        return (Row4) super.fieldsRow();
    }

    @Override
    public Row4<UUID, String, LocalDateTime, LocalDateTime> valuesRow() {
        return (Row4) super.valuesRow();
    }

    @Override
    public Field<UUID> field1() {
        return Clans.CLANS.ID;
    }

    @Override
    public Field<String> field2() {
        return Clans.CLANS.NAME;
    }

    @Override
    public Field<LocalDateTime> field3() {
        return Clans.CLANS.CREATED;
    }

    @Override
    public Field<LocalDateTime> field4() {
        return Clans.CLANS.MODIFIED;
    }

    @Override
    public UUID component1() {
        return getId();
    }

    @Override
    public String component2() {
        return getName();
    }

    @Override
    public LocalDateTime component3() {
        return getCreated();
    }

    @Override
    public LocalDateTime component4() {
        return getModified();
    }

    @Override
    public UUID value1() {
        return getId();
    }

    @Override
    public String value2() {
        return getName();
    }

    @Override
    public LocalDateTime value3() {
        return getCreated();
    }

    @Override
    public LocalDateTime value4() {
        return getModified();
    }

    @Override
    public ClanRecord value1(UUID value) {
        setId(value);
        return this;
    }

    @Override
    public ClanRecord value2(String value) {
        setName(value);
        return this;
    }

    @Override
    public ClanRecord value3(LocalDateTime value) {
        setCreated(value);
        return this;
    }

    @Override
    public ClanRecord value4(LocalDateTime value) {
        setModified(value);
        return this;
    }

    @Override
    public ClanRecord values(UUID value1, String value2, LocalDateTime value3, LocalDateTime value4) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached ClanRecord
     */
    public ClanRecord() {
        super(Clans.CLANS);
    }

    /**
     * Create a detached, initialised ClanRecord
     */
    public ClanRecord(UUID id, String name, LocalDateTime created, LocalDateTime modified) {
        super(Clans.CLANS);

        setId(id);
        setName(name);
        setCreated(created);
        setModified(modified);
    }
}
