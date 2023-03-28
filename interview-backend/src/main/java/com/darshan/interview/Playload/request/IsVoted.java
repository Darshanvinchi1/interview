package com.darshan.interview.Playload.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
public class IsVoted {

    @NotNull
    private Integer userId;

    @JsonCreator
    public IsVoted(@JsonProperty("userId") Integer userId) {
        this.userId = userId;
    }
}
