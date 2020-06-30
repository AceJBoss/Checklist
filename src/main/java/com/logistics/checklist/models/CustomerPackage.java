package com.logistics.checklist.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customer_package")
public class CustomerPackage extends AuditModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User  user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "service_package_id")
    private ServicePackage  servicePackage;

    @Column(name= "expiary_date")
    private String expiaryDate;

    @Column(name= "status")
    private ECustomerPacakge status;  
}