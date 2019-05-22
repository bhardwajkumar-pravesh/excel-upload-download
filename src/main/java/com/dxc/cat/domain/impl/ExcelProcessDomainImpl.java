/**
 * 
 */
package com.dxc.cat.domain.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import com.dxc.cat.domain.ExcelProcessDomain;

/**
 * @author pkumar552
 *
 */
@Component
public class ExcelProcessDomainImpl implements ExcelProcessDomain {

	private final Logger logger = LoggerFactory.getLogger(ExcelProcessDomainImpl.class);

	@Override
	public ResponseEntity<byte[]> processFile(String uploadfolder, MultipartFile mFile, Integer osCell, Integer osVerCell)
			throws IOException {

		byte[] bytes=null;

	    HttpHeaders headers =null;
	    
		/** Updating existing excel */
		try {

			InputStream file = mFile.getInputStream();

			XSSFWorkbook workbook = new XSSFWorkbook(file);

			XSSFSheet sheet = workbook.getSheetAt(0);

			Iterator<Row> rowIterator = sheet.iterator();

			if (rowIterator.hasNext())
				rowIterator.next();
			int rowCtr = 2;
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				Iterator<Cell> cellIterator = row.cellIterator();
				String os = null;
				float ver = new Float("0");
				int colCtr = 0;
				int flag = 1;
				while (cellIterator.hasNext()) {

					Cell cell = cellIterator.next();
					if (colCtr == osCell)
						os = cell.getStringCellValue();
					if (colCtr == osVerCell) {
						try {
							ver = new Float(cell.getNumericCellValue());
						} catch (Exception e) {
							flag = 0;
							logger.info("Row number :" + rowCtr + " colomn number: " + colCtr
									+ " have wrong data in uploaded excel sheet : ");// +cell.getStringCellValue().toString());
							logger.debug("exception : " + e);
							ver = new Float("0");
						}
					}

					colCtr++;
				} // end of cells in a row

				/* Algorithmic implementation */
				logger.info("os : " + os + " ver: " + ver + " flag:" + flag);
				if (flag == 1) {
					if ("Windows".equalsIgnoreCase(os)) {
						if (ver > 2005) {
							Cell newCell = row.createCell(colCtr);
							newCell.setCellValue("Can be Containerised");
						} else {
							Cell newCell = row.createCell(colCtr);
							newCell.setCellValue("Cannot be Containerised");
						}

					} else if (("Linux".equalsIgnoreCase(os))) {
						if (ver > 7) {
							Cell newCell = row.createCell(colCtr);
							newCell.setCellValue("Can be Containerised");
						} else {
							Cell newCell = row.createCell(colCtr);
							newCell.setCellValue("Cannot be Containerised");
						}
					}
				} // end of flag

				rowCtr++;
			}

			file.close();
			FileOutputStream out = new FileOutputStream(uploadfolder + mFile.getOriginalFilename());
			workbook.write(out);
			
			logger.info("done");
			/** download logic*/	
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			//
					    try {
					        workbook.write(bos);
					    } finally {
					        bos.close();
					    }
					    workbook.close();		    
					     bytes = bos.toByteArray();

					    headers = new HttpHeaders();

					    headers.set("Content-Type", "application/vnd.ms-excel;");
					    headers.set("content-length",Integer.toString(bytes.length));
					    headers.set("Content-Disposition", "attachment; filename="+mFile.getOriginalFilename());
					    
		} catch (Exception e) {
			e.printStackTrace();
		}

		return  new ResponseEntity<byte[]>(bytes, headers, HttpStatus.CREATED);
	}

}
