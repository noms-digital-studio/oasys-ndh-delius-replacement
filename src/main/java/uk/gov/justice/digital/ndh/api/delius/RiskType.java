package uk.gov.justice.digital.ndh.api.delius;


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RiskType {
    @JacksonXmlProperty(localName = "CaseReferenceNumber", namespace = "http://www.bconline.co.uk/oasys/risk")
    private String caseReferenceNumber;
    @JacksonXmlProperty(localName = "RiskOfHarm", namespace = "http://www.bconline.co.uk/oasys/risk")
    private String riskOfHarm;
}
