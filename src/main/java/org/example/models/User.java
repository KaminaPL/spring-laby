package org.example.models;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(of="id")
@ToString
public class User
{
    private String id;
    private String login;
    private String password;
    private String role;

    public User copy()
    {
        return User.builder()
                .id(id)
                .login(login)
                .password(password)
                .role(role)
                .build();
    }
}
