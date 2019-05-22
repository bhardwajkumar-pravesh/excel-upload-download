/**
 * 
 */
package com.dxc.cat.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dxc.cat.domain.ExcelProcessDomain;
import com.dxc.cat.service.ExcelService;

/**
 * @author pkumar552
 *
 */
@Service
public class ExcelServiceImpl implements ExcelService {

	private final Logger logger = LoggerFactory.getLogger(ExcelServiceImpl.class);
	// Save the uploaded file to this folder
	private String uploadfolder = "C://temp//";
	
	@Autowired
	ExcelProcessDomain excelProcessDomain;

	@Override
	public ResponseEntity<byte[]> processFile(List<MultipartFile> files, Integer osCell, Integer verCell) throws IOException {
		MultipartFile file=null;
		Iterator<MultipartFile> iterator = files.iterator();
		if (iterator.hasNext()) {
			file = iterator.next();
		}
		// process file
		

			
			String originalFilename = file.getOriginalFilename();
			logger.info("Original file NAme: " + originalFilename);
			
			return excelProcessDomain.processFile(uploadfolder, file, osCell-1, verCell-1); //index start from 0
		

	}

}
