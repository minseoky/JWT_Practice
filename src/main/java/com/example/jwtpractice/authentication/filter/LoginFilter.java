package com.example.jwtpractice.authentication.filter;

import com.example.jwtpractice.authentication.dto.CustomUserDetails;
import com.example.jwtpractice.authentication.provider.JwtProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

@Slf4j
@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {


        //클라이언트 요청에서 username, password 추출
        String username = obtainUsername(request);
        String password = obtainPassword(request);


        //스프링 시큐리티에서 username과 password를 검증하기 위해서는 token에 담아야 함
        //세번째 인자로는 원래 Role이 들어감.
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password, null);

        //token에 담은 검증을 위한 AuthenticationManager로 전달
        return authenticationManager.authenticate(token);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        //인증 성공시
        CustomUserDetails customUserDetails = (CustomUserDetails) authResult.getPrincipal();

        String username = customUserDetails.getUsername();

        // role 추출
        Collection<? extends GrantedAuthority> authorities = authResult.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();

        String role = auth.getAuthority();

        String token = jwtProvider.createJwt(username, role, 60*60*100L);

        response.addHeader("Authorization", "Bearer " + token);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        //인증 실패시
        response.setStatus(401);
    }
}

