package org.example;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(of="login")
@ToString
public class User
{
    public enum Role
    {
        USER,
        ADMIN
    }

    private String id;
    private String login;
    private String password;
    private Role role;

    public User copy()
    {
        return User.builder()
                .login(login)
                .password(password)
                .role(role)
                .build();
    }
}
