package com.dbank.ist.referencedata.nace.service;

import com.dbank.ist.referencedata.nace.dto.NaceDto;
import com.dbank.ist.referencedata.nace.entity.Nace;
import com.dbank.ist.referencedata.nace.exceptions.ParentNotFoundExecption;
import com.dbank.ist.referencedata.nace.repository.NaceDataRepository;
import com.poiji.bind.Poiji;
import com.poiji.option.PoijiOptions;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;


@Slf4j
@Service
public class NaceServiceImpl implements NaceService {

    public static final String UPLOAD_DIRECTORY = "uploaded/";
    public static final String FILE_UPLOADED_SUCCESSFULLY = "File Uploaded Successfully";
    private final PoijiOptions poijiOptions;
    private final NaceDataRepository naceDataRepository;
    private final ModelMapper modelMapper;

    @SneakyThrows
    @Autowired
    public NaceServiceImpl(NaceDataRepository naceDataRepository, ModelMapper modelMapper, PoijiOptions poijiOptions) {
        this.naceDataRepository = naceDataRepository;
        this.modelMapper = modelMapper;
        this.poijiOptions = poijiOptions;
        Files.createDirectories(Paths.get(UPLOAD_DIRECTORY));
    }

    @Override
    public List<NaceDto> getAllNaceRecords() {
        List<NaceDto> allNaceRecords = new ArrayList<>();
        naceDataRepository.findAll().forEach(nace -> allNaceRecords.add(modelMapper.map(nace, NaceDto.class)));
        log.info("Returning {} records for allNaceRecords ", allNaceRecords.size());
        log.debug("And the Id for those are {}.", allNaceRecords.stream().map(nace -> String.valueOf(nace.getOrder()))
                .collect(Collectors.joining(",")));

        return allNaceRecords;
    }

    @SneakyThrows
    @Override
    public NaceDto getNaceRecord(int id) {
        Nace nace = naceDataRepository.findByOrder(id).get();
        log.info("Fetched from database {} for id {}", nace, id);
        return modelMapper.map(nace, NaceDto.class);
    }

    @Override
    public NaceDto putNaceRecord(NaceDto naceDto) {
        log.info("Received to save : {}", naceDto.toString());
        Nace savedNaceRecord;
        try {
            savedNaceRecord = naceDataRepository.save(modelMapper.map(naceDto, Nace.class));
        } catch (JpaObjectRetrievalFailureException exception) {
            log.error("The parent nace with code {} wasn't found ", naceDto.getParent());
            throw new ParentNotFoundExecption(naceDto.getParent());
        }

        log.debug("Saved with order :{}", savedNaceRecord.getOrder());
        return modelMapper.map(savedNaceRecord, NaceDto.class);
    }

    @Override
    public String bulkUploadFromExcel(MultipartFile excelFile) {
        File file = store(excelFile);
        Poiji.fromExcel(file, NaceDto.class, poijiOptions, this::putNaceRecord);

        return FILE_UPLOADED_SUCCESSFULLY;
    }

    @SneakyThrows
    private File store(MultipartFile file) {
        String filePathName = UPLOAD_DIRECTORY + File.separator + file.getOriginalFilename();
        Files.copy(file.getInputStream()
                , Paths.get(filePathName)
                , StandardCopyOption.REPLACE_EXISTING);

        return new File(filePathName);
    }
}
