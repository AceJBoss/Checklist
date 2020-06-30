package com.logistics.checklist.response;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class auditorsReponse {
    private Long auditor_id;
    private String picture;
    private String fullname;
    private String username;
    private String roleName;
}