package com.usermanagement.model.requests;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CreateUserRequest {

    String username;
    String email;
    String firstname;
    String lastname;
    String password;

}