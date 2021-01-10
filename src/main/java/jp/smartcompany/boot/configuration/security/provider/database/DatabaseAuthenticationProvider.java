package jp.smartcompany.boot.configuration.security.provider.database;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import jp.smartcompany.boot.configuration.security.SecurityConstant;
import jp.smartcompany.boot.configuration.security.dto.SmartUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DatabaseAuthenticationProvider implements AuthenticationProvider {

    private final DatabaseUserDetailsServiceImpl userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 获取前端表单中输入后返回的用户名、密码
        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();
        String md5Password = DigestUtil.md5Hex(password);

        SmartUserDetails userDetails = (SmartUserDetails) userDetailsService.loadUserByUsernamePassword(username,md5Password);
        if (!StrUtil.equals(md5Password, userDetails.getPassword())) {
            throw new BadCredentialsException(SecurityConstant.PASSWORD_ERROR);
        }
        return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }

}
