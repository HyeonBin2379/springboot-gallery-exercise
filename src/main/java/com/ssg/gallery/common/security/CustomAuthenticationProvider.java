package com.ssg.gallery.common.security;

import com.ssg.gallery.common.util.HashUtils;
import com.ssg.gallery.member.entity.LoginUser;
import com.ssg.gallery.member.entity.Member;
import com.ssg.gallery.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder bcryptPasswordEncoder;
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String rawPwd = (String) authentication.getCredentials();

        LoginUser userDetails = (LoginUser) userDetailsService.loadUserByUsername(username);

        // 기존 HashUtil 사용 시 비밀번호를 Bcrypt로 암호화 후 저장
        if (userDetails.getSalt() != null) {
            String oldHash = HashUtils.generateHash(rawPwd, userDetails.getSalt());

            if (!oldHash.equals(userDetails.getPassword())) {
                throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
            }

            String newBcryptPwd = bcryptPasswordEncoder.encode(rawPwd);
            Member member = memberRepository.findByLoginId(username)
                    .orElseThrow(() -> new BadCredentialsException("사용자 없음"));
            member.migratePwd(newBcryptPwd);
        } else {
            if (!bcryptPasswordEncoder.matches(rawPwd, userDetails.getPassword())) {
                throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
            }
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
