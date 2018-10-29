package com.training360.yr8ckwebshopapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import javax.sql.DataSource;


@SpringBootApplication
@EnableWebSecurity
@Configuration
public class Yr8CkWebshopAppApplication extends
		WebSecurityConfigurerAdapter {

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	public static void main(String[] args) {
		SpringApplication.run(Yr8CkWebshopAppApplication.class, args);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http

				.csrf().disable()
				.authorizeRequests()
				.antMatchers("/basket").authenticated()
				.antMatchers(HttpMethod.POST, "/products").hasRole("ADMIN")
				.antMatchers(HttpMethod.GET, "/users").hasRole("ADMIN")
				.antMatchers("/", "/js/**", "/css/**", "/api/**").permitAll()
				.antMatchers("/basket.html", "/myorders.html").hasAnyRole("ADMIN", "USER")
				.antMatchers( "/adminproducts.html", "/orders.html", "/users.html").hasRole("ADMIN")
				.and()
				.formLogin()
//				.loginPage("/login.html")
				.defaultSuccessUrl("/products.html")
				.and()
				.logout();
	}

	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth, DataSource dataSource, PasswordEncoder passwordEncoder) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(passwordEncoder)
				.usersByUsernameQuery("select user_name as principal, password as credentials, true from user where user_name = ?")
				.authoritiesByUsernameQuery("select user_name, role from user where user_name = ?")
				.rolePrefix("ROLE_");
	}

}
