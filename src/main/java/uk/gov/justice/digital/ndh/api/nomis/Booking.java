package uk.gov.justice.digital.ndh.api.nomis;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Value
@Builder
public class Booking {
    private Long bookingId;
    private String bookingNo;
    private Long offenderId;
    private Long rootOffenderId;
    private AgencyLocation agencyLocation;
    private AgencyInternalLocation livingUnit;
    private Boolean activeFlag;
    private String bookingStatus;
    private String inOutStatus;
    private String statusReason;
    private LocalDate startDate;
    private Optional<LocalDate> endDate;
    private LocalDateTime caseDateTime;
    private Long bookingSequence;
    private ExternalMovement lastMovement;
}
