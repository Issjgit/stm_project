package com.stmproject.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.stmproject.model.STM;
import com.stmproject.repository.ReviseRepository;

import jakarta.transaction.Transactional;

@Service
public class ReviseService {
	
	@Autowired
    private ReviseRepository reviceRepository;

    public STM getSTMById(String id) {
        Optional<STM> stmOptional = reviceRepository.findByStmNo(id);
        return stmOptional.orElse(null);
    }
            
   
    private static final String UPLOAD_DIR = "/STM_File";
    
    @Transactional
    public void updateSTM(STM updatedSTM) {
        Optional<STM> optionalSTM = reviceRepository.findByStmNo(updatedSTM.getStmNo());        
          STM existingSTM = optionalSTM.get();

            // Update the existingSTM with values from updatedSTM
            existingSTM.setLinkDestination(updatedSTM.getLinkDestination());
            existingSTM.setTextShortEN(updatedSTM.getTextShortEN());
            existingSTM.setTextShortJP(updatedSTM.getTextShortJP());
            existingSTM.setPdfFile(updatedSTM.getPdfFile());
            existingSTM.setWordFile(updatedSTM.getWordFile());
            existingSTM.setLastUpdated(updatedSTM.getLastUpdated());
            existingSTM.setFinalDrafterName(updatedSTM.getFinalDrafterName());
            existingSTM.setOldSTMNumber(updatedSTM.getOldSTMNumber());
            existingSTM.setRemarks1(updatedSTM.getRemarks1());
            existingSTM.setNote2(updatedSTM.getNote2());
            existingSTM.setNote3(updatedSTM.getNote3());
            // Save the updated entity
            reviceRepository.save(existingSTM);

            /*// Save the files
            try {
                //String pdfFileName = saveFile(pdfFile, "pdf");
                //String wordFileName = saveFile(wordFile, "docx");

                //existingSTM.setPdfFile(pdfFileName);
                //existingSTM.setWordFile(wordFileName);

                // Save the entity again to update file names
                //reviseRepository.save(existingSTM);
            } catch (IOException e) {
                // Handle file saving exception
                e.printStackTrace();
            }
        } else {
            // Handle the case where STM with stmNo is not found
        }*/
  
  }
    
	private String saveFile(MultipartFile file, String extension) throws IOException {
        if (file != null && !file.isEmpty()) {
            String originalFileName = file.getOriginalFilename();
            Path uploadPath = Paths.get("/STM_File", UPLOAD_DIR);
            Path filePath = uploadPath.resolve(originalFileName);
            Files.createDirectories(uploadPath);
            System.out.println("filePath " + filePath);
            // Save the file to the specified directory
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            return originalFileName;
        }
        return null;
    }

}
