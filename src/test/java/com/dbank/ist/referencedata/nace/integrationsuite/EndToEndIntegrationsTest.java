package com.dbank.ist.referencedata.nace.integrationsuite;

import com.dbank.ist.referencedata.nace.NaceServiceApplication;
import com.dbank.ist.referencedata.nace.dto.NaceDto;
import com.dbank.ist.referencedata.nace.repository.NaceDataRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.dbank.ist.referencedata.nace.util.NaceTestUtils.*;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest(classes = NaceServiceApplication.class,
                webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class EndToEndIntegrationsTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    NaceDataRepository repo;


    @Test
    @SneakyThrows
    public void testNoSuchRecordScenarioAPI() {

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/nacedata/" + 10)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        assertEquals("Details not found", mvcResult.getResponse().getContentAsString(), "The response body is not as expected");
        assertEquals(HttpStatus.NOT_FOUND, HttpStatus.valueOf(mvcResult.getResponse().getStatus()), "The response status is not as expected");
    }

    @SneakyThrows
    @Test
    public void testPutNaceDetailsForAbsentCodeField() {
        NaceDto naceDto = getNaceDto(ID_FIVE);
        naceDto.setCode(null);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/nacedata/")
                .content(mapper.writeValueAsString(naceDto))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        assertEquals("The \"Code\" is a mandatory field", mvcResult.getResponse().getContentAsString(), "The response body is not as expected");
        assertEquals(HttpStatus.BAD_REQUEST, HttpStatus.valueOf(mvcResult.getResponse().getStatus()), "The response status is not as expected");
        assertEquals(0, repo.count(), "The count in DB table is not as expected");
    }

    @SneakyThrows
    @Test
    public void testPuttingANewRecord() {

        assertEquals(0, repo.count(), "The count in DB table is not as expected");

        NaceDto naceDto = getNaceDto(ID_FIVE);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/nacedata/")
                .content(mapper.writeValueAsString(naceDto))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        assertEquals("{\"order\":5,\"level\":null,\"code\":\"5\",\"parent\":null,\"description\":\"Description for record 5\",\"thisItemIncludes\":null,\"thisItemAlsoIncludes\":null,\"rulings\":null,\"thisItemExcludes\":null,\"referenceToIsicRev4\":null}"
                , mvcResult.getResponse().getContentAsString()
                , "The response body is not as expected");
        assertEquals(HttpStatus.CREATED, HttpStatus.valueOf(mvcResult.getResponse().getStatus()), "The response status is not as expected");
        assertEquals(1, repo.count(), "The count in DB table is not as expected");
    }

    @SneakyThrows
    @Test
    public void testGetNaceDetailsAPI() {

        repo.save(getStubNace(ID_FIVE));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/nacedata/" + ID_FIVE)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        assertEquals("{\"order\":5,\"level\":null,\"code\":\"5\",\"parent\":null,\"description\":\"Description for record 5\",\"thisItemIncludes\":null,\"thisItemAlsoIncludes\":null,\"rulings\":null,\"thisItemExcludes\":null,\"referenceToIsicRev4\":null}"
                , mvcResult.getResponse().getContentAsString()
                , "The response body is not as expected");
        assertEquals(HttpStatus.OK, HttpStatus.valueOf(mvcResult.getResponse().getStatus()), "The response status is not as expected");
    }

    @SneakyThrows
    @Test
    public void testGetAllNaceDetailsAPI() {

        repo.save(getStubNace(ID_FIVE));
        repo.save(getStubNace(ID_TWO));

        {
            RequestBuilder requestBuilder = MockMvcRequestBuilders
                    .get("/nacedata/" + "all")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON_VALUE);

            MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

            assertTrue("The response body is not as expected", mvcResult.getResponse().getContentAsString().contains(
                    "{\"order\":5,\"level\":null,\"code\":\"5\",\"parent\":null,\"description\":\"Description for record 5\",\"thisItemIncludes\":null,\"thisItemAlsoIncludes\":null,\"rulings\":null,\"thisItemExcludes\":null,\"referenceToIsicRev4\":null}"));
            assertTrue("The response body is not as expected", mvcResult.getResponse().getContentAsString().contains(
                    "{\"order\":2,\"level\":null,\"code\":\"2\",\"parent\":null,\"description\":\"Description for record 2\",\"thisItemIncludes\":null,\"thisItemAlsoIncludes\":null,\"rulings\":null,\"thisItemExcludes\":null,\"referenceToIsicRev4\":null}"));
            assertEquals(HttpStatus.OK, HttpStatus.valueOf(mvcResult.getResponse().getStatus()), "The response status is not as expected");
        }
    }

    @SneakyThrows
    @Test
    public void testUploadBulkUploadDataViaExcel() {

       MockMultipartFile mockMultipartFile = getMockMultipartFile();

        mockMvc.perform(MockMvcRequestBuilders.multipart("/nacedata/bulkupload")
                        .file(mockMultipartFile))
                .andExpect(status().isCreated());
    }

    @BeforeEach
    public void beforeAll() {
        clearNaceTable();
    }

    private void clearNaceTable() {
        log.info("repo.deleteAll() is being triggered. The table currently contains {} records.", repo.count());
        repo.deleteAll();
        log.info("repo.deleteAll() was triggered. The table now contains {} records.", repo.count());
    }
}
