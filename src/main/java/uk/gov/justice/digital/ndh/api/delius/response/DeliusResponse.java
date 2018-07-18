package uk.gov.justice.digital.ndh.api.delius.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeliusResponse {
    @JsonProperty("Body")
    private JsonNode body;

    public boolean isFault() {
        return body.hasNonNull("Fault");
    }
}