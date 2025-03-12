package pl.edytaborowska;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
class GitHubResourceTest {

    @Test
    public void testGetRepositoriesHappyPath() {
        // given
        String username = "edelta27";

        // when
        Response response = given()
                .pathParam("username", username)
                .when()
                .get("/github/users/{username}/repos")
                .then()
                .statusCode(200)
                .extract().response();

        // then
        assertNotNull(response);
        assertEquals(200, response.statusCode());

        String userName = response.jsonPath().getString("userName");
        assertEquals(username, userName);

        var repositories = response.jsonPath().getList("repositories");
        assertNotNull(repositories);
        assertFalse(repositories.isEmpty());

        String firstRepoName = response.jsonPath().getString("repositories[0].repositoryName");
        assertNotNull(firstRepoName);

        String firstOwnerLogin = response.jsonPath().getString("repositories[0].ownerLogin");
        assertEquals(username, firstOwnerLogin);

        var branches = response.jsonPath().getList("repositories[1].branches");
        assertNotNull(branches);
        assertFalse(branches.isEmpty());

        String firstBranchName = response.jsonPath().getString("repositories[1].branches[0].branchName");
        assertNotNull(firstBranchName);

        String commitSHA = response.jsonPath().getString("repositories[1].branches[0].commitSHA");
        assertNotNull(commitSHA);
    }
}