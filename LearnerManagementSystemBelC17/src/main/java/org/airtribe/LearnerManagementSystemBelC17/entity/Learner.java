package org.airtribe.LearnerManagementSystemBelC17.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.List;


@Entity
public class Learner {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long learnerId;

  @NotNull
  private String learnerName;

  @NotNull
  @NotEmpty
  @Email
  private String learnerEmail;

  @NotNull
  @NotEmpty(message = "Phone number cannot be empty")
  @Positive(message = "Phone must be between 1 & 10")
  private String learnerPhone;

  @ManyToMany(mappedBy = "learners")
  @JsonIgnore
  private List<Cohort> cohorts;

  public List<Cohort> getCohorts() {
    return cohorts;
  }

  public void setCohorts(List<Cohort> cohorts) {
    this.cohorts = cohorts;
  }

  public Learner(Long learnerId, String learnerName, String learnerEmail, String learnerPhone) {
    this.learnerId = learnerId;
    this.learnerName = learnerName;
    this.learnerEmail = learnerEmail;
    this.learnerPhone = learnerPhone;
  }

  public Learner() {

  }

  public Long getLearnerId() {
    return learnerId;
  }

  public void setLearnerId(Long learnerId) {
    this.learnerId = learnerId;
  }

  public String getLearnerName() {
    return learnerName;
  }

  public void setLearnerName(String learnerName) {
    this.learnerName = learnerName;
  }

  public String getLearnerEmail() {
    return learnerEmail;
  }

  public void setLearnerEmail(String learnerEmail) {
    this.learnerEmail = learnerEmail;
  }

  public String getLearnerPhone() {
    return learnerPhone;
  }

  public void setLearnerPhone(String learnerPhone) {
    this.learnerPhone = learnerPhone;
  }
}
