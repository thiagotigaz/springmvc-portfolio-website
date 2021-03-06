package br.com.supercloud.cms.repository;

import br.com.supercloud.cms.model.Tag;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends PagingAndSortingRepository<Tag, Integer> {

	@Query("Select t.name from Tag t")
	List<String> findAllTagNames();

	List<Tag> findAllByNameAllIgnoreCaseIn(String[] tagsName);

	Tag findByName(@Param("name") String tagName);
}
