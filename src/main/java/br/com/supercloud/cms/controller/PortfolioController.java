package br.com.supercloud.cms.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import javax.validation.Valid;

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

import br.com.supercloud.cms.model.Portfolio;
import br.com.supercloud.cms.model.SCFile;
import br.com.supercloud.cms.model.Tag;
import br.com.supercloud.cms.model.UploadResponse;
import br.com.supercloud.cms.repository.PortfolioRepository;
import br.com.supercloud.cms.repository.SCFileRepository;
import br.com.supercloud.cms.repository.TagRepository;
import br.com.supercloud.cms.service.FileUtil;
import br.com.supercloud.cms.util.Mappings;

@Controller
public class PortfolioController {

	@Autowired
	private PortfolioRepository portfolioRepo;
	@Autowired
	private TagRepository tagRepo;
	@Autowired
	private SCFileRepository fileRepo;
	@Autowired
	private FileUtil fileManager;

	@ModelAttribute("allTags")
	public List<String> allTagsName() {
		return tagRepo.findAllTagNames();
	}

	@RequestMapping(value = "/admin/portfolio", method = RequestMethod.GET)
	public String portfoliosAdmin(Portfolio portfolio, Model model) {
		// model.addAttribute("allTags", tagRepo.findAllTagNames());
		model.addAttribute("allPortfolios", portfolioRepo.findAll());
		// model.addAttribute("allTags", tagRepo.findAll());
		return "admin/portfolio";
	}

	@Transactional
	@RequestMapping(value = "/admin/portfolio", method = RequestMethod.POST, params = { "save" })
	public String savePortfolio(
			@RequestParam(value = "tagsStringArray", required = false) String[] tags,
			@Valid Portfolio portfolio, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "admin/portfolio";
		}
		if (tags != null)
			portfolio.setTags(tagRepo.findAllByNameAllIgnoreCaseIn(tags));
		portfolioRepo.save(portfolio);
		model.asMap().clear();
		model.addAttribute("mensagem", "Portfolio adicionado com sucesso!");
		return "redirect:/admin/portfolio";
	}

	@Transactional
	@RequestMapping(value = "/admin/portfolio", method = RequestMethod.PUT, params = { "edit" })
	public String editPortfolio(
			@RequestParam(value = "tagsStringArray", required = false) String[] tags,
			@Valid Portfolio portfolio, BindingResult bindingResult, Model model) {
		System.out.println(portfolio.getCustomer());

		if (bindingResult.hasErrors()) {
			return "admin/portfolio";
		}

		if (tags != null) {
			for (int i = 0; i < tags.length; i++)
				tags[i] = StringUtils.capitalizeWords(tags[i].toLowerCase());
			portfolio.setTags(tagRepo.findAllByNameAllIgnoreCaseIn(tags));
		}
		if (portfolio.getImages() == null)
			portfolio
					.setImages(fileRepo.findAllByPortfolioId(portfolio.getId()));
		portfolioRepo.save(portfolio);
		return "redirect:/admin/portfolio";
	}

	@RequestMapping(value = "/admin/portfolio/{id}")
	public String singlePortfolio(
			@PathVariable(value = Mappings.ID_PARAMETER) Integer id, Model model) {
		Portfolio portfolio = portfolioRepo.findOne(id);
		if (portfolio == null)
			portfolio = new Portfolio();
		model.addAttribute("portfolio", portfolio);
		return "/admin/portfolio";
	}

	@RequestMapping(value = "/admin/portfolio/{id}/upload", method = RequestMethod.POST)
	public @ResponseBody UploadResponse portfolioFileUpload(
			@PathVariable("id") Integer id, Model model,
			@RequestParam("files") MultipartFile[] portfolioFiles,
			HttpServletResponse response) {

		List<Integer> files = new ArrayList<Integer>();

		for (MultipartFile file : portfolioFiles) {
			try {
				Portfolio portfolio = portfolioRepo.findOne(id);
				List<SCFile> images = portfolio.getImages();
				if (images == null) {
					images = new ArrayList<SCFile>();
					portfolio.setImages(images);
				}
				SCFile savedFile = fileManager.saveFile(file, portfolio);
				images.add(savedFile);
				portfolio.setCoverImage(savedFile);
				portfolioRepo.save(portfolio);
				files.add(savedFile.getId());
				model.addAttribute("portfolio", portfolio);
				return new UploadResponse(portfolio.getId(), savedFile.getId());
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}

	@RequestMapping(value = "/admin/portfolio/image/{portfolioId}/{fileId}", method = RequestMethod.DELETE)
	public @ResponseBody String portfolioDeleteImage(
			@PathVariable("portfolioId") Integer portfolioId,
			@PathVariable("fileId") Integer fileId, HttpServletResponse response) {

		fileManager.deleteFile(portfolioId, fileId);

		return "{message: File removed sucessfully}";
	}

	@RequestMapping(value = "/admin/portfolio/image/setcover/{portfolioId}/{fileId}", method = RequestMethod.PUT)
	public @ResponseBody String portfolioSetComverImage(
			@PathVariable("portfolioId") Integer portfolioId,
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
