package ru.kata.spring.boot_security.demo.model;

import org.hibernate.annotations.Cascade;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.*;

@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "email", nullable = false, unique = true)
    @NotBlank(message = "Username should not be empty")
    @Size(min = 5, max = 50, message = "Поле должно содержать от 5 до 50 символов")
    @Email
    private String username;
    @Column(name = "first_name", nullable = false)
    @NotBlank(message = "First Name should not be empty")
    @Size(min = 2, max = 15, message = "Поле должно содержать от 2 до 15 символов")
    private String firstName;
    @Column(name = "last_name", nullable = false)
    @NotBlank(message = "Last name should not be empty")
    @Size(min = 2, max = 25, message = "Поле должно содержать от 2 до 25 символов")
    private String lastName;
    @Column(name = "age")
    @NotNull(message = "Age should not be empty")
    @Min(value = 18, message = "must be greater than 17")
    @Max(value = 100, message = "must be less than 101")
    private int age;
    @Column(name = "password", nullable = false)
    @NotBlank(message = "Password should not be empty")
    private String password;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private Set<Role> roleList;
    public User() {   }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username, String firstName, String lastName, int age,
                String password, Set<Role> roleList) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.password = password;
        this.roleList = roleList;
    }

    public User(Long id, String firstName, String lastName, int age, String username,
                String password, Set<Role> roleList) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.username = username;
        this.password = password;
        this.roleList = roleList;

    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", password='" + password + '\'' +
                ", roleList=" + roleList +
                '}';
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(Set<Role> roleList) {
        this.roleList = roleList;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return getRoleList();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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

    public String roleToString(){
        StringBuilder sb = new StringBuilder();
        for(Role role: roleList){
            sb.append(role.getNameRole()).append(" ");
        }
        return sb.toString();
    }
}


