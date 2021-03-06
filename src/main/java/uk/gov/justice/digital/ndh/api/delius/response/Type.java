package uk.gov.justice.digital.ndh.api.delius.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Type {
    @JsonProperty("PreCJALicenceConditionType")
    private Category preCJALicenceConditionType;

    @JsonProperty("PostCJALicenceConditionType")
    private Category postCJALicenceConditionType;
}
