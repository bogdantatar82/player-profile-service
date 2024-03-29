/*
 * This file is generated by jOOQ.
 */
package com.hunus.playerprofileservice.database.jooq.model.tables;


import com.google.gson.JsonElement;
import com.hunus.playerprofileservice.database.jooq.PostgresJSONGsonBinding;
import com.hunus.playerprofileservice.database.jooq.TimestampDateTimeBinding;
import com.hunus.playerprofileservice.database.jooq.model.Keys;
import com.hunus.playerprofileservice.database.jooq.model.Public;
import com.hunus.playerprofileservice.database.jooq.model.tables.records.PlayerProfileRecord;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row20;
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
public class PlayerProfiles extends TableImpl<PlayerProfileRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.player_profile</code>
     */
    public static final PlayerProfiles PLAYER_PROFILE = new PlayerProfiles();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<PlayerProfileRecord> getRecordType() {
        return PlayerProfileRecord.class;
    }

    /**
     * The column <code>public.player_profile.id</code>.
     */
    public final TableField<PlayerProfileRecord, UUID> ID = createField("id", SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.player_profile.credential</code>.
     */
    public final TableField<PlayerProfileRecord, String> CREDENTIAL = createField("credential", SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>public.player_profile.last_session</code>.
     */
    public final TableField<PlayerProfileRecord, LocalDateTime> LAST_SESSION = createField("last_session", org.jooq.impl.SQLDataType.TIMESTAMP.defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "", new TimestampDateTimeBinding());

    /**
     * The column <code>public.player_profile.total_spent</code>.
     */
    public final TableField<PlayerProfileRecord, Integer> TOTAL_SPENT = createField("total_spent", SQLDataType.INTEGER, this, "");

    /**
     * The column <code>public.player_profile.total_refund</code>.
     */
    public final TableField<PlayerProfileRecord, Integer> TOTAL_REFUND = createField("total_refund", SQLDataType.INTEGER, this, "");

    /**
     * The column <code>public.player_profile.total_transactions</code>.
     */
    public final TableField<PlayerProfileRecord, Integer> TOTAL_TRANSACTIONS = createField("total_transactions", SQLDataType.INTEGER, this, "");

    /**
     * The column <code>public.player_profile.last_purchase</code>.
     */
    public final TableField<PlayerProfileRecord, LocalDateTime> LAST_PURCHASE = createField("last_purchase", org.jooq.impl.SQLDataType.TIMESTAMP.defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "", new TimestampDateTimeBinding());

    /**
     * The column <code>public.player_profile.active_campaigns</code>.
     */
    public final TableField<PlayerProfileRecord, JsonElement> ACTIVE_CAMPAIGNS = createField("active_campaigns", org.jooq.impl.DefaultDataType.getDefaultDataType("\"pg_catalog\".\"jsonb\""), this, "", new PostgresJSONGsonBinding());

    /**
     * The column <code>public.player_profile.level</code>.
     */
    public final TableField<PlayerProfileRecord, Integer> LEVEL = createField("level", SQLDataType.INTEGER, this, "");

    /**
     * The column <code>public.player_profile.experience</code>.
     */
    public final TableField<PlayerProfileRecord, Integer> EXPERIENCE = createField("experience", SQLDataType.INTEGER, this, "");

    /**
     * The column <code>public.player_profile.total_playtime</code>.
     */
    public final TableField<PlayerProfileRecord, Integer> TOTAL_PLAYTIME = createField("total_playtime", SQLDataType.INTEGER, this, "");

    /**
     * The column <code>public.player_profile.country</code>.
     */
    public final TableField<PlayerProfileRecord, String> COUNTRY = createField("country", SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>public.player_profile.language</code>.
     */
    public final TableField<PlayerProfileRecord, String> LANGUAGE = createField("language", SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>public.player_profile.birthdate</code>.
     */
    public final TableField<PlayerProfileRecord, LocalDateTime> BIRTHDATE = createField("birthdate", org.jooq.impl.SQLDataType.TIMESTAMP.defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "", new TimestampDateTimeBinding());

    /**
     * The column <code>public.player_profile.gender</code>.
     */
    public final TableField<PlayerProfileRecord, String> GENDER = createField("gender", SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>public.player_profile.inventory</code>.
     */
    public final TableField<PlayerProfileRecord, JsonElement> INVENTORY = createField("inventory", org.jooq.impl.DefaultDataType.getDefaultDataType("\"pg_catalog\".\"jsonb\""), this, "", new PostgresJSONGsonBinding());

    /**
     * The column <code>public.player_profile.custom_field</code>.
     */
    public final TableField<PlayerProfileRecord, String> CUSTOM_FIELD = createField("custom_field", SQLDataType.VARCHAR(255), this, "");

    /**
     * The column <code>public.player_profile.created</code>.
     */
    public final TableField<PlayerProfileRecord, LocalDateTime> CREATED = createField("created", org.jooq.impl.SQLDataType.TIMESTAMP.defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "", new TimestampDateTimeBinding());

    /**
     * The column <code>public.player_profile.modified</code>.
     */
    public final TableField<PlayerProfileRecord, LocalDateTime> MODIFIED = createField("modified", org.jooq.impl.SQLDataType.TIMESTAMP.defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "", new TimestampDateTimeBinding());

    /**
     * The column <code>public.player_profile.clan_id</code>.
     */
    public final TableField<PlayerProfileRecord, UUID> CLAN_ID = createField("clan_id", SQLDataType.UUID, this, "");

    private PlayerProfiles(Name alias, Table<PlayerProfileRecord> aliased) {
        this(alias, aliased, null);
    }

    private PlayerProfiles(Name alias, Table<PlayerProfileRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.player_profile</code> table reference
     */
    public PlayerProfiles(String alias) {
        this(DSL.name(alias), PLAYER_PROFILE);
    }

    /**
     * Create an aliased <code>public.player_profile</code> table reference
     */
    public PlayerProfiles(Name alias) {
        this(alias, PLAYER_PROFILE);
    }

    /**
     * Create a <code>public.player_profile</code> table reference
     */
    public PlayerProfiles() {
        this(DSL.name("player_profile"), null);
    }

    public <O extends Record> PlayerProfiles(Table<O> child, ForeignKey<O, PlayerProfileRecord> key) {
        super(child, key, PLAYER_PROFILE);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Public.PUBLIC;
    }

    @Override
    public UniqueKey<PlayerProfileRecord> getPrimaryKey() {
        return Keys.RULES_PKEY;
    }

    @Override
    public List<ForeignKey<PlayerProfileRecord, ?>> getReferences() {
        return Arrays.asList(Keys.PLAYER_PROFILE__PLAYER_PROFILE_CLAN_ID_FKEY);
    }

    private transient Clans _clan;

    public Clans clan() {
        if (_clan == null)
            _clan = new Clans(this, Keys.PLAYER_PROFILE__PLAYER_PROFILE_CLAN_ID_FKEY);

        return _clan;
    }

    @Override
    public PlayerProfiles as(String alias) {
        return new PlayerProfiles(DSL.name(alias), this);
    }

    @Override
    public PlayerProfiles as(Name alias) {
        return new PlayerProfiles(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public PlayerProfiles rename(String name) {
        return new PlayerProfiles(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public PlayerProfiles rename(Name name) {
        return new PlayerProfiles(name, null);
    }

    // -------------------------------------------------------------------------
    // Row20 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row20<UUID, String, LocalDateTime, Integer, Integer, Integer, LocalDateTime, JsonElement, Integer, Integer, Integer, String, String, LocalDateTime, String, JsonElement, String, LocalDateTime, LocalDateTime, UUID> fieldsRow() {
        return (Row20) super.fieldsRow();
    }
}
