package br.com.supercloud.cms.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.supercloud.cms.model.Portfolio;

@Repository
public interface PortfolioRepository extends PagingAndSortingRepository<Portfolio,Integer>{
	
	public List<Portfolio> findAll();

	@Modifying
	@Transactional
	@Query("UPDATE Portfolio p SET p.coverImage.id = :fileId WHERE p.id = :portfolioId")
	public void setCoverImage(@Param("portfolioId") Integer portfolioId, @Param("fileId") Integer fileId);
}
