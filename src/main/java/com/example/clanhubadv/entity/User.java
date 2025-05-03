package com.example.clanhubadv.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column (nullable = false)
    private String username;

    @Column (nullable = false, unique = true)

    private String email;


    @Column (nullable = false)
    private String password;

    @ManyToOne
    @JoinColumn (name = "role_id")
    private Role role;

    @Column(nullable = true)
    private UUID familyID;

    @Column(nullable = true)
    private UUID inviteCode;

    @Column(name = "avatar_id")
    private String avatarId;

    @Column(name = "age")
    private Integer age;

}