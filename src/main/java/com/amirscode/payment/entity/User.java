package com.amirscode.payment.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false,unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String phoneNumber;

    private boolean accountNonExpired = true;

    private boolean accountNonLocked = true;

    private boolean credentialsNonExpired = true;

    private boolean enabled = true;

//    @ManyToMany(fetch = FetchType.EAGER)
//    List<Permission> permissions;

    @ManyToOne
    private Role role;

    public User(String manager, String root123, String manager1, Role byName) {

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //todo role and permissions
        List<Role> list = new ArrayList<>();
        list.add(this.role);
        return list;
    }
}
