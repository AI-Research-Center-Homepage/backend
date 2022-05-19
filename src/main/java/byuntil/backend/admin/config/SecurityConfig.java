package byuntil.backend.admin.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
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


    @Override
    protected void configure(HttpSecurity http)throws Exception{
        //아래의 antMatchers에는 resources/templates하위가 와야하는건가 -> 아님

        http
                .authorizeRequests()
                .antMatchers("/sample/member").hasRole("ADMIN")
                .antMatchers("/sample/all").permitAll();
        http.formLogin();
        http.csrf().disable();
        // /logout으로 들어가면 logout됨 어떤 버튼을 누르면 해당 url로 redirect되도록 설계하면 될듯
        http.logout();
        http.httpBasic();

        http.authorizeRequests().antMatchers("/h2-console/**").permitAll()
                .and().csrf().ignoringAntMatchers("/h2-console/**")
                .and().headers().frameOptions().sameOrigin();
    }
}
