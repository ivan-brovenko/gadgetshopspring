package com.epam.istore.model;

import lombok.*;

import javax.persistence.*;

@Entity(name = "user")
@Setter
@Getter
@NoArgsConstructor
@RequiredArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    private String name;

    @NonNull
    private String surname;

    @NonNull
    private String email;

    @NonNull
    private String password;

    @NonNull
    private boolean gender;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
}
