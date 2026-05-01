package cgb.transfer.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import cgb.transfer.security.service.JwtService;
import cgb.transfer.security.service.MyUserDetailsService;

import java.io.IOException;

// Exemple de JwtAuthenticationFilter non géré comme un @Component (autre possibilité)
// mais qui impose dans ce cas d'etre instancié et configuré par SecurityConfig
// et être dans ce cas géré comme un @Bean (voir security config)
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private JwtService jwtService;
    private MyUserDetailsService userDetailsService;

    // Pour régler les problèmes de circularité, pas d'injection, on construit à la main dans
    // SecurityConfig
    public void setJwtService(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    public void setUserDetailsService(MyUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    /*  OU par
        @Autowired
        private final JwtService jwtService;
        @Autowired
        private final MyUserDetailsService userDetailsService;
        si on ne veut pas le gérer comme un bean
        mais dans ce cas ne doit pas être ajouté explicitement dans le security config
    */

    // Pour ajouter les éléments d'authentification de l'utilisateur au contexte d'authentification
    // puis fait suivre la chaine
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7);
            username = jwtService.extractUsername(jwt);
            System.out.print("Utilisateur : " + username + "  - jeton : " + jwt);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // UserDetails obtenu par le service : contient le username, les authorities, ..
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                // Pour info affiche la première autorité du user : ADMIN, USER ou COMPTABLE...
                System.out.print("userDetails : " + userDetails.getAuthorities().stream().findFirst().toString());

                // Vérifie la validité du token, username et expiration
                if (jwtService.isTokenValid(jwt, userDetails)) {
                    // Token avec détails
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    // Rajoute le authToken au contexte
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}