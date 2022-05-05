package chastaing.controlleur;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import chastaing.model.MyUserDetails;
import chastaing.model.User;
import chastaing.todoux.UserRepository;

@Service
@EntityScan("chastaing.model")
public class MyUserDetailsService implements UserDetailsService{
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = userRepository.findByUserName(username);
		
		user.orElseThrow(() -> new UsernameNotFoundException("Not found :" + username));
		
		return user.map(MyUserDetails::new).get();
	}

	@Bean
	public BCryptPasswordEncoder getInstance() {
		return new BCryptPasswordEncoder();
	}

}
