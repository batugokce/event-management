package com.bgokce.eventmanagement.usecases.login;

import com.bgokce.eventmanagement.DTO.AuthenticationResponse;
import com.bgokce.eventmanagement.DTO.LoginDTO;
import com.bgokce.eventmanagement.configuration.MyUserDetailsService;
import com.bgokce.eventmanagement.usecases.manageperson.Authority;
import com.bgokce.eventmanagement.usecases.manageperson.Person;
import com.bgokce.eventmanagement.utilities.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final MyUserDetailsService myUserDetailsService;
    private final LoginService loginService;

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody LoginDTO personDTO) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(personDTO.getUsername(),personDTO.getPassword())
            );
        }
        catch (BadCredentialsException e){
            throw new Exception("Wrong id or pw",e);
        }

        final UserDetails userDetails = myUserDetailsService.loadUserByUsername(
                personDTO.getUsername()
        );

        final String jwt = jwtUtil.generateToken(userDetails);

        loginService.setFirebaseToken(personDTO.getUsername(),personDTO.getFirebaseToken());

        String type = ((Authority) userDetails.getAuthorities().toArray()[0]).getAuthority();
        String tc = ((Person) userDetails).getTcNo();
        return ResponseEntity.ok().body(new AuthenticationResponse(jwt,type,tc));

    }
}
