CREATE TABLE clan (
    id uuid NOT NULL,
    "name" varchar(255) NOT NULL,
    created timestamp NOT NULL,
    modified timestamp NOT NULL,
    CONSTRAINT clan_pkey PRIMARY KEY (id)
);

CREATE TABLE device (
    id uuid NOT NULL,
    model varchar(255) NOT NULL,
    carrier varchar(255) NOT NULL,
    firmware varchar(255) NOT NULL,
    player_id uuid NOT NULL,
    created timestamp NOT NULL,
    modified timestamp NOT NULL,
    CONSTRAINT queues_pkey PRIMARY KEY (id)
);

CREATE TABLE player_profile (
    id uuid NOT NULL,
    credential varchar(255) NOT NULL,
    last_session timestamp NULL,
    total_spent int4 NULL,
    total_refund int4 NULL,
    total_transactions int4 NULL,
    last_purchase timestamp NULL,
    active_campaigns varchar(255) NULL,
    "level" int4 NULL,
    experience int4 NULL,
    total_playtime int4 NULL,
    country varchar(255) NOT NULL,
    "language" varchar(255) NOT NULL,
    birthdate timestamp NOT NULL,
    gender varchar(255) NOT NULL,
    inventory varchar(512) NULL,
    custom_field varchar(255) NULL,
    created timestamp NOT NULL,
    modified timestamp NOT NULL,
    clan_id uuid NULL,
    CONSTRAINT rules_pkey PRIMARY KEY (id)
);

ALTER TABLE ONLY device
    ADD CONSTRAINT device_player_id_fkey FOREIGN KEY (player_id) REFERENCES player_profile(id);

ALTER TABLE ONLY player_profile
    ADD CONSTRAINT player_profile_clan_id_fkey FOREIGN KEY (clan_id) REFERENCES clan(id);
