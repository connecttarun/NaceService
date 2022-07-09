package com.dbank.ist.referencedata.nace.entity;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor      // for modelmapper
@AllArgsConstructor     // for builder
@Getter
@Setter
@Table(name = "NACE")
@Entity
public class Nace {

    @Id
    @NotNull
    @Column(name = "itemID")
    Integer order;

    Integer level;

    String code;

    String parent;

    String description;

    @Column(length = 6000)
    String thisItemIncludes;

    @Column(length = 2000)
    String thisItemAlsoIncludes;

    String rulings;

    @Column(length = 2000)
    String thisItemExcludes;

    String referenceToIsicRev4;
}
