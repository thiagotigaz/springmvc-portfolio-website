package br.com.supercloud.cms.controller;

import br.com.supercloud.cms.model.Portfolio;
import br.com.supercloud.cms.model.SCFile;
import br.com.supercloud.cms.model.Tag;
import br.com.supercloud.cms.model.pojo.UploadResponse;
import br.com.supercloud.cms.repository.PortfolioRepository;
import br.com.supercloud.cms.repository.TagRepository;
import br.com.supercloud.cms.service.PortfolioService;
import br.com.supercloud.cms.service.SCFileService;
import br.com.supercloud.cms.util.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Controller
public class PortfolioController {

	@Autowired
	private PortfolioRepository portfolioRepo;

	@Autowired
	private TagRepository tagRepo;

	@Autowired
	private SCFileService fileService;

	@Autowired
	private PortfolioService portfolioService;

	@Transactional
	@ModelAttribute("allTags")
	public List<String> allTagsName() {
		return tagRepo.findAllTagNames();
	}

	@Transactional
	@RequestMapping(value = "/admin/portfolio", method = RequestMethod.GET)
	public String portfoliosAdmin(Model model) {
		model.addAttribute("portfolio", new Portfolio());
		model.addAttribute("allPortfolios", portfolioRepo.findAll());
		
		return "admin/portfolio";
	}

	@RequestMapping(value = "/admin/portfolio", method = RequestMethod.POST)
	public String savePortfolio(@RequestParam(value = "tagsStringArray", required = false) String[] tags,
			@Valid Portfolio portfolio, BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {
			return "admin/portfolio";
		}

		portfolioService.saveOrUpdate(portfolio, tags);

		model.asMap().clear();
		model.addAttribute("mensagem",
				portfolio.getId() == null ? "Portfolio added successfully!" : "Portfolio updated successfully!");

		return "redirect:/admin/portfolio";
	}

	
	@RequestMapping(value = "/admin/portfolio/{id}")
	public String singlePortfolio(@PathVariable(value = Mappings.ID_PARAMETER) Integer id, Model model) {
		model.addAttribute("portfolio", portfolioRepo.findById(id).orElse(new Portfolio()));
		return "/admin/portfolio";
	}

	@RequestMapping(value = "/admin/portfolio/{id}/upload", method = RequestMethod.POST)
	public @ResponseBody UploadResponse portfolioFileUpload(@PathVariable("id") Integer idPortfolio, Model model,
			@RequestParam("file") MultipartFile portfolioFile, HttpServletResponse response) {

		SCFile savedFile;
		try {
			savedFile = fileService.uploadPortfolioFile(idPortfolio, portfolioFile);
		} catch (IOException e) {
			return null;
		}

		return new UploadResponse(idPortfolio, savedFile.getId());
	}

	@RequestMapping(value = "/admin/portfolio/image/{portfolioId}/{fileId}", method = RequestMethod.DELETE)
	public @ResponseBody String portfolioDeleteImage(@PathVariable("portfolioId") Integer portfolioId,
			@PathVariable("fileId") Integer fileId, HttpServletResponse response) {

		fileService.deletePortfolioFile(portfolioId, fileId);

		return "{message: File removed sucessfully}";
	}

	@RequestMapping(value = "/admin/portfolio/image/setcover/{portfolioId}/{fileId}", method = RequestMethod.PUT)
	public @ResponseBody String portfolioSetComverImage(@PathVariable("portfolioId") Integer portfolioId,
			@PathVariable("fileId") Integer fileId, HttpServletResponse response) {

		portfolioRepo.setCoverImage(portfolioId, fileId);

		return "{message: File removed sucessfully}";
	}

	@RequestMapping(value = "/admin/portfolio/tag/check/{tagName}", method = RequestMethod.POST)
	public @ResponseBody String checkTag(@PathVariable("tagName") String tagName) {
		Tag tag = tagRepo.findByName(tagName);

		if (tag == null) {
			tag = new Tag(StringUtils.capitalizeWords(tagName.toLowerCase()));

			tagRepo.save(tag);

			return "{message:'Tag added sucesfully'}";
		}

		return "{}";
	}

}