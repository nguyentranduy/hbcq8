package com.hbc.gcp.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Service
public class GcpService {

	@Value("classpath:gcp_service.json")
	private Resource resource;

	private static final GsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
	private String serviceAccountKeyPath;

	private String getPathToGoogleCredentials() {
		try {
			Path filePath = Paths.get(resource.getURI());
			return filePath.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getServiceAccountKeyPath() {
		return serviceAccountKeyPath;
	}

	public String uploadImageToDrive(File file) throws GeneralSecurityException, IOException {
		try {
			String folderId = "1x6N46NhlynITpT5nxXQ7p4E7hLSCj7vb";
			Drive drive = createDriveService();
			com.google.api.services.drive.model.File fileMetaData = new com.google.api.services.drive.model.File();
			fileMetaData.setName(file.getName());
			fileMetaData.setParents(Collections.singletonList(folderId));
			FileContent mediaContent = new FileContent("image/jpeg", file);
			com.google.api.services.drive.model.File uploadedFile = drive.files().create(fileMetaData, mediaContent)
					.setFields("id").execute();
			String imageUrl = "https://drive.google.com/uc?export=view&id=" + uploadedFile.getId();
			System.out.println("IMAGE URL: " + imageUrl);
			file.delete();
			return imageUrl;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}

	}

	private Drive createDriveService() throws GeneralSecurityException, IOException {

		GoogleCredential credential = GoogleCredential.fromStream(new FileInputStream(getPathToGoogleCredentials()))
				.createScoped(Collections.singleton(DriveScopes.DRIVE));

		return new Drive.Builder(GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, credential).build();

	}
}
