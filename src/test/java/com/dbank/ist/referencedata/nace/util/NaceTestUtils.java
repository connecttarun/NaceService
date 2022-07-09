package com.dbank.ist.referencedata.nace.util;

import com.dbank.ist.referencedata.nace.dto.NaceDto;
import com.dbank.ist.referencedata.nace.entity.Nace;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;

public class NaceTestUtils {

    public static final Integer ID_TWO = 2;
    public static final Integer ID_FIVE = 5;
    public static final String DESCRIPTION_FIVE = "Description for record 5";

    public static NaceDto getNaceDto(int id) {
        NaceDto.NaceDtoBuilder builder = NaceDto.builder().order(id).description(DESCRIPTION_FIVE.replace("5", Integer.toString(id)));
        return builder.build();
    }

    public static Nace getStubNace(int id) {
        Nace.NaceBuilder builder = Nace.builder().order(id).description(DESCRIPTION_FIVE.replace("5", Integer.toString(id)));
        return builder.build();
    }

    public static MockMultipartFile getMockMultipartFile() throws IOException {
        return new MockMultipartFile(
                "file",
                "ForTestNace.xls",
                "application/x-xls",
                new ClassPathResource("bulkupload/ForTestNace.xls").getInputStream());
    }
}