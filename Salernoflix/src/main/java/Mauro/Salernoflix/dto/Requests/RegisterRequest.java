package Mauro.Salernoflix.dto.Requests;

import Mauro.Salernoflix.dto.Enum.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    private String username;

    private String password;

    private Role role;

}
