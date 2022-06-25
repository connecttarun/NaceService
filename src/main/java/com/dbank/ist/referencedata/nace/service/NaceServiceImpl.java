package com.dbank.ist.referencedata.nace.service;

import com.dbank.ist.referencedata.nace.dto.NaceDto;
import com.dbank.ist.referencedata.nace.entity.Nace;
import com.dbank.ist.referencedata.nace.repository.NaceDataRepository;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
@Slf4j
public class NaceServiceImpl implements NaceService {

    private NaceDataRepository naceDataRepository;

    private ModelMapper modelMapper;

    @Autowired
    public NaceServiceImpl(NaceDataRepository naceDataRepository, ModelMapper modelMapper){
        this.naceDataRepository = naceDataRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<NaceDto> getAllNaceRecords(){
        List<NaceDto> allNaceRecords = new ArrayList<>();
        naceDataRepository.findAll().forEach(
                nace -> {
                   allNaceRecords.add(modelMapper.map(nace, NaceDto.class));
                }
        );
        log.info("Returning {} records for allNaceRecords ", allNaceRecords.size());
        log.debug("And the Id for those are {}.", allNaceRecords.stream().map(nace -> String.valueOf(nace.getOrder()))
                .collect(Collectors.joining(",")));

        return allNaceRecords;
    }

    @Override
    public NaceDto getNaceRecord(int id) throws NoSuchElementException {
        Nace nace = naceDataRepository.findById(id).get();
        log.info("Fetched from database {} for id {}",nace, id);
        return modelMapper.map(nace, NaceDto.class);
    }

    @Override
    public NaceDto putNaceRecord(NaceDto naceDto){
        log.info("Received to save : {}",naceDto.toString());
        Nace savedNaceRecord = naceDataRepository.save(modelMapper.map(naceDto, Nace.class));
        log.debug("Saved with order :{}", savedNaceRecord.getOrder());
        return modelMapper.map(savedNaceRecord, NaceDto.class);
    }

}
