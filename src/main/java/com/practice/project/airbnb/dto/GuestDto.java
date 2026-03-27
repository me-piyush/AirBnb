package com.practice.project.airbnb.dto;

import com.practice.project.airbnb.entity.User;
import com.practice.project.airbnb.entity.enums.Gender;

public class GuestDto {

    private Long id;
    private User user;
    private String name;
    private Gender gender;
    private Integer age;

}
