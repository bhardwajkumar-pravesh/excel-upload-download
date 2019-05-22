package com.dxc.cat.service;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface ExcelService {
	public ResponseEntity<byte[]> processFile(List<MultipartFile> files, Integer osCell, Integer verCell) throws IOException;
	
}
