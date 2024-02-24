package org.example.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User  implements Serializable {
    private String name ;
    private String email ;
    private String mobileNumber ;
}
