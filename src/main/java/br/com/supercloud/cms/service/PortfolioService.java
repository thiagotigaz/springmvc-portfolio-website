package br.com.supercloud.cms.service;

import br.com.supercloud.cms.model.Portfolio;
import br.com.supercloud.cms.repository.PortfolioRepository;
import br.com.supercloud.cms.repository.SCFileRepository;
import br.com.supercloud.cms.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import javax.transaction.Transactional;

@Service
public class PortfolioService {

	@Autowired
	private PortfolioRepository portfolioRepo;

	@Autowired
	private TagRepository tagRepo;

	@Autowired
	private SCFileRepository fileRepo;

	@Transactional
	public void saveOrUpdate(Portfolio portfolio, String[] tags) {
		if (tags != null) {
			for (int i = 0; i < tags.length; i++) {
				tags[i] = StringUtils.capitalizeWords(tags[i].toLowerCase());
			}

			portfolio.setTags(tagRepo.findAllByNameAllIgnoreCaseIn(tags));
		}

		if (portfolio.getCoverImage() != null) {
			portfolio.setCoverImage(fileRepo.findById(portfolio.getCoverImage().getId()).orElse(null));
		}

		if (portfolio.getImages() == null) {
			portfolio.setImages(fileRepo.findAllByPortfolioId(portfolio.getId()));
		}

		portfolioRepo.save(portfolio);
	}

}
