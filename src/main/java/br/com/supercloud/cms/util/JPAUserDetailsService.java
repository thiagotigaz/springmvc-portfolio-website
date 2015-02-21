package br.com.supercloud.cms.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import br.com.supercloud.cms.model.User;
import br.com.supercloud.cms.repository.UserRepository;

@Component
public class JPAUserDetailsService implements UserDetailsService{

	@Autowired
	private UserRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		User user = userRepo.findByUsernameIgnoreCase(username);
		if(user!=null){
			String roles[] = new String[user.getRoles().size()];
			for(int i = 0 ; i < user.getRoles().size();i++){
				roles[i] = user.getRoles().get(i).getName();
			}
			
			user.setAuthorities(AuthorityUtils.createAuthorityList(roles));
		}
		return user;
	}

}
