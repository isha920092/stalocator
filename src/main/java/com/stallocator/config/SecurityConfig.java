package com.stallocator.config;

//import static org.springframework.security.config.Customizer.withDefaults;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import com.stallocator.entities.Role;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @SuppressWarnings("deprecation")
//	@Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests(requests -> requests
//                        .antMatchers("/admin/**").hasRole(Role.ADMIN.toString())
//                        .antMatchers("/owner/**").hasRole(Role.OWNER.toString())
//                        .antMatchers("/user/**").hasRole(Role.CUSTOMER.toString())
//                        .anyRequest().authenticated())
//                .formLogin()
//                .loginPage("/login")
//                .permitAll()
//                .logout(logout -> logout
//                        .permitAll());
//    }
//
//    // Add UserDetailsService bean for user authentication if needed
//}
//
