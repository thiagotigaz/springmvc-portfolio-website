package br.com.supercloud.cms.repository;

import br.com.supercloud.cms.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

	public User findByUsernameIgnoreCase(@Param(value = "username") String username);

}
