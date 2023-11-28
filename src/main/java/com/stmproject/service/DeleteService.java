package com.stmproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stmproject.model.STM;
import com.stmproject.repository.STM_RegistrationRepository;

@Service
public class DeleteService {
	
	 @Autowired
	    private STM_RegistrationRepository stmRepository;

	 public STM getStmByStmNo(String stmNo) {
	        return stmRepository.findById(stmNo).orElse(null);
	    }

	    public boolean isDeleteStm(String stmNo) {
	        STM stm = stmRepository.findById(stmNo).orElse(null);
	        if (stm != null) {
	            stm.setIsDeleted(true);
	            stmRepository.save(stm);
	            return true;
	        }
	        return false;
}
	    
	    
	    
	    
	    
}