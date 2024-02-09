/*
 * This file is generated by jOOQ.
 */
package com.hunus.playerprofileservice.database.jooq.model;


import com.hunus.playerprofileservice.database.jooq.model.tables.Clans;
import com.hunus.playerprofileservice.database.jooq.model.tables.Devices;
import com.hunus.playerprofileservice.database.jooq.model.tables.FlywaySchemaHistory;
import com.hunus.playerprofileservice.database.jooq.model.tables.PlayerProfiles;

import java.util.Arrays;
import java.util.List;

import org.jooq.Catalog;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Public extends SchemaImpl {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public</code>
     */
    public static final Public PUBLIC = new Public();

    /**
     * The table <code>public.clan</code>.
     */
    public final Clans CLAN = Clans.CLANS;

    /**
     * The table <code>public.device</code>.
     */
    public final Devices DEVICE = Devices.DEVICE;

    /**
     * The table <code>public.flyway_schema_history</code>.
     */
    public final FlywaySchemaHistory FLYWAY_SCHEMA_HISTORY = FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY;

    /**
     * The table <code>public.player_profile</code>.
     */
    public final PlayerProfiles PLAYER_PROFILE = PlayerProfiles.PLAYER_PROFILE;

    /**
     * No further instances allowed
     */
    private Public() {
        super("public", null);
    }


    @Override
    public Catalog getCatalog() {
        return DefaultCatalog.DEFAULT_CATALOG;
    }

    @Override
    public final List<Table<?>> getTables() {
        return Arrays.asList(
            Clans.CLANS,
            Devices.DEVICE,
            FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY,
            PlayerProfiles.PLAYER_PROFILE
        );
    }
}
