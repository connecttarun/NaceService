package com.dbank.ist.referencedata.nace.util;

import com.dbank.ist.referencedata.nace.dto.NaceDto;
import com.dbank.ist.referencedata.nace.entity.Nace;

public class NaceTestUtils {

    public static final Integer ID_TWO = 2;
    public static final Integer ID_FIVE = 5;
    public static final String DESCRIPTION_FIVE = "Description for record 5";

    public static NaceDto getNaceDto(int id) {
        NaceDto.NaceDtoBuilder builder = NaceDto.builder().order(id).description(DESCRIPTION_FIVE.replace("5", Integer.toString(id)));
        return builder.build();
    }

    public static Nace getNace(int id) {
        Nace.NaceBuilder builder = Nace.builder().order(id).description(DESCRIPTION_FIVE.replace("5", Integer.toString(id)));
        return builder.build();
    }
}
