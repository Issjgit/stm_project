package com.stmproject.service;

import java.util.List;

import org.springframework.stereotype.Service;
import com.stmproject.model.SearchPageDao;

@Service
public interface S_UserService {

	public List<SearchPageDao> getAllValues();

	public List<SearchPageDao> getValuesBySetValue(SearchPageDao dao);

}
