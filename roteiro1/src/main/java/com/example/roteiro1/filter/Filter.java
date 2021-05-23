package com.example.roteiro1.filter;

import com.example.roteiro1.service.AuthService;
import io.jsonwebtoken.Jwts;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Filter extends GenericFilterBean {
    public final static int TOKEN_INDEX = 7;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String header = getHeader(servletRequest, servletResponse);
        String token = header.substring(TOKEN_INDEX);

        try {
            Jwts.parser().setSigningKey(AuthService.SECRET_KEY_TOKEN).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            ((HttpServletResponse) servletResponse).sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    private String getHeader(ServletRequest servletRequest, ServletResponse servletResponse) throws IOException {
        String header = ((HttpServletRequest) servletRequest).getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            ((HttpServletResponse) servletResponse).sendError(HttpServletResponse.SC_UNAUTHORIZED,
                    "Token inexistente ou mal formatado!");
        }

        return header;
    }
}
