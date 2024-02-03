package domain.entity;

import lombok.*;

/**
 * класс описывающий пользователя
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @EqualsAndHashCode.Exclude
    private Long id;
    @EqualsAndHashCode.Exclude
    private UserRole userRole;
    @EqualsAndHashCode.Exclude
    private String firstName;
    @EqualsAndHashCode.Exclude
    private String lastName;
    private String email;
    @EqualsAndHashCode.Exclude
    private String password;

}
