package com.darshan.interview.model;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private Integer userId;

    @NotNull
    private Integer candidateId;


    public Vote(Integer userId, Integer candidateId) {
        this.userId = userId;
        this.candidateId =candidateId;
    }
}
