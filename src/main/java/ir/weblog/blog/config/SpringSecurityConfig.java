package ir.weblog.blog.config;

import ir.weblog.blog.modules.jwt.filter.JwtRequestFilter;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JwtRequestFilter JwtRequestFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource)
                .passwordEncoder(new BCryptPasswordEncoder())
                .usersByUsernameQuery("select email,password,enabled from users_tbl where email = ?")
                .authoritiesByUsernameQuery("select email,roles from authorities where email = ?");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests().antMatchers("/", "/hello", "/auth", "/login", "/posts/search", "/index", "/css/**", "/js/**", "/img/**")
                .permitAll()
                /*.and().authorizeRequests()
                .antMatchers("/users/**", "/categories/**")
                //    .hasRole("ADMIN")
                .hasAuthority("ADMIN")
                .antMatchers("/posts/**")
                .hasAnyAuthority("ADMIN", "USER")*/
                .anyRequest().authenticated()
                //    .anyRequest().denyAll()
                .and().formLogin()
                .loginPage("/login")
                .usernameParameter("email")
                .and().logout().logoutUrl("/logout").deleteCookies("JWT").permitAll()
                .and().exceptionHandling().accessDeniedPage("/403")
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().addFilterBefore(JwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
