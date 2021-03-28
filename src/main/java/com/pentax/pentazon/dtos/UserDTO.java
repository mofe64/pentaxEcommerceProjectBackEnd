package com.pentax.pentazon.dtos;

import com.pentax.pentazon.models.Address;
import com.pentax.pentazon.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String id;
    @NotNull
    @NotBlank(message = "firstname field cannot be empty")
    @Size(min = 2, message = "Firstname cannot be less than 2 characters")
    private String firstName;
    @NotNull
    @NotBlank(message = "lastname field cannot be empty")
    @Size(min = 2, message = "lastname cannot be less than 2 characters")
    private String lastName;
    @Email(message = "Please provide a valid email")
    private String email;
    @NotNull
    @NotBlank(message = "username field cannot be empty")
    private String username;
    private List<Address> addresses = new ArrayList<>();
    @NotNull
    @NotBlank
    private String password;
    private String cartId;

    public static User unpackDTO(UserDTO userDTO){
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setFirstname(userDTO.getFirstName());
        user.setLastname(userDTO.getLastName());
        user.setAddresses(userDTO.getAddresses());
        user.setPassword(userDTO.getPassword());
        user.setUsername(userDTO.getUsername());
        return user;
    }

    public static UserDTO packDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setAddresses(user.getAddresses());
        userDTO.setEmail(user.getEmail());
        userDTO.setFirstName(user.getFirstname());
        userDTO.setLastName(user.getLastname());
        userDTO.setUsername(user.getUsername());
        userDTO.setPassword("");
        userDTO.setCartId(user.getCartId());
        userDTO.setId(user.getId());
        return userDTO;
    }
}
