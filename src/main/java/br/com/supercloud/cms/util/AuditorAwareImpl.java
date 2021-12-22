package br.com.supercloud.cms.util;

import br.com.supercloud.cms.model.User;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<User> {

	@Override
	public Optional<User> getCurrentAuditor() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    if (authentication == null || !authentication.isAuthenticated()) {
	      return Optional.empty();
	    }
		User principal = (User) authentication.getPrincipal();
		return Optional.ofNullable(principal) ;
	}
}
