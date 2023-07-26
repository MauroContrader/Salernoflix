package Mauro.Salernoflix.dto.Requests;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequest {

    private String username;

    private String password;

    @Schema(defaultValue = "false")
    private Boolean rememberMe;

}
