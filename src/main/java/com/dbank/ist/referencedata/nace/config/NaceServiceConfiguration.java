package com.dbank.ist.referencedata.nace.config;

import com.dbank.ist.referencedata.nace.dto.NaceDto;
import com.dbank.ist.referencedata.nace.entity.Nace;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.poiji.option.PoijiOptions;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class NaceServiceConfiguration {

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select().apis(RequestHandlerSelectors.basePackage("com.dbank.ist.referencedata.nace"))
                .paths(PathSelectors.any())
                .build().apiInfo(apiInfo());
    }

    public ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .contact(new Contact("Best Team", "www.dbank.com/bestTeam", "bestTeamMail@dbank.com"))
                .title("The Nace Data Service")
                .description("The service acts as the owner and guardian of the NACE reference data")
                .build();
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.createTypeMap(NaceDto.class, Nace.class)
                .setPropertyCondition(Conditions.isNotNull());
        return modelMapper;
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public PoijiOptions poijiOptions(){
        return PoijiOptions.PoijiOptionsBuilder.settings()
                .addListDelimiter(";")
                .preferNullOverDefault(true)
                .build();
    }

}
