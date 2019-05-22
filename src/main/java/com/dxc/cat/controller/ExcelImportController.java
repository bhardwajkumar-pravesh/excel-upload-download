/**
 * 
 */
package com.dxc.cat.controller;

import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.dxc.cat.controller.model.UploadModel;
import com.dxc.cat.service.ExcelService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author pkumar552
 *
 */
@RestController
public class ExcelImportController {
	 private final Logger logger = LoggerFactory.getLogger(ExcelImportController.class);

	 @Autowired
	 ExcelService excelService ;
	 
	    @PostMapping("/upload")
	    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile uploadfile, @RequestParam("oscell") String oscell, @RequestParam("vercell") String vercell) {

	        logger.debug("Single file upload!");

	        if (uploadfile.isEmpty()) {
	            return new ResponseEntity("please select a file!", HttpStatus.OK);
	        }

	        try {
	        	logger.info("saving ");
	        	Integer osCell = Integer.parseInt(oscell);
	        	Integer verCell = Integer.parseInt(vercell);
	        	return excelService.processFile(Arrays.asList(uploadfile),osCell,verCell);

	        } catch (IOException e) {
	            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	        }

	        //return new ResponseEntity("Successfully uploaded - " +	                uploadfile.getOriginalFilename(), new HttpHeaders(), HttpStatus.OK);

	    }

	    

}
