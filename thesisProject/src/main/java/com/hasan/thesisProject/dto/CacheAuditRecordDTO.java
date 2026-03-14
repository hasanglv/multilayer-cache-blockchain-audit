package com.hasan.thesisProject.dto;

import lombok.Data;

import java.math.BigInteger;
@Data
public class CacheAuditRecordDTO {

    private BigInteger operationType;
    private String actor;
    private BigInteger role;
    private String resourceId;
    private String decision;
    private BigInteger timestamp;
    private String hashValue;

    public CacheAuditRecordDTO(
            BigInteger operationType,
            String actor,
            BigInteger role,
            String resourceId,
            String decision,
            BigInteger timestamp,
            String hashValue
    ) {
        this.operationType = operationType;
        this.actor = actor;
        this.role = role;
        this.resourceId = resourceId;
        this.decision = decision;
        this.timestamp = timestamp;
        this.hashValue = hashValue;
    }
}