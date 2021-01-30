package pl.sdacademy.todolist.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.NoResultException;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import pl.sdacademy.todolist.service.OrderService;

@RequiredArgsConstructor
@Controller
public class PhotosController {

	private final ResourceLoader resourceLoader;
	private final OrderService orderService;
	private final String PARENT_DIR = new File(System.getProperty("user.dir")).getParent() + File.separator + "uploads"
			+ File.separator;
	
	@GetMapping("/edit/{id}/pics")
	public String showPhotos(@PathVariable("id") Long id, Model model) {
		String orderNo = orderService.findOrderNoById(id);
		File file;
		String dir = PARENT_DIR + id + File.separator;
		file = new File(dir);
		if (!file.exists()) {
			file.mkdirs();
		}
		Set<String> photosNames = Stream.of(new File(dir).listFiles())
				.filter(fileName -> !fileName.isDirectory())
				.map(File::getName)
				.collect(Collectors.toSet());
		model.addAttribute("photosNames", photosNames);
		model.addAttribute("orderNo", orderNo);
		model.addAttribute("orderId", id);
		return "photos";
	}

	@PostMapping("/upload/{id}")
	public String uploadFile(@RequestParam("file") MultipartFile mFile, @PathVariable("id") Long id,
			RedirectAttributes attributes) {

		if (mFile.isEmpty()) {
			attributes.addFlashAttribute("message", "Please select a file to upload.");
			return "redirect:/edit/" + id + "/pics";
		}

		String fileName = StringUtils.cleanPath(mFile.getOriginalFilename());

		try {
			Path path = Paths.get(PARENT_DIR + id + File.separator + fileName);
			Files.copy(mFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}

		attributes.addFlashAttribute("message", "You successfully uploaded " + fileName + '!');
		return "redirect:/edit/" + id + "/pics";
	}

	@GetMapping("/image/delete/{orderId}/{imageName}")
	public String deletePhoto(@PathVariable Long orderId, @PathVariable String imageName,
			RedirectAttributes attributes) {
		File file;
		String dir = PARENT_DIR + orderId + File.separator + imageName;
		file = new File(dir);
		attributes.addFlashAttribute("message", file.delete() 
				? "You successfully deleted " + imageName + " file!"
				: "File " + imageName + " doesn't exist or you don't have permission to delete");
		return "redirect:/edit/" + orderId + "/pics";
	}

	@GetMapping(value = "/image/{orderId}/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
	@ResponseBody
	public ResponseEntity<Resource> download(@PathVariable Long orderId, @PathVariable String imageName) {
		try {
			InputStream photo = resourceLoader.getResource("file:" + PARENT_DIR + orderId + File.separator + imageName)
					.getInputStream();
			Resource resource = new InputStreamResource(photo);
			return new ResponseEntity<>(resource, HttpStatus.OK);
		} catch (IOException e) {
			e.printStackTrace();
		}
		throw new NoResultException("file not found");
	}

}
