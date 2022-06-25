package com.dbank.ist.referencedata.nace.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.sun.istack.NotNull;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({"order", "level", "code", "parent", "description", "thisItemIncludes",
		"thisItemAlsoIncludes", "rulings", "thisItemExcludes", "referenceToIsicRev4"})
@Builder
public class NaceDto {

    @NotNull
    Integer order;

    Integer level;

    String code;

    String parent;        //looks like the reference is to the code in the parent

    String description;

    String thisItemIncludes;

    String thisItemAlsoIncludes;

    String rulings;

    String thisItemExcludes;

    String referenceToIsicRev4;

}