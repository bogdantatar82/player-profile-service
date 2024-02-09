/*
 * This file is generated by jOOQ.
 */
package com.hunus.playerprofileservice.database.jooq.model.tables;


import com.hunus.playerprofileservice.database.jooq.TimestampDateTimeBinding;
import com.hunus.playerprofileservice.database.jooq.model.Keys;
import com.hunus.playerprofileservice.database.jooq.model.Public;
import com.hunus.playerprofileservice.database.jooq.model.tables.records.DeviceRecord;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row7;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Devices extends TableImpl<DeviceRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.device</code>
     */
    public static final Devices DEVICE = new Devices();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<DeviceRecord> getRecordType() {
        return DeviceRecord.class;
    }

    /**
     * The column <code>public.device.id</code>.
     */
    public final TableField<DeviceRecord, UUID> ID = createField("id", SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.device.model</code>.
     */
    public final TableField<DeviceRecord, String> MODEL = createField("model", SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>public.device.carrier</code>.
     */
    public final TableField<DeviceRecord, String> CARRIER = createField("carrier", SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>public.device.firmware</code>.
     */
    public final TableField<DeviceRecord, String> FIRMWARE = createField("firmware", SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>public.device.player_id</code>.
     */
    public final TableField<DeviceRecord, UUID> PLAYER_ID = createField("player_id", SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.device.created</code>.
     */
    public final TableField<DeviceRecord, LocalDateTime> CREATED = createField("created", org.jooq.impl.SQLDataType.TIMESTAMP.defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "", new TimestampDateTimeBinding());

    /**
     * The column <code>public.device.modified</code>.
     */
    public final TableField<DeviceRecord, LocalDateTime> MODIFIED = createField("modified", org.jooq.impl.SQLDataType.TIMESTAMP.defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "", new TimestampDateTimeBinding());

    private Devices(Name alias, Table<DeviceRecord> aliased) {
        this(alias, aliased, null);
    }

    private Devices(Name alias, Table<DeviceRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.device</code> table reference
     */
    public Devices(String alias) {
        this(DSL.name(alias), DEVICE);
    }

    /**
     * Create an aliased <code>public.device</code> table reference
     */
    public Devices(Name alias) {
        this(alias, DEVICE);
    }

    /**
     * Create a <code>public.device</code> table reference
     */
    public Devices() {
        this(DSL.name("device"), null);
    }

    public <O extends Record> Devices(Table<O> child, ForeignKey<O, DeviceRecord> key) {
        super(child, key, DEVICE);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Public.PUBLIC;
    }

    @Override
    public UniqueKey<DeviceRecord> getPrimaryKey() {
        return Keys.QUEUES_PKEY;
    }

    @Override
    public List<ForeignKey<DeviceRecord, ?>> getReferences() {
        return Arrays.asList(Keys.DEVICE__DEVICE_PLAYER_ID_FKEY);
    }

    private transient PlayerProfiles _playerProfile;

    public PlayerProfiles playerProfile() {
        if (_playerProfile == null)
            _playerProfile = new PlayerProfiles(this, Keys.DEVICE__DEVICE_PLAYER_ID_FKEY);

        return _playerProfile;
    }

    @Override
    public Devices as(String alias) {
        return new Devices(DSL.name(alias), this);
    }

    @Override
    public Devices as(Name alias) {
        return new Devices(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Devices rename(String name) {
        return new Devices(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Devices rename(Name name) {
        return new Devices(name, null);
    }

    // -------------------------------------------------------------------------
    // Row7 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row7<UUID, String, String, String, UUID, LocalDateTime, LocalDateTime> fieldsRow() {
        return (Row7) super.fieldsRow();
    }
}
