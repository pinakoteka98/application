package pl.sdacademy.todolist.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class PhotosController {
	
	private final String PARENT_DIR = new File (System.getProperty("user.dir")).getParent() + "\\uploads\\";

	@GetMapping("/edit/{id}/pics")
	public String showPhotos(@PathVariable("id") Long id, Model model) {
		File file;
		String dir = PARENT_DIR + id + "\\";
		file = new File(dir);
		if (!file.exists()) {
			file.mkdirs();
		}
		Set<String> photosNames = Stream.of(new File(dir).listFiles())
			.filter(fileName -> !fileName.isDirectory())
			.map(File::getName)
			.collect(Collectors.toSet());
		model.addAttribute("photosNames", photosNames);
		model.addAttribute("orderId", id);
		photosNames.forEach(photo-> System.out.println(photo));
		return "photos";
	}

	@PostMapping("/upload/{id}")
	public String uploadFile(@RequestParam("file") MultipartFile mFile, @PathVariable("id") Long id,
			RedirectAttributes attributes) {
		
		String uploadDir = System.getProperty("user.dir") + "\\uploads\\" + id + "\\";
		System.out.println("uploadDir:" + uploadDir);
		File file;
		
		System.out.println("parentDir:" + PARENT_DIR);

		// check if file is empty
		if (mFile.isEmpty()) {
			attributes.addFlashAttribute("message", "Please select a file to upload.");
			return "redirect:/edit/" + id + "/pics";
		}

		// normalize the file path
		String fileName = StringUtils.cleanPath(mFile.getOriginalFilename());

		// save the file on the local file system
		try {
//			file = new File(uploadDir);
			file = new File(PARENT_DIR + id + "\\");
			if (!file.exists()) {
				file.mkdirs();
			}
			System.out.println("Ścieżka: " + file.getAbsolutePath());
			Path path = Paths.get(PARENT_DIR + id + "\\" + fileName);
			System.out.println("Path:" + path);
			Files.copy(mFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// return success response
		attributes.addFlashAttribute("message", "You successfully uploaded " + fileName + '!');

		return "redirect:/edit/" + id + "/pics";
	}
	
	@GetMapping(value = "/image/{orderId}/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
	@ResponseBody
	public byte[] getImage(@PathVariable Long orderId, @PathVariable String imageName) throws IOException {

	    File serverFile = new File(PARENT_DIR + orderId + "\\" + imageName);
	    System.out.println("Path:" + serverFile.getCanonicalPath());

	    return Files.readAllBytes(serverFile.toPath());
	}

}
