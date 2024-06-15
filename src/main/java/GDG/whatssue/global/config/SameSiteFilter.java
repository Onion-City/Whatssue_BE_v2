package GDG.whatssue.global.config;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;

public class SameSiteFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        filterChain.doFilter(request, response);

        // SameSite=None; Secure 설정
        Collection<String> headers = response.getHeaders("Set-Cookie");
        if(headers != null){
            for (String header : headers) {
                if(header.contains("JSESSIONID") || header.contains("SESSION")){
                    response.setHeader("Set-Cookie", String.format("%s; %s", header, "SameSite=None; Secure"));
                }
            }
        }
    }
}
