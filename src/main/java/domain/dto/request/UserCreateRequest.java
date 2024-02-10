package domain.dto.request;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
//@Builder
//@Jacksonized
public class UserCreateRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;

}
