package domain.dto.response;

import lombok.Data;

@Data
public class UserResponse {

    private Long userId;
    private String userRole;
    private String firstname;
    private String lastName;
    private String email;
    private String password;
}
