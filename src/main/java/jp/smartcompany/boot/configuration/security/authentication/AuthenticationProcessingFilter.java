package jp.smartcompany.boot.configuration.security.authentication;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jp.smartcompany.boot.configuration.security.SecurityConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

@RequiredArgsConstructor
public class AuthenticationProcessingFilter extends UsernamePasswordAuthenticationFilter {

  private final ObjectMapper objectMapper;

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
    UsernamePasswordAuthenticationToken authRequest;
    try (InputStream is = request.getInputStream()) {
      JsonNode loginForm = objectMapper.readTree(is);
      String username = loginForm.get("username").textValue();
      String password = loginForm.get("password").textValue();
      authRequest = new UsernamePasswordAuthenticationToken(username,password);
    } catch (IOException e) {
      e.printStackTrace();
      throw new BadCredentialsException(SecurityConstant.USERNAME_OR_PASSWORD_NOT_EXISTS);
    }
    setDetails(request,authRequest);
    return this.getAuthenticationManager().authenticate(authRequest);
  }

}
