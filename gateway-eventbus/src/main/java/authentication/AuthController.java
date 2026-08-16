package authentication;

import authentication.dto.SigninRequest;
import authentication.dto.SignupRequest;
import authentication.dto.SignupResposne;
import authentication.dto.SinginResponse;
import authentication.jwt.Util;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/uth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final Util jwtUtil;

    @PostMapping("/signin")
    public SinginResponse signIn(@Valid @RequestBody SigninRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtUtil.tokenGen(userDetails.getUsername());

        return new SinginResponse(token);
    }

    @PostMapping("/signup")
    public SignupResposne signUp(@Valid @RequestBody SignupRequest request) {

        User newUser = User.builder()
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .role("ROL_USER")
                .build();

        userRepository.save(newUser);

        return new SignupResposne("regisered");
    }
}