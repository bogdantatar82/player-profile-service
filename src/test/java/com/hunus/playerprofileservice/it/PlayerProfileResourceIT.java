package com.hunus.playerprofileservice.it;

import static com.hunus.playerprofileservice.PlayerProfileUtils.jsonBody;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.equalTo;

import java.sql.SQLException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.hunus.playerprofileservice.dto.ClanDTO;
import com.hunus.playerprofileservice.PlayerProfileUtils;
import com.hunus.playerprofileservice.dto.PlayerProfileDTO;

public class PlayerProfileResourceIT extends BaseIT {
    private static final String CLAN_URL = "/v0/clans";
    private static final String PLAYER_PROFILE_URL = "/v0/profiles";

    private ClanDTO clan;
    private PlayerProfileDTO playerProfile;

    @BeforeEach
    void beforeEach() throws SQLException {
        clearDb();
        clan = new ClanDTO("Hello world clan");
        playerProfile = PlayerProfileUtils.generatePlayerProfile();
    }

    @Test
    void matchPlayerProfile_updates_player_profile_with_current_active_campaigns_name() {
        // first save the clan
        given()
            .when()
                .contentType(JSON)
                .body(jsonBody(clan))
                .post(CLAN_URL)
            .then()
                .statusCode(201);

        // then save the player profile
        String playerId = given()
            .when()
                .contentType(JSON)
                .body(jsonBody(playerProfile))
                .post(PLAYER_PROFILE_URL)
            .then()
                .statusCode(201)
                .extract()
                .jsonPath().getString("playerId");

        // and finally do the player profile matching
        given()
            .when()
                .contentType(JSON)
                .post(PLAYER_PROFILE_URL + String.format("/%s/match", playerId))
            .then()
                .statusCode(200)
                .body("activeCampaigns[0]", equalTo("mycampaign"));
    }
}
