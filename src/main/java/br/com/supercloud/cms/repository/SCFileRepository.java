package br.com.supercloud.cms.repository;

import br.com.supercloud.cms.model.SCFile;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SCFileRepository extends PagingAndSortingRepository<SCFile, Integer> {

	List<SCFile> findAllByPortfolioId(@Param("id") Integer id);

}
