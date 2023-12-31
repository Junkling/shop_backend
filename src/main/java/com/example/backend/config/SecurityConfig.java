package com.example.backend.config;

import com.example.backend.filter.JwtAuthenticationFilter;
import com.example.backend.member.service.JwtServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@Slf4j
@RequiredArgsConstructor
public class SecurityConfig
//        extends WebSecurityConfigurerAdapter
{
    private final JwtServiceImpl jwtService;

    @Bean
    public BCryptPasswordEncoder encoder() {
        // DB 패스워드 암호화
        return new BCryptPasswordEncoder();
    }
//    @Override
//    public void configure(WebSecurity web) {
//        // resource 에 대해 Spring Security FilterChain 제외
//        web.ignoring().antMatchers("/resources/**");
//    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
////      super.configure(http); // 이 코드 삭제하면 기존 시큐리티가 가진 모든 기능 비활성화
//        log.info("로그인");
//        http.csrf().disable(); // csrf 토큰 비활성화 코드
//
//        http.authorizeRequests()
//                .antMatchers("/api/seller/**").hasAnyRole("seller")
//                .antMatchers().authenticated() // 이 주소로 시작되면 인증이 필요
//                .anyRequest().permitAll() // 그게 아닌 모든 주소는 인증 필요 없음
//                .and()
//                .formLogin()
//                .loginPage("/login") // 인증필요한 주소로 접속하면 이 주소로 이동시킴
//                .loginProcessingUrl("/api/account/loginProc") // 스프링 시큐리티가 로그인 자동 진행 POST방식으로 로그인 진행
//                .defaultSuccessUrl("/"); // 로그인이 정상적이면 "/" 로 이동
//    }
@Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
            .httpBasic().disable()
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .antMatchers().authenticated()
            .antMatchers("/api/seller/items").hasRole("seller")
//            .antMatchers().permitAll()
            .anyRequest().permitAll()
            .and()
            .addFilterBefore(new JwtAuthenticationFilter(jwtService), UsernamePasswordAuthenticationFilter.class);
    return http.build();
}
}
