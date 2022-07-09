/**
 *
 */
package com.dbank.ist.referencedata.nace.controller;

import com.dbank.ist.referencedata.nace.dto.NaceDto;
import com.dbank.ist.referencedata.nace.service.NaceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author tarunkumar
 */
@RestController
@Slf4j
@RequestMapping(value = "/nacedata")
public class NaceResource {

    private final NaceService naceService;

    @Autowired
    public NaceResource(NaceService naceService) {
        this.naceService = naceService;
    }

    @GetMapping("/all")
    @ResponseBody
    public ResponseEntity<List<NaceDto>> getAllNaceDetails() {
        return new ResponseEntity<List<NaceDto>>((List<NaceDto>) naceService.getAllNaceRecords(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<NaceDto> getNaceDetails(@PathVariable int id) {
        return new ResponseEntity<NaceDto>(naceService.getNaceRecord(id), HttpStatus.OK);
    }

    @PostMapping(value="bulkupload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> bulkUploadFromExcel(@RequestPart("file") MultipartFile excelFile) {
        return new ResponseEntity<String>(naceService.bulkUploadFromExcel(excelFile), HttpStatus.CREATED);
    }

    @PutMapping("/")
    public ResponseEntity<NaceDto> putNaceDetails(@RequestBody NaceDto naceDto) {
        return new ResponseEntity<NaceDto>(naceService.putNaceRecord(naceDto), HttpStatus.CREATED);
    }

}
