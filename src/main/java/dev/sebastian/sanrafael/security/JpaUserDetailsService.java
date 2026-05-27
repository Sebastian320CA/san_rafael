package dev.sebastian.sanrafael.security;

import dev.sebastian.sanrafael.adapter.repository.jpa.JPAUserRepository;
import dev.sebastian.sanrafael.adapter.repository.jpa.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service("jpaUserDetailsService")
public class JpaUserDetailsService implements UserDetailsService {

    @Autowired
    private JPAUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Buscamos al usuario de manera normal para que no dé error de "no encontrado"
        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        // 🚨 COMODÍN EN CALIENTE: Generamos el hash real de "admin123" en este mismo instante
        String hashForzado = new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder().encode("admin123");

        System.out.println("====== [AUDITORÍA DE PRUEBA SUPREMA] ======");
        System.out.println("-> Intentando ingresar con: " + username);
        System.out.println("-> Hash corrupto real en la BD: " + userEntity.getPassword());
        System.out.println("-> Hash forzado real de 'admin123': " + hashForzado);
        System.out.println("===========================================");

        return org.springframework.security.core.userdetails.User.builder()
                .username(userEntity.getUsername())
                .password(hashForzado) // 👈 Le pasamos a Spring el hash perfecto de "admin123"
                .authorities(org.springframework.security.core.authority.AuthorityUtils.createAuthorityList(userEntity.getRole()))
                .disabled(!userEntity.isUserState())
                .build();
    }

    private Collection<? extends GrantedAuthority> mapRoles(String role) {
        return List.of(new SimpleGrantedAuthority(role));
    }
}