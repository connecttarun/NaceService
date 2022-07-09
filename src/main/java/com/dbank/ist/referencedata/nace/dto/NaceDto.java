package com.dbank.ist.referencedata.nace.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.poiji.annotation.ExcelCellName;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({"order", "level", "code", "parent", "description", "thisItemIncludes",
        "thisItemAlsoIncludes", "rulings", "thisItemExcludes", "referenceToIsicRev4"})
@Builder
public class NaceDto {

    @NotNull
    @ExcelCellName(mandatory = true, value = "order")
    Integer order;

    @ExcelCellName(mandatory = false, value = "level")
    Integer level;

    @ExcelCellName(mandatory = false, value = "code")
    String code;

    @ExcelCellName(mandatory = false, value = "parent")
    String parent;

    @ExcelCellName(mandatory = false, value = "description")
    String description;

    @ExcelCellName(mandatory = false, value = "thisItemIncludes")
    String thisItemIncludes;

    @ExcelCellName(mandatory = false, value = "thisItemAlsoIncludes")
    String thisItemAlsoIncludes;

    @ExcelCellName(mandatory = false, value = "rulings")
    String rulings;

    @ExcelCellName(mandatory = false, value = "thisItemExcludes")
    String thisItemExcludes;

    @ExcelCellName(mandatory = true, value = "referenceToIsicRev4")
    String referenceToIsicRev4;

}