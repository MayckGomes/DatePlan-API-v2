package com.mayckgomes.dateplan_api.configurations;

import com.mayckgomes.dateplan_api.jwt.JwtService;
import com.mayckgomes.dateplan_api.domains.UserDomain;
import com.mayckgomes.dateplan_api.exception.custom.token.TokenExpiredException;
import com.mayckgomes.dateplan_api.exception.custom.token.TokenInBlackListException;
import com.mayckgomes.dateplan_api.services.RedisBlackListService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final RedisBlackListService redisBlackListService;

    public SecurityFilter(JwtService jwtService,
                          RedisBlackListService redisBlackListService) {
        this.jwtService = jwtService;
        this.redisBlackListService = redisBlackListService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            var token = request.getHeader("Authorization");

            if (token != null && token.startsWith("Bearer ")) {

                token = token.replace("Bearer ", "");

                redisBlackListService.verifyIfBlacklisted(jwtService.getTokenId(token));

                UserDomain user = jwtService.decodeAccessToken(token);

                var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            filterChain.doFilter(request, response);

        } catch (TokenExpiredException e){

            SecurityContextHolder.clearContext();

            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");
            response.getWriter().write("""
                    {\s
                        "status": 401,
                        "message": "Token is expired"
                     }\s
                   \s""");

            return;
        } catch (TokenInBlackListException e){

            SecurityContextHolder.clearContext();

            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");
            response.getWriter().write("""
                    {\s
                        "status": 401,
                        "message": "Token is blacklisted"
                     }\s
                   \s""");

            return;
        }

    }
}
