package com.web.shopgym.payloads.request;

import com.web.shopgym.enums.EUserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private String id;
    private String userName;
    private String email;
    private String password;
    private String phoneNumber;
    private String address;
    private String fullName;
    private String avatar;
    private EUserStatus status;
    private List<String> roles;
}
