package com.darshan.interview.controller;

import com.darshan.interview.Playload.request.IsVoted;
import com.darshan.interview.Playload.request.VoteRequest;
import com.darshan.interview.Playload.response.MessageResponse;
import com.darshan.interview.Repository.VoteRepository;
import com.darshan.interview.model.Vote;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@AllArgsConstructor
@NoArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/vote")
public class VoteController {

    @Autowired
    VoteRepository voteRepository;

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> voteCreate( @Valid @RequestBody VoteRequest voteRequest){
        if(voteRepository.existsByUserId(voteRequest.getUserId())){
            return ResponseEntity.badRequest().body("Error: You already voted");
        }
        if(voteRequest.getUserId() == null){
           return ResponseEntity.badRequest().build();
        }
        if(voteRequest.getCandidateId() == null){
            return ResponseEntity.badRequest().build();
        }

        Vote vote = new Vote(
                voteRequest.getUserId(),
                voteRequest.getCandidateId()
        );

        voteRepository.save(vote);

        return ResponseEntity.ok(new MessageResponse("Voted Successfully"));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> voteCount() {
        HashMap<String,Integer> map = new HashMap<>();

        map.put("Candidate_4",voteRepository.countAllByCandidateId(4));
        map.put("Candidate_3",voteRepository.countAllByCandidateId(3));
        map.put("Candidate_2",voteRepository.countAllByCandidateId(2));
        map.put("Candidate_1",voteRepository.countAllByCandidateId(1));

        return ResponseEntity.ok().body(map);
    }

    @PostMapping("voted")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<HashMap<String,Boolean>> voted(@Valid @RequestBody IsVoted voted){
        HashMap<String,Boolean> map = new HashMap<>();
        if(voteRepository.existsByUserId(voted.getUserId())){
            map.put("isVoted",true);
        }else {
            map.put("isVoted",false);
        }

        return ResponseEntity.ok().body(map);
    }
}
