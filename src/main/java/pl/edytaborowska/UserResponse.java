package pl.edytaborowska;

import java.util.List;

public record UserResponse(String userName,
                           List<Repository> repositories) {

    public record Repository(
            String repositoryName,
            String ownerLogin,
            List<Branch> branches
    ) {}

    public record Branch(
            String branchName,
            String commitSHA
    ) {}
}
