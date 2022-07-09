package com.dbank.ist.referencedata.nace.service;

import com.dbank.ist.referencedata.nace.dto.NaceDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface NaceService {
    List<NaceDto> getAllNaceRecords();

    NaceDto getNaceRecord(int id);

    NaceDto putNaceRecord(NaceDto naceDto);

    String bulkUploadFromExcel(MultipartFile excelFile);
}
