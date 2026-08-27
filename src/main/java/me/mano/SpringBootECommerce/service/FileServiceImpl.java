package me.mano.SpringBootECommerce.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileServiceImpl implements FileService{
  
  @Override
  public String uploadImage(String path, MultipartFile file) throws IOException {
    String originalFileName = file.getOriginalFilename();

    String randomId = UUID.randomUUID().toString();
    String fileName = randomId.concat(originalFileName.substring(originalFileName.lastIndexOf('.')));

    File folder = new File(path);
    if(!folder.exists()) {
      folder.mkdirs();
    }

    String filePath = path + File.separator + fileName;
    Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);

    return fileName;
  }
}
