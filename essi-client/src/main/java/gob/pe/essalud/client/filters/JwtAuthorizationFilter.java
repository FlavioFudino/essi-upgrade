package gob.pe.essalud.client.filters;

import com.auth0.jwt.exceptions.TokenExpiredException;
import gob.pe.essalud.client.dto.UserSessionDto;
import gob.pe.essalud.client.service.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private static final String AUTHORIZATION = "Authorization";

    private final JwtService jwtService;

    public JwtAuthorizationFilter(AuthenticationManager authManager,
                                  JwtService jwtService) {
        super(authManager);
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        try {
            String authHeader = request.getHeader(AUTHORIZATION);
            if (jwtService.isBearer(authHeader)) {
                UsernamePasswordAuthenticationToken authentication = getUsernamePasswordAuthenticationToken(authHeader);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            filterChain.doFilter(request, response);
        } catch (TokenExpiredException ex) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
        }

    }

    private UsernamePasswordAuthenticationToken getUsernamePasswordAuthenticationToken(String authHeader) {
        List<GrantedAuthority> authorities = jwtService.roles(authHeader).stream()
                .map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        UserSessionDto user = new UserSessionDto(
                jwtService.tipoDocumento(authHeader),
                jwtService.numeroDocumento(authHeader));
        return new UsernamePasswordAuthenticationToken(user, null, authorities);
    }
}
