package br.com.supercloud.cms.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import br.com.supercloud.cms.model.Experience;
import br.com.supercloud.cms.model.ExperienceTypeEnum;

@Repository
public interface ExperienceRepository extends PagingAndSortingRepository<Experience, Integer> {

	List<Experience> findAllByExperienceTypeEnum(ExperienceTypeEnum experienceTypeEnum);
	
}
