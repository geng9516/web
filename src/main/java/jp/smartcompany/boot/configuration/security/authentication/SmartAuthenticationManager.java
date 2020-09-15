package jp.smartcompany.boot.configuration.security.authentication;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SmartAuthenticationManager implements AuthenticationManager {

  private final SmartAuthenticationProvider authenticationProvider;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    Authentication result = authenticationProvider.authenticate(authentication);
    if (Objects.nonNull(result)) {
      return result;
    }
    throw new ProviderNotFoundException("providerNotFound");
  }
}
