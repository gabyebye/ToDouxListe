package chastaing.controlleur;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@EntityScan("chastaing.model")
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	@Autowired
	MyUserDetailsService myUserDetailsService;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(myUserDetailsService);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/").hasRole("USER")
			.antMatchers("/registration").permitAll()
			.antMatchers("/addTask").hasRole("USER")
			.antMatchers("/error").permitAll()
			.antMatchers("/tache-fini").hasRole("USER")
			.and()
			.formLogin()
			.loginPage("/login").permitAll()
			.and()
			.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.logoutSuccessUrl("/login").deleteCookies("JSESSIONID")
			.invalidateHttpSession(true);
	}
	
	public BCryptPasswordEncoder getPasswordEncoder() {
		return myUserDetailsService.getInstance();
	}

}
