package uk.gov.justice.digital.ndh.api.nomis;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class Alert {
    private Long bookingId;
    private Integer alertSeq;
    private Long offenderId;
    private String caseloadId;
    private String caseloadType;
    private LocalDate alertDate;
    private LocalDate createdDate;
    private LocalDate expiryDate;
    private String alertType;
    private KeyValue alertCode;
    private String authorizePersonText;
    private String alertStatus;
    private Boolean verified;
    private String comments;
}
