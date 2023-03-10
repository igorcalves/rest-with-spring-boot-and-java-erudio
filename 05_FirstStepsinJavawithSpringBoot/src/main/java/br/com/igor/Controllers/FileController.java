package br.com.igor.Controllers;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.igor.data.vo.v1.UploadFileRespondeVO;
import br.com.igor.services.FileStorageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

@Tag(name = "File Endpoint")
@RestController
@RequestMapping("/api/file/v1")
public class FileController {
	
	private Logger logger = Logger.getLogger(FileController.class.getName());
	
	@Autowired
	private FileStorageService	service;
	
	@PostMapping("/uploadFile")
	private UploadFileRespondeVO uploadFile(@RequestParam("file") MultipartFile file) {
		logger.info("Storing file to Disk");
		var fileName = service.storeFile(file);
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("/api/file/v1/donwloadFile/")
				.path(fileName)
				.toUriString();
		return new UploadFileRespondeVO(fileName,fileDownloadUri,file.getContentType(),file.getSize());
	}
	@PostMapping("/uploadMultipleFiles")
	private List<UploadFileRespondeVO> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
		logger.info("Storing files to Disk");
		return Arrays.asList(files)
				.stream()
				.map(file -> uploadFile(file))
				.collect(Collectors.toList());
	}
	
	
	@GetMapping("/donwloadFile/{fileName:.+}")
	private ResponseEntity<Resource> downLoadFile(@PathVariable("fileName") String fileName,HttpServletRequest request) {
		logger.info("Reading a file on Disk");
		
		Resource resource = service.loadFileAsResource(fileName);
		
		String contentType = "";
		
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (Exception e) {
			logger.info("Could not determine file type!");
		}
		
		if(contentType.isBlank()) contentType = "application/octet-stream";
		
		return  ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachmen; fileName=\"" + resource.getFilename() + "\"")
				.body(resource);
	}
	


}
