package uk.gov.justice.digital.ndh.api.oasys.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Code {
    @JacksonXmlProperty(localName = "Value", namespace = "http://www.w3.org/2003/05/soap-envelope")
    private String value;
}
