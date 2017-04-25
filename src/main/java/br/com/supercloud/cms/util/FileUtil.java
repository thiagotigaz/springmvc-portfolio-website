package br.com.supercloud.cms.util;

import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Method;
import org.imgscalr.Scalr.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * This class provides a simple implementation to store binary data on the file
 * system in a folder folder. The class provides methods for saving and
 * retrieving binary data.
 * 
 * @author thiago
 *
 */
@Component
public class FileUtil {

	private Path targetDir_;

	@Autowired
	public FileUtil(@Value("${config.files.path}") String path) throws IOException {
		targetDir_ = Paths.get(path);

		if (!Files.exists(targetDir_)) {
			Files.createDirectories(targetDir_);
		}
	}

	// Private helper method for resolving file paths
	private Path getFilePath(String fileName) {
		assert (fileName != null);

		return targetDir_.resolve(fileName);
	}

	/**
	 * This method copies the binary data for the given fileName to the provided
	 * output stream. The caller is responsible for ensuring that the specified
	 * file has binary data associated with it. If not, this method will throw a
	 * FileNotFoundException.
	 * 
	 * @param v
	 * @param out
	 * @throws IOException
	 */
	public void copyFileData(String fileName, OutputStream out) throws IOException {
		Path source = getFilePath(fileName);

		if (!Files.exists(source)) {
			throw new FileNotFoundException("Unable to find the referenced file: " + source.getFileName().toString());
		}

		Files.copy(source, out);
	}

	private static final int WIDTH = 1600;
	private static final int HEIGHT = 900;
	private static final int WIDTH_THUMB = 512;
	private static final int HEIGHT_THUMB = 512;

	public void resizeAndSaveImgAndThumb(String filename, String fileExtension, InputStream is) {
		try {
			fileExtension = fileExtension.toLowerCase();

			BufferedImage bufferedImage = ImageIO.read(is);

			// PROCESSAMENTO IMAGEM GRANDE
			BufferedImage img = Scalr.resize(bufferedImage, Method.ULTRA_QUALITY, Mode.AUTOMATIC, WIDTH, HEIGHT);
			if (img.getHeight() < HEIGHT) {
				int padding = (HEIGHT - img.getHeight()) % 2 == 0 ? (HEIGHT - img.getHeight()) / 2 : ((HEIGHT - img.getHeight()) / 2) + 1;

				img = Scalr.pad(img, padding, Color.WHITE);
				img = Scalr.crop(img, padding, 0, WIDTH, HEIGHT);
			}

			if (img.getWidth() < WIDTH) {
				int padding = (WIDTH - img.getWidth()) % 2 == 0 ? (WIDTH - img.getWidth()) / 2 : ((WIDTH - img.getWidth()) / 2) + 1;

				img = Scalr.pad(img, padding, Color.WHITE);
				img = Scalr.crop(img, 0, padding, WIDTH, HEIGHT);
			}

			
			// PROCESSAMENTO THUMB
			BufferedImage thumb = bufferedImage;
			if (thumb.getWidth() < thumb.getHeight()) {
				thumb = Scalr.resize(thumb, Method.ULTRA_QUALITY, Mode.FIT_TO_WIDTH, WIDTH_THUMB, HEIGHT_THUMB);

				int padding = (thumb.getHeight() - HEIGHT_THUMB) % 2 == 0 ? (thumb.getHeight() - HEIGHT_THUMB) / 2 : ((thumb.getHeight() - HEIGHT_THUMB) / 2) + 1;

				thumb = Scalr.crop(thumb, 0, padding, WIDTH_THUMB, HEIGHT_THUMB);
			}else if(thumb.getWidth() > thumb.getHeight()  ){
				thumb = Scalr.resize(thumb, Method.ULTRA_QUALITY, Mode.FIT_TO_HEIGHT, WIDTH_THUMB, HEIGHT_THUMB);

				int posX = (thumb.getWidth() - WIDTH_THUMB) % 2 == 0 ? (thumb.getWidth() - WIDTH_THUMB) / 2 : ((thumb.getWidth() - WIDTH_THUMB) / 2) + 1;

				thumb = Scalr.crop(thumb, posX, 0, WIDTH_THUMB, HEIGHT_THUMB);
			} else{
				thumb = Scalr.resize(thumb, Method.ULTRA_QUALITY, Mode.AUTOMATIC, WIDTH_THUMB, HEIGHT_THUMB);
			}
			
			// GRAVACAO DAS IMAGENS PRONTAS
			ImageIO.write(img, fileExtension.replace(".", ""), getFilePath(filename + fileExtension).toFile());
			ImageIO.write(thumb, fileExtension.replace(".", ""), getFilePath(filename + "_thumb" + fileExtension).toFile());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
