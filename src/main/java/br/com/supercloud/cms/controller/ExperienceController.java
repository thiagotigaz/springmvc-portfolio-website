package br.com.supercloud.cms.controller;

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

import br.com.supercloud.cms.model.Experience;
import br.com.supercloud.cms.repository.ExperienceRepository;
import br.com.supercloud.cms.util.Mappings;

@Controller
public class ExperienceController {

	@Autowired
	private ExperienceRepository experienceRepo;

	@ModelAttribute("allExperiences")
	public Iterable<Experience> allTagsName() {
		return experienceRepo.findAll();
	}

	@RequestMapping(value = "/admin/experience", method = RequestMethod.GET)
	public String experiencesAdmin(Experience experience, Model model) {
		return "admin/experience";
	}

	@Transactional
	@RequestMapping(value = "/admin/experience", method = RequestMethod.POST)
	public String saveExperience(@Valid Experience experience, BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {
			return "admin/experience";
		}

		experienceRepo.save(experience);

		model.asMap().clear();
		model.addAttribute("mensagem",
				experience.getId() == null ? "Experience added successfully!" : "Experience updated successfully!");

		return "redirect:/admin/experience";
	}

	@RequestMapping(value = "/admin/experience/{id}")
	public String singleExperience(@PathVariable(value = Mappings.ID_PARAMETER) Integer id, Model model) {
		Experience experience = experienceRepo.findOne(id);

		if (experience == null) {
			experience = new Experience();
		}

		model.addAttribute("experience", experience);

		return "/admin/experience";
	}

}
