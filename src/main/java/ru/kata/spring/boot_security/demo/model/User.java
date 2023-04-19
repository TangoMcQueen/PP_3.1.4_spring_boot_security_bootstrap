package ru.kata.spring.boot_security.demo.model;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.validator.CheckEmail;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name")
    @Size(min = 2, max = 15, message = "Name must be min 2 symbols")
    @NotBlank(message = "Name should not be empty")
    @Pattern(regexp = "^[A-Za-zА-Яа-яЁё]{2,15}$")
    private String firstName;
    @Column(name = "last_name")
    @Size(min = 2, max = 25, message = "Last Name must be min 2 symbols")
    @NotBlank(message = "Last Name should not be empty")
    @Pattern(regexp = "^[A-Za-zА-Яа-яЁё]{2,25}$")
    private String lastName;
    @Column(name = "age", nullable = false)
    @NotNull(message = "Age should not be empty")
    @Min(value = 18, message = "must be greater than 17")
    @Max(value = 100, message = "must be less than 101")
    private int age;
    @Column(name = "email", nullable = false, unique = true)
    @Size(min = 7, max = 40, message = "Email must be min 7 symbols")
    //@CheckEmail
    @Email
    private String email;
    @Column(name = "password", nullable = false)
    @NotBlank(message = "Password should not be empty")
    //@Size(min = 4, max = 50, message = "Password must be min 4 symbols")
    //@Pattern(regexp = "^[a-zA-Z0-9]+$")
    private String password;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles = new HashSet<>();

    public User() {
    }

    public User(String firstName, String lastName, int age, String email,  String password, Set<Role> roles) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String getUsername() {
        return email;
    }
    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Transactional
    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    // Методы интерфейса
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}