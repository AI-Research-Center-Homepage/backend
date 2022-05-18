package byuntil.backend.admin.config;

import byuntil.backend.admin.filter.ApiCheckFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
//configuration어노테이션이 있으면 해당 클래스 안에 bean을 정의할 수 있다
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ApiCheckFilter apiCheckFilter(){
        return new ApiCheckFilter();
    }
    @Override
    protected void configure(HttpSecurity http)throws Exception{
        //아래의 antMatchers에는 resources/templates하위가 와야하는건가 -> 아님
        http.authorizeRequests()
                .antMatchers("/sample/all").permitAll()
                .antMatchers("/sample/member").hasRole("ADMIN");
        http.formLogin();//인가/인증에 문제시 로그인 화면을 보여준다
        http.csrf().disable();//csrf 공격을 막기 위한 csrf토큰 안보이게 처리
        http.logout();
    }
}
