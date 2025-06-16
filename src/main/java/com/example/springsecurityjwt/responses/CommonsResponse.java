package com.example.springsecurityjwt.responses;

import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@MappedSuperclass
public class CommonsResponse {

    private String createUser;

    private String updateUser;

    private LocalDateTime createDate;

    private LocalDateTime updateDate;

}
