package com.hbc.utili;

import java.io.File;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.hbc.constant.Globals;

public class SaveFile {
	public static final String doSaveFile(MultipartFile file, String username) throws IllegalStateException, IOException {

		String filePath = Globals.FOLDER_PATH + username + file.getOriginalFilename();
		file.transferTo(new File(filePath));
		String fileUrl = Globals.HOST + Globals.PORT + "/" + Globals.FOLDER_NAME + "//" + file.getOriginalFilename();
		return fileUrl;
	}
}
