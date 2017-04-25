package br.com.supercloud.cms.repository;

import br.com.supercloud.cms.model.Experience;
import br.com.supercloud.cms.model.ExperienceTypeEnum;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExperienceRepository extends PagingAndSortingRepository<Experience, Integer> {

	List<Experience> findAllByExperienceTypeEnumOrderByStartDateDesc(ExperienceTypeEnum experienceTypeEnum);
	
}
