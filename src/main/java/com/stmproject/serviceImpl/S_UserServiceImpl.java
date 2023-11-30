package com.stmproject.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import com.stmproject.model.SearchPageDao;
import com.stmproject.model.STM;
import com.stmproject.repository.S_UserRepository;
import com.stmproject.service.S_UserService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

@Service
public class S_UserServiceImpl implements S_UserService {

	@Autowired
	private EntityManager entManager;
	@Autowired
	private S_UserRepository sUserRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public List<SearchPageDao> getAllValues() {

		List<STM> stms = sUserRepo.findAllValues();
		List<SearchPageDao> daoList = new ArrayList<>();
		int i = 1;
		for (STM ent : stms) {
			SearchPageDao sDao = new SearchPageDao();
			sDao = modelMapper.map(ent, SearchPageDao.class);
			sDao.setRowIndex(i++);
			daoList.add(sDao);
		}
		return daoList;
	}

	@Override
	public List<SearchPageDao> getValuesBySetValue(SearchPageDao dao) {
		String strQuery = "Select * from dbo.STM where STM_No like '" + dao.getsTM_No() + "%'";
		StringBuffer sb = new StringBuffer();

		sb.append(strQuery);
		if (!ObjectUtils.isEmpty(dao.getLink_destination())) {
			sb.append(" and Link_Destination like '" + dao.getLink_destination() + "%'");
		}

		if (!ObjectUtils.isEmpty(dao.getText_short_jpn())) {
			sb.append(" and Text_Short_JP like '" + dao.getText_short_jpn() + "%'");
		}
		if (!ObjectUtils.isEmpty(dao.getText_short_eng())) {
			sb.append(" and Text_Short_EN like '" + dao.getText_short_eng() + "%'");
		}
		if (!ObjectUtils.isEmpty(dao.getFinal_drafter_Name())) {
			sb.append(" and Final_Drafter_Name like '" + dao.getFinal_drafter_Name() + "%'");
		}
		if (!ObjectUtils.isEmpty(dao.getOld_stm_number())) {
			sb.append(" and Old_STM_Number like '" + dao.getOld_stm_number() + "%'");
		}

		String q = sb.toString();
		System.out.println("Query is : " + q);

		Query query = (Query) entManager.createNativeQuery(q, STM.class);

		List<STM> list = query.getResultList();
		List<SearchPageDao> daoList = new ArrayList<>();
		int i = 1;
		for (STM ent : list) {
			SearchPageDao sDao = new SearchPageDao();
			sDao = modelMapper.map(ent, SearchPageDao.class);
			sDao.setRowIndex(i++);
			daoList.add(sDao);
		}
		return daoList;
	}

}
