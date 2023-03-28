package com.darshan.interview.Playload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class VoteRequest {

    @NotNull
    private Integer userId;

    @NotNull
    private Integer CandidateId;
}
