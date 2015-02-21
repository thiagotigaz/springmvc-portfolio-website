package br.com.supercloud.cms.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.supercloud.cms.model.SCFile;

@Repository
public interface SCFileRepository extends
		PagingAndSortingRepository<SCFile, Integer> {

	List<SCFile> findAllByPortfolioId(@Param("id") Integer id);

}
