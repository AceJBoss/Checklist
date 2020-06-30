package com.logistics.checklist.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "user_role")
public class UserRole extends AuditModel{
	
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(name = "role_name")
  private String roleName;

  @JsonIgnore
  @OneToMany(mappedBy = "userRole", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<User> users;
}
