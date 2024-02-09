package com.hunus.playerprofileservice.it;

import static com.hunus.playerprofileservice.PlayerProfileUtils.generatePlayerProfile;
import static com.hunus.playerprofileservice.PlayerProfileUtils.generateActiveCampaign;
import static com.hunus.playerprofileservice.PlayerProfileUtils.jsonBody;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.equalTo;

import java.sql.SQLException;
import java.util.List;

import com.hunus.playerprofileservice.dto.ClanDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.hunus.playerprofileservice.dto.PlayerProfileDTO;
import com.hunus.playerprofileservice.dto.campaign.CampaignDTO;

public class PlayerProfileV1ResourceIT extends BaseIT {

    private static final String CLAN_URL = "/v0/clans";
    private static final String PLAYER_PROFILE_URL = "/v0/profiles";
    private static final String PLAYER_PROFILE_V1_URL = "/v1/profiles";

    private ClanDTO clan;
    private PlayerProfileDTO playerProfile;
    private List<CampaignDTO> activeCampaigns;

    @BeforeEach
    void beforeEach() throws SQLException {
        clearDb();
        clan = new ClanDTO("Hello world clan");
        playerProfile = generatePlayerProfile();
        activeCampaigns = List.of(
            generateActiveCampaign("mylastcampaign",
                List.of("UK", "RO", "CA"),
                List.of("item_1"),
                null,
                List.of("item_4")
            ),
            generateActiveCampaign("mycampaign",
                List.of("UK", "RO", "CA"),
                List.of("item_1"),
                null,
                List.of("item_3")
            )
        );
    }

    @Test
    void matchPlayerProfile_updates_player_profile_with_active_campaigns_name() {
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
                .body(jsonBody(activeCampaigns))
                .post(PLAYER_PROFILE_V1_URL + String.format("/%s/match", playerId))
            .then()
                .statusCode(200)
                .body("activeCampaigns[0]", equalTo("mylastcampaign"))
                .body("activeCampaigns[1]", equalTo("mycampaign"));
    }
}
