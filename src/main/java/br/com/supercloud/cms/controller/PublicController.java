package br.com.supercloud.cms.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.supercloud.cms.model.Mail;
import br.com.supercloud.cms.model.Portfolio;
import br.com.supercloud.cms.repository.PortfolioRepository;
import br.com.supercloud.cms.service.FileUtil;

@Controller
public class PublicController {
	@Autowired
	private PortfolioRepository portfolioRepo;
	@Autowired
	private FileUtil fileService;
	@Autowired
	private JavaMailSender javaMailSender;
	@Value("${mail.from}")
	private String FROM;

	@RequestMapping("/")
	public String index(Model model) {
		List<Portfolio> result = portfolioRepo.findAll();
		int size = result.size();
		if (size != 0) {
			int numRows = (size % 3 == 0 && size > 3) ? (size / 3)
					: (size / 3) + 1;
			List<List<Portfolio>> portfolios = new ArrayList<List<Portfolio>>();
			for (int i = 0; i < numRows; i++) {
				List<Portfolio> row = new ArrayList<Portfolio>();
				int numColumns = i == numRows - 1 ? size % 3 : 3;
				for (int j = 0; j < numColumns; j++)
					row.add(result.get(i == 0 ? j : (i * 3) + j));
				portfolios.add(row);
			}
			model.addAttribute("allPortfolios", portfolios);
		}
		return "index";
	}

	@RequestMapping("/portfolio")
	public String portfolios(Model model) {
		model.addAttribute("allPortfolios", portfolioRepo.findAll());
		return "portfolio";
	}

	@RequestMapping("/portfolio/{portfolioId}")
	public String portfolioDetail(
			@PathVariable("portfolioId") Integer portfolioId, Model model) {
		model.addAttribute("portfolio", portfolioRepo.findOne(portfolioId));
		model.addAttribute("allPortfolios", portfolioRepo.findAll());
		return "portfolio";
	}

	@RequestMapping(value = "/file/{fileId}", method = RequestMethod.GET)
	public void getFile(@PathVariable("fileId") Integer fileId,
			HttpServletResponse response) {
		try {
			fileService.getFile(fileId, response);
		} catch (IOException e) {
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
