package pl.edytaborowska;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;
import java.util.stream.Collectors;

@Path("/github")
public class GitHubResource {

    private final GitHubClient gitHubClient;

    public GitHubResource(@RestClient GitHubClient gitHubClient) {
        this.gitHubClient = gitHubClient;
    }

    @GET
    @Path("/users/{userName}/repos")
    public UserResponse getRepositories(@PathParam("userName") String userName) {
        List<GitHubClient.RepositoryResponse> repositories;
        try {
            repositories = gitHubClient.getRepositories(userName).stream()
                    .filter(repo -> !repo.fork())
                    .collect(Collectors.toList());

            if (repositories.isEmpty()) {
                throw new NotFoundException("No repositories found for user " + userName);
            }
        } catch (NotFoundException e) {
            throw new NotFoundException(
                    Response.status(Response.Status.NOT_FOUND)
                            .entity(new ErrorResponse(404, "User not found or no repositories"))
                            .build()
            );
        }

        List<UserResponse.Repository> repositoryList = repositories.stream()
                .map(repo -> new UserResponse.Repository(
                        repo.name(),
                        repo.owner().login(),
                        gitHubClient.getBranches(userName, repo.name()).stream()
                                .map(branch -> new UserResponse.Branch(branch.name(), branch.commit().sha()))
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());

        return new UserResponse(userName, repositoryList);
    }

    public static class ErrorResponse {
        public int status;
        public String message;

        public ErrorResponse(int status, String message) {
            this.status = status;
            this.message = message;
        }
    }
}
