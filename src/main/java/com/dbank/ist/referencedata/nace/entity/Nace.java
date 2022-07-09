package com.dbank.ist.referencedata.nace.entity;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor      // for modelmapper
@AllArgsConstructor     // for builder
@Data
@Table(name = "NACE")
@Entity
public class Nace {

    @EqualsAndHashCode.Include
    @Column(name = "itemID")
    Integer order;

    Integer level;

    @EqualsAndHashCode.Include
    @Id
    @NotNull
    String code;

    @OneToOne
    @JoinColumn(name = "parent",referencedColumnName = "code")
    Nace parent;

    String description;

    @Column(length = 6000)
    String thisItemIncludes;

    @Column(length = 2000)
    String thisItemAlsoIncludes;

    @Column(length = 1000)
    String rulings;

    @Column(length = 2000)
    String thisItemExcludes;

    String referenceToIsicRev4;
}
