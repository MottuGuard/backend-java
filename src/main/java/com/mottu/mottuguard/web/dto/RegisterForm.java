package com.mottu.mottuguard.web.dto;

import jakarta.validation.constraints.*;
public class RegisterForm {
    @NotBlank private String name;
    @Email @NotBlank private String email;
    @Size(min=4) private String password;
    public String getName(){return name;} public void setName(String v){this.name=v;}
    public String getEmail(){return email;} public void setEmail(String v){this.email=v;}
    public String getPassword(){return password;} public void setPassword(String v){this.password=v;}
}
