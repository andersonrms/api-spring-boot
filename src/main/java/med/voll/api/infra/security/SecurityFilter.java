package med.voll.api.infra.security;

import com.auth0.jwt.algorithms.Algorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import med.voll.api.domain.user.UserRepository;
import med.voll.api.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository repository;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var tokenJWT = getTokenOnHeader(request); // pega o token

        if(tokenJWT != null) {
            var loggedUser = tokenService.getSubject(tokenJWT); // decodifica o token e pega user
            var user = repository.findByLogin(loggedUser); // verifica se existe o usuario na base
            var authenticate = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities()); // dto que representa o user

            SecurityContextHolder.getContext().setAuthentication(authenticate); //força a autenticação. somente nesta linha o spring sabe que o user esta logado
        }

        filterChain.doFilter(request, response);
    }

    private String getTokenOnHeader(HttpServletRequest request){
        var authorizationHeader = request.getHeader("Authorization");

        if(authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ", "");
        }
        return null;
    }
}
