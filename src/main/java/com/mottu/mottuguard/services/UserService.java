package com.mottu.mottuguard.services;

import com.mottu.mottuguard.models.*;
import com.mottu.mottuguard.repository.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service; import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service @Transactional
public class UserService {
    private final UserRepo users; private final RoleRepo roles; private final PasswordEncoder enc;
    public UserService(UserRepo u, RoleRepo r, PasswordEncoder e){this.users=u; this.roles=r; this.enc=e;}

    public User register(String name, String email, String rawPass, boolean admin){
        if(users.findByEmail(email).isPresent()) throw new IllegalArgumentException("Email já usado");
        User u=new User(); u.setName(name); u.setEmail(email); u.setPassword(enc.encode(rawPass));
        Role userRole = roles.findByName("USER").orElseGet(() -> roles.save(new Role("USER")));
        u.getRoles().add(userRole);
        if(admin){ Role r = roles.findByName("ADMIN").orElseGet(() -> roles.save(new Role("ADMIN"))); u.getRoles().add(r);}
        return users.save(u);
    }
}
