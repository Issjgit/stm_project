package com.stmproject.serviceImpl;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.stmproject.model.SearchResultlist;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.stmproject.model.STM;
import com.stmproject.repository.S_UserRepository;
import com.stmproject.service.S_UserService;

@Service
public class S_UserServiceImpl implements S_UserService {

	@Autowired
	private EntityManager entManager;
	@Autowired
	private S_UserRepository sUserRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public List<SearchResultlist> getAllValues() {

		List<STM> stms = sUserRepo.findAllValues();
		List<SearchResultlist> daoList = new ArrayList<>();
		int i = 1;
		SimpleDateFormat sd = new SimpleDateFormat("yyyy/MM/dd");
		for (STM ent : stms) {
			SearchResultlist sDao = new SearchResultlist();
			sDao = modelMapper.map(ent, SearchResultlist.class);
			if (ent.getDraftingDate() != null) {
				String date = sd.format(ent.getDraftingDate());
				sDao.setDraftingDate(date);
			}
			sDao.setRowIndex(i++);
			daoList.add(sDao);
		}
		return daoList;
	}

	@Override
	public List<SearchResultlist> getValuesBySetValue(SearchResultlist dao) {
		String strQuery = "Select * from dbo.STM where STM_No like '" + dao.getStmNo() + "%'";
		StringBuffer sb = new StringBuffer();

		sb.append(strQuery);
		if (!ObjectUtils.isEmpty(dao.getStmVersion())) {
			sb.append(" and STM_Version like '" + dao.getStmVersion() + "%'");
		}

		if (!ObjectUtils.isEmpty(dao.getLinkDestination())) {
			sb.append(" and Link_Destination like '" + dao.getLinkDestination() + "%'");
		}

		if (!ObjectUtils.isEmpty(dao.getTextShortJP())) {
			sb.append(" and Text_Short_JP like '" + dao.getTextShortJP() + "%'");
		}
		if (!ObjectUtils.isEmpty(dao.getTextShortEN())) {
			sb.append(" and Text_Short_EN like '" + dao.getTextShortEN() + "%'");
		}
		if (!ObjectUtils.isEmpty(dao.getFinalDrafterName())) {
			sb.append(" and Final_Drafter_Name like '" + dao.getFinalDrafterName() + "%'");
		}
		if (!ObjectUtils.isEmpty(dao.getOldSTMNumber())) {
			sb.append(" and Old_STM_Number like '" + dao.getOldSTMNumber() + "%'");
		}
		if (!ObjectUtils.isEmpty(dao.getStartDraftDate())) {
			sb.append(" and drafting_date > '" + dao.getStartDraftDate() + "' ");
		}
		if (!ObjectUtils.isEmpty(dao.getEndDraftDate())) {
			sb.append(" and drafting_date < '" + dao.getEndDraftDate() + "' ");
		}
		sb.append(" and is_deleted='0' ");
		sb.append(" ORDER BY no ");

		String q = sb.toString();
		System.out.println("Query is : " + q);

		Query query = (Query) entManager.createNativeQuery(q, STM.class);

		List<STM> list = query.getResultList();
		List<SearchResultlist> daoList = new ArrayList<>();
		SimpleDateFormat sd = new SimpleDateFormat("yyyy/MM/dd");
		int i = 1;
		for (STM ent : list) {
			SearchResultlist sDao = new SearchResultlist();
			sDao = modelMapper.map(ent, SearchResultlist.class);
			if (ent.getDraftingDate() != null) {
				String date = sd.format(ent.getDraftingDate());
				sDao.setDraftingDate(date);
			}
			sDao.setRowIndex(i++);
			daoList.add(sDao);
		}
		return daoList;
	}

}
