package br.com.supercloud.cms.util;

import br.com.supercloud.cms.model.User;
import br.com.supercloud.cms.repository.UserRepository;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class JPAUserDetailsService implements UserDetailsService {

	private final UserRepository userRepo;

	public JPAUserDetailsService(UserRepository userRepo) {
		this.userRepo = userRepo;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByUsernameIgnoreCase(username);
		if (user != null) {
			String roles[] = new String[user.getRoles().size()];
			for (int i = 0; i < user.getRoles().size(); i++) {
				roles[i] = user.getRoles().get(i).getName();
			}
			user.setAuthorities(AuthorityUtils.createAuthorityList(roles));
		}
		return user;
	}

}
