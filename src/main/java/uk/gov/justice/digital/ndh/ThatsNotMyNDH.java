package uk.gov.justice.digital.ndh;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.scheduling.annotation.EnableScheduling;
import uk.gov.justice.digital.ndh.service.Sequence;

@SpringBootApplication
@Slf4j
@EnableJms
@EnableScheduling
public class ThatsNotMyNDH {

    public static final String ASSESSMENT_PROCESS = "NDH_CMS_Web_Client";

    public static void main(String[] args) {
        SpringApplication.run(ThatsNotMyNDH.class, args);
    }

    @Bean(name = "globalObjectMapper")
    @Primary
    public ObjectMapper objectMapper() {
        return new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .setSerializationInclusion(JsonInclude.Include.NON_ABSENT)
                .setSerializationInclusion(JsonInclude.Include.NON_EMPTY)
                .registerModules(new Jdk8Module(), new JavaTimeModule());
    }

    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter(@Qualifier("globalObjectMapper") ObjectMapper objectMapper) {
        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
        jsonConverter.setObjectMapper(objectMapper);
        return jsonConverter;
    }

    @Bean
    public XmlMapper xmlMapper(MappingJackson2XmlHttpMessageConverter xmlConverter) {
        final ObjectMapper objectMapper = xmlConverter.getObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return (XmlMapper) objectMapper;
    }

    @Bean
    public MappingJackson2XmlHttpMessageConverter xmlConverter() {
        final MappingJackson2XmlHttpMessageConverter mappingJackson2XmlHttpMessageConverter = new MappingJackson2XmlHttpMessageConverter();
        final XmlMapper xmlMapper = (XmlMapper) mappingJackson2XmlHttpMessageConverter.getObjectMapper();

        xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        xmlMapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);
        return mappingJackson2XmlHttpMessageConverter;
    }


    @Bean
    public ApplicationListener<ApplicationReadyEvent> buildInfoLogger() {
        return event -> {
            try {
                log.info("BUILD PROPERTIES:");
                BuildProperties buildProperties = (BuildProperties) event.getApplicationContext().getBean("buildProperties");
                buildProperties.iterator().forEachRemaining(prop -> log.info("{} : {}", prop.getKey(), prop.getValue()));
            } catch (NoSuchBeanDefinitionException nsbde) {
                log.warn("No build info found! Is this a local build?");
            }
        };
    }

    @Bean
    public Sequence xtagSequence(@Value("${xtag.sequence.initialVal:0}") Long initialValue,
                                 @Value("${xtag.sequence.maxVal:999999}") Long maxValue) {
        return new Sequence(initialValue, maxValue);

    }

    @Bean
    public Resource mappingCodeDataResource() {
        return new ClassPathResource("mapping_code_data.csv");
    }

    @Bean
    public Resource requirementLookupResource() {
        return new ClassPathResource("requirement_lookup.csv");
    }

    @Bean
    public Resource requirementBlacklistResource() {
        return new ClassPathResource("ignored_requirement_codes.json");
    }

}
