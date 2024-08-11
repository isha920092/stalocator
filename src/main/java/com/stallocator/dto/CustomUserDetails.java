//package com.stallocator.dto;
//
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import com.stallocator.entities.Customer;
//
//import java.util.Collection;
//import java.util.List;
//
//public class CustomUserDetails implements UserDetails {
//    
//    private Customer customer;
//
//    public CustomUserDetails(Customer customer) {
//        this.customer = customer;
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        // Map roles to authorities
//        return List.of(new SimpleGrantedAuthority("ROLE_" + customer.getRole()));
//    }
//
//    @Override
//    public String getPassword() {
//        return customer.getPassword();
//    }
//
//    @Override
//    public String getUsername() {
//        return customer.getEmail();
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true; // Add your logic here
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true; // Add your logic here
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true; // Add your logic here
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true; // Add your logic here
//    }
//}
