package com.saditec.platform.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saditec.platform.security.jwt.JWTService;
import com.saditec.platform.security.jwt.type.UserResponseToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        String username = obtainUsername(request);
        String password = obtainPassword(request);

        if (username == null || password == null) {
            try {
                UsernameAndPasswordRequest usernameAndPassword = obtainCredentialsFromBody(request);
                username = usernameAndPassword.getUsername();
                password = usernameAndPassword.getPassword();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        UserDetails userEntity = (UserDetails) authResult.getPrincipal();
        response.setStatus(HttpServletResponse.SC_OK);

        String accessToken = jwtService.generateToken(userEntity.getAuthorities(), userEntity);
        long expirationTime = new Date().getTime() + jwtService.expirationTime();

        UserResponseToken userResponseToken = new UserResponseToken(accessToken, expirationTime, "Bearer");
        String responseToken = objectMapper.writeValueAsString(userResponseToken);
        response.setContentType("application/json");
        response.getWriter().println(responseToken);
    }


    private UsernameAndPasswordRequest obtainCredentialsFromBody(HttpServletRequest request) throws IOException {

        String body = request.getReader().lines().collect(Collectors.joining());
        UsernameAndPasswordRequest usernameAndPassword = objectMapper.readValue(body.isEmpty()
                ? "{}"
                : body, UsernameAndPasswordRequest.class);

        if (body.isEmpty()) {
            String authorization = request.getHeader("Authorization");
            String stringB64 = authorization.isEmpty() ? "" : authorization.split(" ")[1];
            String[] credentials = stringB64.isEmpty() ? new String[0] : new String(Base64.getDecoder().decode(stringB64)).split(":");
            usernameAndPassword = new UsernameAndPasswordRequest(credentials.length > 0 ? credentials[0] : "", credentials.length > 1 ? credentials[1] : "");
        }
        return usernameAndPassword;
    }
}

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
class UsernameAndPasswordRequest {

    private String username = "";
    private String password = "";
}