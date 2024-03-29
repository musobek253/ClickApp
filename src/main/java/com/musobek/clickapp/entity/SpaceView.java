package com.musobek.clickapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class SpaceView {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Space spaceId;
    @ManyToOne
    private View viewId;

    public SpaceView(Space spaceId, View viewId) {
        this.spaceId = spaceId;
        this.viewId = viewId;
    }
}
