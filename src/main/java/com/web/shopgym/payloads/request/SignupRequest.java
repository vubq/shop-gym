package com.web.shopgym.payloads.request;

import com.web.shopgym.enums.EUserStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {

    private String userName;
    private String email;
    private String password;
    private String phoneNumber;
    private String fullName;
    private String avatar;
    private EUserStatus status;
}
