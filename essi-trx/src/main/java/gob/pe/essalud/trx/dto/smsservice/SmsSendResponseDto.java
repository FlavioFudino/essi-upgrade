package gob.pe.essalud.trx.dto.smsservice;

import lombok.Data;

import java.util.List;

@Data
public class SmsSendResponseDto {
    private int status;
    private String reason;
    private Result result;
}

@Data
class Result {
    private int totalRequest;
    private int totalFailed;
    private List<Request> receivedRequests;
    private List<Request> failedRequests;
    private String dateToSend;
    private String timeZone;
}

@Data
class Request {
    private String mobile;
    private String transactionId;
    private int status;
    private String reason;
}