/*
 * 
 * Copyright 2014 Jules White
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package br.com.supercloud.cms.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.supercloud.cms.model.Portfolio;
import br.com.supercloud.cms.model.SCFile;
import br.com.supercloud.cms.repository.PortfolioRepository;
import br.com.supercloud.cms.repository.SCFileRepository;
import br.com.supercloud.cms.util.FileUtil;

@Service
public class SCFileService {

	@Autowired
	private SCFileRepository fileRepo;

	@Autowired
	private PortfolioRepository portfolioRepo;

	@Autowired
	private FileUtil fileUtil;

	public void getPortfolioFile(Integer fileId, boolean thumb, HttpServletResponse response) throws IOException {
		SCFile file = fileRepo.findOne(fileId);
		if (file != null) {
			response.setContentType(file.getContentType());

			fileUtil.copyFileData(file.getId() + (thumb ? "_thumb" : "") + file.getExtension(), response.getOutputStream());
		} else {
			response.sendError(404);
		}
	}

	@Transactional
	public SCFile uploadPortfolioFile(Integer idPortfolio, MultipartFile portfolioFile) throws IOException {
		Portfolio portfolio = portfolioRepo.findOne(idPortfolio);

		List<SCFile> images = portfolio.getImages();

		if (images == null) {
			images = new ArrayList<SCFile>();
			portfolio.setImages(images);
		}

		SCFile savedFile = saveFile(portfolioFile, portfolio);

		images.add(savedFile);
		portfolio.setCoverImage(savedFile);

		portfolioRepo.save(portfolio);

		return savedFile;
	}

	private SCFile saveFile(MultipartFile file, Portfolio portfolio) throws IOException {
		SCFile scFile = new SCFile();

		String filename = file.getOriginalFilename();
		String extension = filename.substring(filename.lastIndexOf(".")).toLowerCase();

		scFile.setPortfolio(portfolio);
		scFile.setExtension(extension);
		scFile.setContentType(file.getContentType());

		fileRepo.save(scFile);

		fileUtil.resizeAndSaveImgAndThumb(scFile.getId().toString(), scFile.getExtension(), file.getInputStream());

		return scFile;
	}

	@Transactional
	public void deletePortfolioFile(Integer portfolioId, Integer fileId) {
		Portfolio portfolio = portfolioRepo.findOne(portfolioId);

		if (portfolio != null && portfolio.getCoverImage() != null && portfolio.getCoverImage().getId() == fileId) {

			portfolio.setCoverImage(null);

			portfolioRepo.save(portfolio);
		}

		fileRepo.delete(fileId);
	}
}
