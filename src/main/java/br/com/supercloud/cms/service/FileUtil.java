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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import br.com.supercloud.cms.model.Portfolio;
import br.com.supercloud.cms.model.SCFile;
import br.com.supercloud.cms.repository.PortfolioRepository;
import br.com.supercloud.cms.repository.SCFileRepository;

/**
 * This class provides a simple implementation to store gift binary data on the
 * file system in a "gifts" folder. The class provides methods for saving gifts
 * and retrieving their binary data.
 * 
 * @author jules
 *
 */
@Component
public class FileUtil {

	@Autowired
	private SCFileRepository fileRepo;
	@Autowired
	private PortfolioRepository portfolioRepo;

	private Path targetDir_;

	@Autowired
	public FileUtil(@Value("${config.files.path}") String path)
			throws IOException {
		targetDir_ = Paths.get(path);
		if (!Files.exists(targetDir_)) {
			Files.createDirectories(targetDir_);
		}
	}

	// Private helper method for resolving gift file paths
	private Path getFilePath(String fileName) {
		assert (fileName != null);

		return targetDir_.resolve(fileName);
	}

	/**
	 * This method returns true if the specified fileName has binary data stored
	 * on the file system.
	 * 
	 * @param v
	 * @return
	 */
	public boolean hasFile(String fileName) {
		Path source = getFilePath(fileName);
		return Files.exists(source);
	}

	/**
	 * This method copies the binary data for the given fileName to the provided
	 * output stream. The caller is responsible for ensuring that the specified
	 * Gift has binary data associated with it. If not, this method will throw a
	 * FileNotFoundException.
	 * 
	 * @param v
	 * @param out
	 * @throws IOException
	 */
	public void copyFileData(String fileName, OutputStream out)
			throws IOException {
		Path source = getFilePath(fileName);
		if (!Files.exists(source)) {
			throw new FileNotFoundException(
					"Unable to find the referenced file: "
							+ source.getFileName().toString());
		}
		Files.copy(source, out);
	}

	public void getFile(Integer fileId, HttpServletResponse response)
			throws IOException {
		SCFile file = fileRepo.findOne(fileId);
		if (file != null) {
			response.setContentType(file.getContentType());
			copyFileData(file.getId() + file.getExtension(),
					response.getOutputStream());
		} else
			response.sendError(404);
	}

	/**
	 * This method reads all of the data in the provided InputStream and stores
	 * it on the file system. The data is associated with the Gift object that
	 * is provided by the caller.
	 * 
	 * @param v
	 * @param is
	 * @throws IOException
	 */
	private void saveFileData(String fileName, InputStream is)
			throws IOException {
		assert (fileName != null);

		Path target = getFilePath(fileName);
		Files.copy(is, target, StandardCopyOption.REPLACE_EXISTING);
	}

	@Transactional
	public SCFile saveFile(MultipartFile file, Portfolio portfolio)
			throws IOException {
		SCFile scFile = new SCFile();
		scFile.setPortfolio(portfolio);
		String filename = file.getOriginalFilename();
		String extension = filename.substring(filename.lastIndexOf("."))
				.toLowerCase();
		scFile.setExtension(extension);
		scFile.setContentType(file.getContentType());
		fileRepo.save(scFile);

		saveFileData(scFile.getId() + scFile.getExtension(),
				file.getInputStream());
		return scFile;
	}

	@Transactional
	public void deleteFile(Integer portfolioId, Integer fileId) {
		Portfolio portfolio = portfolioRepo.findOne(portfolioId);
		if (portfolio != null && portfolio.getCoverImage() != null
				&& portfolio.getCoverImage().getId() == fileId) {
			portfolio.setCoverImage(null);
			portfolioRepo.save(portfolio);
		}

		fileRepo.delete(fileId);
	}

}
