package byuntil.backend.admin.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class ApiCheckFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("ApiCheckFilter........................................");
        log.info("ApiCheckFilter........................................");
        log.info("ApiCheckFilter........................................");

        filterChain.doFilter(request, response);
        //config/SecurityConfig에 ApiCheckFilter를 Bean으로 등록하였다
        //그 다음, permitAll인 url에 접근하면 위의 log가 뜬다
    }
}
