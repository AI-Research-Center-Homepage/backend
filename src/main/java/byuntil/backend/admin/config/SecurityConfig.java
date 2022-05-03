package byuntil.backend.admin.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //USER라는 권한 부여
        //user1이라는 id, 인코딩된 pw를 password에 인자로 넘기기
        auth.inMemoryAuthentication().withUser("user1")
                .password("$2a$10$qjTbkhjVLWuZEwJuBHox1.iKBR5Sf6xG.NkGQbV5cffc6ecNxNuUi")
                .roles("USER");
    }
    @Override
    protected void configure(HttpSecurity http)throws Exception{
        //아래의 antMatchers에는 resources/templates하위가 와야하는건가
        http.authorizeRequests()
                .antMatchers("/sample/all").permitAll()
                .antMatchers("/sample/member").hasRole("USER");
        // /sample/all 페이지는
        // permitAll : 모든 사용자에게 허락
        // /sample/member페이지는
        // USER권한을 가진 사용자에게만 허락되는 페이지이다
        http.formLogin();//인가/인증에 문제시 로그인 화면을 보여준다
        http.csrf().disable();//csrf 공격을 막기 위한 csrf토큰 안보이게 처리
        http.logout();
    }
}
