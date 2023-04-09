package com.musobek.clickapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class SpaceUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Space spaceId;
    @ManyToOne
    private User userId;

    public SpaceUser(Space spaceId, User userId) {
        this.spaceId = spaceId;
        this.userId = userId;
    }
}
