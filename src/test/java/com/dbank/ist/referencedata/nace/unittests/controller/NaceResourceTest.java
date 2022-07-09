package com.dbank.ist.referencedata.nace.unittests.controller;

import com.dbank.ist.referencedata.nace.controller.NaceResource;
import com.dbank.ist.referencedata.nace.dto.NaceDto;
import com.dbank.ist.referencedata.nace.service.NaceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.hibernate.id.IdentifierGenerationException;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.NoSuchElementException;

import static com.dbank.ist.referencedata.nace.service.NaceServiceImpl.FILE_UPLOADED_SUCCESSFULLY;
import static com.dbank.ist.referencedata.nace.util.NaceTestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
@WebMvcTest(value = NaceResource.class)
class NaceResourceTest {

    @MockBean
    static NaceService naceService;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;

    @SneakyThrows
    @Test
    void testGetNaceDetails() {
        Mockito.when(naceService.getNaceRecord(ID_FIVE)).thenReturn(getNaceDto(ID_FIVE));
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/nacedata/" + ID_FIVE)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        Mockito.verify(naceService, Mockito.times(1)).getNaceRecord(ID_FIVE);
        assertEquals("{\"order\":5,\"level\":null,\"code\":null,\"parent\":null,\"description\":\"Description for record 5\",\"thisItemIncludes\":null,\"thisItemAlsoIncludes\":null,\"rulings\":null,\"thisItemExcludes\":null,\"referenceToIsicRev4\":null}"
                , mvcResult.getResponse().getContentAsString()
                , "The response body is not as expected");
        assertEquals(HttpStatus.OK, HttpStatus.valueOf(mvcResult.getResponse().getStatus()), "The response status is not as expected");
    }

    @SneakyThrows
    @Test
    public void testGetNaceDetailsForAbsentRecord() {
        Mockito.when(naceService.getNaceRecord(ID_FIVE)).thenThrow(NoSuchElementException.class);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/nacedata/" + ID_FIVE)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        Mockito.verify(naceService, Mockito.times(1)).getNaceRecord(ID_FIVE);
        assertEquals("Details not found", mvcResult.getResponse().getContentAsString(), "The response body is not as expected");
        assertEquals(HttpStatus.NOT_FOUND, HttpStatus.valueOf(mvcResult.getResponse().getStatus()), "The response status is not as expected");
    }

    @SneakyThrows
    @Test
    public void testPutNaceDetailsForAbsentOrderField() {
        NaceDto naceDto = getNaceDto(ID_FIVE);
        naceDto.setOrder(null);
        Mockito.when(naceService.putNaceRecord(naceDto)).thenThrow(IdentifierGenerationException.class);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/nacedata/")
                .content(mapper.writeValueAsString(naceDto).replace("\"order\":5,", "")) //removing order
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        Mockito.verify(naceService).putNaceRecord(naceDto);
        assertEquals("The \"Order\" is a mandatory field", mvcResult.getResponse().getContentAsString(), "The response body is not as expected");
        assertEquals(HttpStatus.BAD_REQUEST, HttpStatus.valueOf(mvcResult.getResponse().getStatus()), "The response status is not as expected");

    }

    @Test
    @SneakyThrows
    public void testPutNaceRecord() {

        NaceDto naceDto = getNaceDto(ID_FIVE);
        Mockito.when(naceService.putNaceRecord(naceDto)).thenReturn(naceDto);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/nacedata/")
                .content(mapper.writeValueAsString(naceDto))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        Mockito.verify(naceService).putNaceRecord(naceDto);
        assertEquals("{\"order\":5,\"level\":null,\"code\":null,\"parent\":null,\"description\":\"Description for record 5\",\"thisItemIncludes\":null,\"thisItemAlsoIncludes\":null,\"rulings\":null,\"thisItemExcludes\":null,\"referenceToIsicRev4\":null}"
                , mvcResult.getResponse().getContentAsString()
                , "The response body is not as expected");
        assertEquals(HttpStatus.CREATED, HttpStatus.valueOf(mvcResult.getResponse().getStatus()), "The response status is not as expected");
    }

    @SneakyThrows
    @Test
    public void testGetAllNaceDetails() {
        {
            Mockito.when(naceService.getAllNaceRecords()).thenReturn(List.of(getNaceDto(ID_FIVE), getNaceDto(ID_TWO)));
            RequestBuilder requestBuilder = MockMvcRequestBuilders
                    .get("/nacedata/" + "all")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON_VALUE);

            MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

            Mockito.verify(naceService, Mockito.times(1)).getAllNaceRecords();
            assertEquals("[{\"order\":5,\"level\":null,\"code\":null,\"parent\":null,\"description\":\"Description for record 5\",\"thisItemIncludes\":null,\"thisItemAlsoIncludes\":null,\"rulings\":null,\"thisItemExcludes\":null,\"referenceToIsicRev4\":null}," +
                            "{\"order\":2,\"level\":null,\"code\":null,\"parent\":null,\"description\":\"Description for record 2\",\"thisItemIncludes\":null,\"thisItemAlsoIncludes\":null,\"rulings\":null,\"thisItemExcludes\":null,\"referenceToIsicRev4\":null}]"
                    , mvcResult.getResponse().getContentAsString()
                    , "The response body is not as expected");
            assertEquals(HttpStatus.OK, HttpStatus.valueOf(mvcResult.getResponse().getStatus()), "The response status is not as expected");
        }
    }

    @SneakyThrows
    @Test
    public void testUploadBulkUploadDataViaExcel() {
        MockMultipartFile mockMultipartFile = getMockMultipartFile();
        Mockito.when(naceService.bulkUploadFromExcel(mockMultipartFile)).thenReturn(FILE_UPLOADED_SUCCESSFULLY);

        mockMvc.perform(MockMvcRequestBuilders.multipart("/nacedata/bulkupload")
                        .file(mockMultipartFile))
                        .andExpect(status().isCreated());

        Mockito.verify(naceService).bulkUploadFromExcel(mockMultipartFile);
    }
}