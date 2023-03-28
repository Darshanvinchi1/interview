package com.darshan.interview.Repository;

import com.darshan.interview.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote,Integer> {
    Integer countAllByCandidateId(Integer id);

    Boolean existsByUserId(Integer id);
}
