package com.stallocator.entities;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "beds")
public class Bed extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;
}
