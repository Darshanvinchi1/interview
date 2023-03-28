package com.darshan.interview.model;

import javax.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@Table(name="candidate")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ECandidate name;
}
