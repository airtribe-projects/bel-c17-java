package org.airtribe.LearnerManagementSystemBelC17.repository;

import java.util.List;
import java.util.Optional;
import org.airtribe.LearnerManagementSystemBelC17.entity.Learner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface LearnerRepository extends JpaRepository<Learner, Long> {
  public List<Learner> findByLearnerName(String learnerName);

  @Query("SELECT l FROM Learner l WHERE l.learnerName = ?1")
  public List<Learner> findByMyName(String name);

  Optional<Learner> findByLearnerEmail(String learnerEmail);
}
