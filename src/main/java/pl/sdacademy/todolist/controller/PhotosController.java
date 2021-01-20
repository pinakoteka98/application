package pl.sdacademy.todolist.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class PhotosController {

	@GetMapping("/edit/{id}/pics")
	public String showPhotos(@PathVariable("id") Long id, Model model) {
		model.addAttribute("orderId", id);
		return "photos";
	}

	@PostMapping("/upload/{id}")
	public String uploadFile(@RequestParam("file") MultipartFile mFile, @PathVariable("id") Long id,
			RedirectAttributes attributes) {
		String uploadDir = System.getProperty("user.dir") + "\\uploads\\" + id + "\\";
		System.out.println("uploadDir:" + uploadDir);
		File file;

		// check if file is empty
		if (mFile.isEmpty()) {
			attributes.addFlashAttribute("message", "Please select a file to upload.");
			return "redirect:/edit/" + id + "/pics";
		}

		// normalize the file path
		String fileName = StringUtils.cleanPath(mFile.getOriginalFilename());

		// save the file on the local file system
		try {
			file = new File(uploadDir);
			if (!file.exists()) {
				file.mkdir();
			}
			System.out.println("Ścieżka: " + file.getAbsolutePath());
			Path path = Paths.get(uploadDir + fileName);
			System.out.println("Path:" + path);
			Files.copy(mFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// return success response
		attributes.addFlashAttribute("message", "You successfully uploaded " + fileName + '!');

		return "redirect:/edit/" + id + "/pics";
	}

}
