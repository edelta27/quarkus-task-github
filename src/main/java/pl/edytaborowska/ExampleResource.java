package pl.edytaborowska;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

@RegisterRestClient(baseUri = "https://api.github.com")
@ClientHeaderParam(name = "Authorization", value = "{getAuthorizationHeader}")
public interface GitHubClient {

    @GET
    @Path("/users/{owner}/repos")
    List<RepositoryResponse> getRepositories(@PathParam("owner") String owner);
    @GET
    @Path("/repos/{owner}/{repo}/branches")
    List<BranchResponse> getBranches(@PathParam("owner") String owner, @PathParam("repo") String repo);

    record RepositoryResponse(String name, boolean fork, Owner owner) {
        public record Owner(String login) {}
    }

    record BranchResponse(String name, Commit commit) {
        public record Commit(String sha) {}
    }

    default String getAuthorizationHeader() {
        return "Bearer " + ConfigProvider.getConfig().getValue("github.token", String.class);
    }
}
