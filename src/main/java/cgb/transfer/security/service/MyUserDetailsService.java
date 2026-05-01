package cgb.transfer.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import cgb.transfer.entity.UserCGB;
import cgb.transfer.entity.Role;
import cgb.transfer.repository.UserCGBRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserCGBRepository userCGBRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public MyUserDetailsService(UserCGBRepository userCGBRepository, PasswordEncoder passwordEncoder) {
        this.userCGBRepository = userCGBRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserCGB user = userCGBRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return getUserDetailsFromUser(user);
    }

    // Attention : conversion du UserCGB en UserDetails Spring.
    // Ou possibilité de laisser obtention par le UserDetails qui implémente UserDetails
    private UserDetails getUserDetailsFromUser(UserCGB user) {
        // Construction des autorités à partir du role du UserCGB
        List<GrantedAuthority> authorities = new ArrayList<>();
        Role role = user.getRole();
        // Permettre aux annotations hasRole de parser le role — exemple : ROLE_COMPTABLE extrait de COMPTABLE
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role.name()));
        // Construction d'un Spring UserDetails
        // C'est le User associé au UserDetails Spring, pas le nôtre. D'où le chemin complet pour distinction
        org.springframework.security.core.userdetails.User springUser =
                new org.springframework.security.core.userdetails.User(
                        user.getUsername(),
                        user.getPassword(),
                        authorities
                );
        return springUser;
    }

    public UserCGB registerUser(UserCGB user) {
        // Encoder le mot de passe
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        // Sauvegarder l'utilisateur dans la base de données
        return userCGBRepository.save(user);
    }

    public Long count() {
        return userCGBRepository.count();
    }
}