package com.example.testbot.model.response;

import lombok.Data;
import lombok.experimental.Accessors;

@Data 
@Accessors(chain = true)
public class UserResponse {
    private Integer id;
    private String login;
    private String firstName;
    private String lastName;
    private String middleName;
    private Integer age;
    private AddressResponse address;
}
