package com.dxc.cat.domain;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface ExcelProcessDomain {

	
	public ResponseEntity<byte[]> processFile(String uploadfolder, MultipartFile mFile, Integer osCell, Integer osVerCell ) throws IOException ;
}
