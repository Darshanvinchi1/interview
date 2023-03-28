package com.darshan.interview.Repository;

import com.darshan.interview.model.Candidate;
import com.darshan.interview.model.ERole;
import com.darshan.interview.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CandidateRepository extends JpaRepository<Candidate, Integer> {
    Optional<Role> findByName(ERole name);
}
