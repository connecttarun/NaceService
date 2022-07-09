package com.dbank.ist.referencedata.nace.unittests.service;

import com.dbank.ist.referencedata.nace.dto.NaceDto;
import com.dbank.ist.referencedata.nace.entity.Nace;
import com.dbank.ist.referencedata.nace.repository.NaceDataRepository;
import com.dbank.ist.referencedata.nace.service.NaceServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static com.dbank.ist.referencedata.nace.util.NaceTestUtils.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
class NaceServiceImplTest {

    @Mock
    NaceDataRepository naceDataRepository;
    @Mock
    ModelMapper mapper;
    @InjectMocks
    NaceServiceImpl naceService;

    @Test
    void testGetNaceRecord() {

        Nace nace = getStubNace(ID_FIVE);
        NaceDto expectedNaceRecord = NaceDto.builder().order(ID_FIVE).description(DESCRIPTION_FIVE).build();

        Mockito.when(naceDataRepository.findByOrder(ID_FIVE)).thenReturn(Optional.of(nace));
        Mockito.when(mapper.map(nace, NaceDto.class)).thenReturn(expectedNaceRecord);

        NaceDto actualNaceRecord = naceService.getNaceRecord(ID_FIVE);

        Mockito.verify(naceDataRepository, Mockito.times(1)).findByOrder(ID_FIVE.intValue());
        Mockito.verify(mapper, Mockito.times(1)).map(nace, NaceDto.class);
        assertEquals(expectedNaceRecord, actualNaceRecord, "The received naceDto record didn't match");
    }

    @Test
    void testGetAllNaceRecords() {
        Nace naceFive = getStubNace(ID_FIVE);
        Nace naceTwo = getStubNace(ID_TWO);
        NaceDto naceDtoFive = getNaceDto(ID_FIVE);
        NaceDto naceDtoTwo = getNaceDto(ID_TWO);

        List<NaceDto> expectedNaceRecord = List.of(naceDtoFive, naceDtoTwo);

        Mockito.when(naceDataRepository.findAll()).thenReturn(List.of(naceFive, naceTwo));
        Mockito.when(mapper.map(naceFive, NaceDto.class)).thenReturn(naceDtoFive);
        Mockito.when(mapper.map(naceTwo, NaceDto.class)).thenReturn(naceDtoTwo);

        List<NaceDto> actualNaceRecords = naceService.getAllNaceRecords();

        Mockito.verify(naceDataRepository, Mockito.times(1)).findAll();
        Mockito.verify(mapper, Mockito.times(2)).map(any(), eq(NaceDto.class));
        assertThat(actualNaceRecords, containsInAnyOrder(expectedNaceRecord.toArray()));
    }

    @Test
    void testPutNaceRecord() {
        Nace naceFive = getStubNace(ID_FIVE);
        NaceDto naceDtoFive = getNaceDto(ID_FIVE);

        Mockito.when(naceDataRepository.save(naceFive)).thenReturn(naceFive);
        Mockito.when(mapper.map(naceFive, NaceDto.class)).thenReturn(naceDtoFive);
        Mockito.when(mapper.map(naceDtoFive, Nace.class)).thenReturn(naceFive);

        NaceDto actualNaceRecord = naceService.putNaceRecord(naceDtoFive);

        Mockito.verify(naceDataRepository).save(naceFive);
        Mockito.verify(mapper, Mockito.times(2)).map(any(), any());

        assertEquals(naceDtoFive, actualNaceRecord, "The Actual NaceDto received was different from expected");
    }
}