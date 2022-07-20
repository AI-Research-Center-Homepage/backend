package byuntil.backend.admin.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
//configuration어노테이션이 있으면 해당 클래스 안에 bean을 정의할 수 있다
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http)throws Exception{
        //아래의 antMatchers에는 resources/templates하위가 와야하는건가 -> 아님
        http.authorizeRequests().antMatchers("/h2-console/**").permitAll()
                .and().csrf().ignoringAntMatchers("/h2-console/**")
                .and().headers().frameOptions().sameOrigin();

        http
                .authorizeRequests().anyRequest()
                //.antMatchers("/sample/member").hasRole("ADMIN")
                //.antMatchers("/sample/admin").hasRole("ADMIN")
                //.antMatchers("/sample/all")
                .permitAll();
        http.formLogin();
        http.csrf().disable();
        // /logout으로 들어가면 logout됨 어떤 버튼을 누르면 해당 url로 redirect되도록 설계하면 될듯
        http.logout();
        http.httpBasic();




        //쿠키(서버가 사용자의 웹브라우저에 저장하는 데아터) 의 만료시간 설정
        http.rememberMe().tokenValiditySeconds(60*60*7).userDetailsService(userDetailsService());//7days

    }

}
