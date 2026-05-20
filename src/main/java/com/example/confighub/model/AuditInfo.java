package com.example.confighub.model;

import java.time.Instant;

public class AuditInfo {

    private String createdBy;
    private String modifiedBy;
    private Instant createdAt;
    private Instant modifiedAt;
    private String internalTraceId;

    public AuditInfo() {
    }

    public AuditInfo(String createdBy, String modifiedBy, Instant createdAt, Instant modifiedAt) {
        this.createdBy = createdBy;
        this.modifiedBy = modifiedBy;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(Instant modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public String getInternalTraceId() {
        return internalTraceId;
    }

    public void setInternalTraceId(String internalTraceId) {
        this.internalTraceId = internalTraceId;
    }
}
