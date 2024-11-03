package com.lld4.productservice.dtos;

import lombok.Getter;
import lombok.Setter;
import com.lld4.productservice.models.Role;
import com.lld4.productservice.models.User;

import java.util.List;

@Setter
@Getter
public class UserDto {
    private String name;
    private String email;
    private List<Role> roles;
    private Boolean isEmailVerified;
    private Boolean isDeleted;;


    /* This is the best practice to out converted/mapped at the DTO class itself. Take the user as inout and
    translate it into the UserDto */
    public static UserDto from(User user) {
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setName(user.getName());
        userDto.setIsEmailVerified(user.getIsEmailVerified());
        userDto.setIsDeleted(user.getIsDeleted());
        userDto.setRoles(user.getRoles());
        return userDto;
    }
}
