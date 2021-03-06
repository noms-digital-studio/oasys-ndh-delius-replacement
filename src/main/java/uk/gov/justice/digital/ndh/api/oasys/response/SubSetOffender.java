package uk.gov.justice.digital.ndh.api.oasys.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class SubSetOffender {

    @JacksonXmlProperty(namespace = "http://www.hp.com/NDH_Web_Service/subsetoffender", localName = "CMSProbNumber")
    @JsonProperty("CMSProbNumber")
    private String cmsProbNumber;
    @JacksonXmlProperty(namespace = "http://www.hp.com/NDH_Web_Service/subsetoffender", localName = "FamilyName")
    private String familyName;
    @JacksonXmlProperty(namespace = "http://www.hp.com/NDH_Web_Service/subsetoffender", localName = "Forename1")
    private String forename1;
    @JacksonXmlProperty(namespace = "http://www.hp.com/NDH_Web_Service/subsetoffender", localName = "Forename2")
    private String forename2;
    @JacksonXmlProperty(namespace = "http://www.hp.com/NDH_Web_Service/subsetoffender", localName = "Forename3")
    private String forename3;
    @JacksonXmlProperty(namespace = "http://www.hp.com/NDH_Web_Service/subsetoffender", localName = "DateOfBirth")
    private String dateOfBirth;
    @JacksonXmlProperty(namespace = "http://www.hp.com/NDH_Web_Service/subsetoffender", localName = "Gender")
    private String gender;
    @JacksonXmlProperty(namespace = "http://www.hp.com/NDH_Web_Service/subsetoffender", localName = "LAOIndicator")
    private String laoIndicator;
    @JacksonXmlProperty(namespace = "http://www.hp.com/NDH_Web_Service/subsetevent", localName = "SubSetEvent")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<SubSetEvent> subSetEvents;
}
