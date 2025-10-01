package com.mottu.mottuguard.config;
import com.mottu.mottuguard.models.User;
import com.mottu.mottuguard.repository.UserRepo;
import org.springframework.context.annotation.*;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean PasswordEncoder passwordEncoder(){ return new BCryptPasswordEncoder(); }

    @Bean UserDetailsService uds(UserRepo users){
        return username -> users.findByEmail(username)
                .map(SecUser::new)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
    }

    static class SecUser implements UserDetails {
        private final User u;
        SecUser(User u){this.u=u;}
        @Override public java.util.Collection<? extends org.springframework.security.core.GrantedAuthority> getAuthorities(){
            return u.getRoles().stream().map(r -> new org.springframework.security.core.authority.SimpleGrantedAuthority("ROLE_"+r.getName())).toList(); }
        @Override public String getPassword(){return u.getPassword();}
        @Override public String getUsername(){return u.getEmail();}
        @Override public boolean isAccountNonExpired(){return true;}
        @Override public boolean isAccountNonLocked(){return true;}
        @Override public boolean isCredentialsNonExpired(){return true;}
        @Override public boolean isEnabled(){return true;}
    }

    @Bean SecurityFilterChain http(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login","/register","/css/**","/error").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/motos/**").hasAnyRole("SUPERVISOR","ADMIN")
                        .anyRequest().authenticated())
                .formLogin(form -> form.loginPage("/login").defaultSuccessUrl("/", true).permitAll())
                .logout(l -> l.logoutUrl("/logout").logoutSuccessUrl("/login?out").permitAll())
                .csrf(Customizer.withDefaults());
        return http.build();
    }
}
