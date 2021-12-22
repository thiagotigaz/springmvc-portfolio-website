package br.com.supercloud.cms.controller;

import br.com.supercloud.cms.model.ExperienceTypeEnum;
import br.com.supercloud.cms.model.Portfolio;
import br.com.supercloud.cms.model.pojo.Mail;
import br.com.supercloud.cms.repository.ExperienceRepository;
import br.com.supercloud.cms.repository.PortfolioRepository;
import br.com.supercloud.cms.service.SCFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class PublicController {

	private final PortfolioRepository portfolioRepo;
	private final ExperienceRepository experienceRepo;
	private final SCFileService fileService;
	private final JavaMailSender javaMailSender;

	@Value("${mail.from}")
	private String FROM;

	public PublicController(PortfolioRepository portfolioRepo, ExperienceRepository experienceRepo, SCFileService fileService, JavaMailSender javaMailSender) {
		this.portfolioRepo = portfolioRepo;
		this.experienceRepo = experienceRepo;
		this.fileService = fileService;
		this.javaMailSender = javaMailSender;
	}

	@RequestMapping("/")
	public String index(Model model) {
		List<Portfolio> result = portfolioRepo.findAll();
		int size = result.size();
		if (size != 0) {
			int numRows = (size % 3 == 0 && size > 3) ? (size / 3) : (size / 3) + 1;
			List<List<Portfolio>> portfolios = new ArrayList<>();
			for (int i = 0; i < numRows; i++) {
				List<Portfolio> row = new ArrayList<>();
				int numColumns = 3;

				// last row
				if (i == numRows - 1) {
					numColumns = size % 3 == 0 ? 3 : size % 3;
				}

				for (int j = 0; j < numColumns; j++) {
					row.add(result.get(i == 0 ? j : (i * 3) + j));
				}
				portfolios.add(row);
			}

			model.addAttribute("allPortfolios", portfolios);
		}
		model.addAttribute("allAcademic", experienceRepo.findAllByExperienceTypeEnumOrderByStartDateDesc(ExperienceTypeEnum.ACADEMIC));
		model.addAttribute("allProfessional", experienceRepo.findAllByExperienceTypeEnumOrderByStartDateDesc(ExperienceTypeEnum.PROFESSIONAL));

		return "index";
	}

	@Transactional
	@RequestMapping("/portfolio")
	public String portfolios(Model model) {
		model.addAttribute("allPortfolios", portfolioRepo.findAll());

		return "portfolio";
	}

	@RequestMapping("/portfolio/{portfolioId}")
	public String portfolioDetail(@PathVariable("portfolioId") Integer portfolioId, Model model) {
		model.addAttribute("portfolio", portfolioRepo.findById(portfolioId).orElse(new Portfolio()));
		model.addAttribute("allPortfolios", portfolioRepo.findAll());
		return "portfolio";
	}

	@RequestMapping(value = "/file/{fileId}", method = RequestMethod.GET)
	public void getFile(@PathVariable("fileId") Integer fileId, @Param("thumb") boolean thumb, HttpServletResponse response) {
		try {
			fileService.getPortfolioFile(fileId, thumb, response);
		} catch (IOException e) {
			// TODO handle excetion
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/mail/send", method = RequestMethod.POST)
	public String sendEmail(@Valid Mail email) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();

		mailMessage.setTo(FROM);
		mailMessage.setReplyTo(email.getEmail());
		mailMessage.setFrom(FROM);
		mailMessage.setSubject("Super Cloud Website");
		mailMessage.setText(email.getMessage());

		javaMailSender.send(mailMessage);

		return "index";
	}
}
