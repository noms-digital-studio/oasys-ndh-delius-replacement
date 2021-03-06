package uk.gov.justice.digital.ndh.api.nomis;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class AgencyInternalLocation {
    private Long internalLocationId;
    private String internalLocationCode;
    private String agencyLocationId;
    private String internalLocationType;
    private String description;
    private String securityLevelCode;
    private Integer capacity;
    private Long parentInternalLocationId;
    private Boolean active;
    private Integer listSequence;
    private Long cnaNumber;
    private Boolean certified;
    private LocalDate deactivateDate;
    private LocalDate reactivateDate;
    private String deactivateReasonCode;
    private String comments;
    private String userDesc;
    private Long acaCapRating;
    private String unitType;
    private Integer operationCapacity;
    private Integer numberOfOccupants;
    private String trackingFlag;

}
