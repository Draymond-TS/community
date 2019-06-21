package life.draymond.community.dto;

import lombok.Data;

import java.io.Serializable;
@Data
public class GitHubUser  implements Serializable {
    private String name;
    private Long id;
    private String bio;
    private String avatar_url;
}

