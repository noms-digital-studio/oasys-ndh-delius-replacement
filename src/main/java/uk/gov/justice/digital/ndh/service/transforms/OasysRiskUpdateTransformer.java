package uk.gov.justice.digital.ndh.service.transforms;

import org.springframework.stereotype.Service;
import uk.gov.justice.digital.ndh.api.delius.request.DeliusRiskUpdateSoapBody;
import uk.gov.justice.digital.ndh.api.delius.request.DeliusRiskUpdateSoapEnvelope;
import uk.gov.justice.digital.ndh.api.delius.request.DeliusRiskUpdateSoapHeader;
import uk.gov.justice.digital.ndh.api.delius.request.RiskType;
import uk.gov.justice.digital.ndh.api.delius.request.SubmitRiskDataRequest;
import uk.gov.justice.digital.ndh.api.delius.response.DeliusRiskUpdateResponse;
import uk.gov.justice.digital.ndh.api.oasys.response.RiskUpdateResponse;
import uk.gov.justice.digital.ndh.api.oasys.response.SubmitRiskDataResponseSoapBody;
import uk.gov.justice.digital.ndh.api.oasys.response.SubmitRiskDataResponseSoapEnvelope;
import uk.gov.justice.digital.ndh.api.soap.SoapEnvelope;

import java.util.Optional;

@Service
public class OasysRiskUpdateTransformer {

    public static final String VERSION = "1.0";

    public DeliusRiskUpdateSoapEnvelope deliusRiskUpdateRequestOf(SoapEnvelope oasysRiskUpdate) {
        return DeliusRiskUpdateSoapEnvelope.builder()
                .header(DeliusRiskUpdateSoapHeader
                        .builder()
                        .header(uk.gov.justice.digital.ndh.api.delius.request.Header
                                .builder()
                                .messageId(oasysRiskUpdate.getBody().getRiskUpdateRequest().getHeader().getCorrelationID())
                                .version(VERSION)
                                .build())
                        .build())
                .body(DeliusRiskUpdateSoapBody
                        .builder()
                        .submitRiskDataRequest(SubmitRiskDataRequest
                                .builder()
                                .risk(RiskType
                                        .builder()
                                        .riskOfHarm(oasysRiskUpdate.getBody().getRiskUpdateRequest().getRisk().getRiskofHarm())
                                        .caseReferenceNumber(oasysRiskUpdate.getBody().getRiskUpdateRequest().getCmsProbNumber())
                                        .build())
                                .build())
                        .build())
                .build();
    }

    public SubmitRiskDataResponseSoapEnvelope oasysRiskUpdateResponseOf(DeliusRiskUpdateResponse deliusRiskUpdateResponse, Optional<SoapEnvelope> maybeOasysRiskUpdate) {
        return SubmitRiskDataResponseSoapEnvelope
                .builder()
                .body(SubmitRiskDataResponseSoapBody
                        .builder()
                        .response(RiskUpdateResponse
                                .builder()
                                .caseReferenceNumber(deliusRiskUpdateResponse.getCaseReferenceNumber().orElse(null))
                                .header(maybeOasysRiskUpdate.map(soapEnvelope -> soapEnvelope.getBody().getRiskUpdateRequest().getHeader()).orElse(null))
                                .build())
                        .build())
                .build();
    }

}
