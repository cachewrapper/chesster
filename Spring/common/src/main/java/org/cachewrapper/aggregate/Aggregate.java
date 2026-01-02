package org.cachewrapper.aggregate;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@NoArgsConstructor
public abstract class Aggregate {

    @Id
    @Column(name = "aggregate_uuid")
    protected UUID aggregateUUID;

    public Aggregate(UUID aggregateUUID) {
        this.aggregateUUID = aggregateUUID;
    }
}