package com.musobek.clickapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String LastName;;
    private String FirstName;
    private String email;
    private String color;
    private String password;
    private String initialLetter;
    @ManyToOne
    @JoinColumn(name = "attachment_id_ID")
    private Attachment attachmentId;


}
