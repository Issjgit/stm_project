package com.stmproject.model;

import org.springframework.stereotype.Component;

@Component
public class SearchPageDao {

	private int rowIndex;

	private Integer no;

	private String sTM_No;

	private int Stm_version;

	private String link_destination;

	private String text_short_jpn;

	private String text_short_eng;

	private String pdf_file;

	private String word_file;

	private String final_drafter_Name;

	private String old_stm_number;

	private String remarks1;

	private String note2;

	private String note3;

	private String creator_sso_id;

	private byte is_deleted;

	public SearchPageDao() {
		// TODO Auto-generated constructor stub
	}

	public int getRowIndex() {
		return rowIndex;
	}

	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}

	public Integer getNo() {
		return no;
	}

	public void setNo(Integer no) {
		this.no = no;
	}

	public String getsTM_No() {
		return sTM_No;
	}

	public void setsTM_No(String sTM_No) {
		this.sTM_No = sTM_No;
	}

	public int getStm_version() {
		return Stm_version;
	}

	public void setStm_version(int stm_version) {
		Stm_version = stm_version;
	}

	public String getLink_destination() {
		return link_destination;
	}

	public void setLink_destination(String link_destination) {
		this.link_destination = link_destination;
	}

	public String getText_short_jpn() {
		return text_short_jpn;
	}

	public void setText_short_jpn(String text_short_jpn) {
		this.text_short_jpn = text_short_jpn;
	}

	public String getText_short_eng() {
		return text_short_eng;
	}

	public void setText_short_eng(String text_short_eng) {
		this.text_short_eng = text_short_eng;
	}

	public String getPdf_file() {
		return pdf_file;
	}

	public void setPdf_file(String pdf_file) {
		this.pdf_file = pdf_file;
	}

	public String getWord_file() {
		return word_file;
	}

	public void setWord_file(String word_file) {
		this.word_file = word_file;
	}

	public String getFinal_drafter_Name() {
		return final_drafter_Name;
	}

	public void setFinal_drafter_Name(String final_drafter_Name) {
		this.final_drafter_Name = final_drafter_Name;
	}

	public String getOld_stm_number() {
		return old_stm_number;
	}

	public void setOld_stm_number(String old_stm_number) {
		this.old_stm_number = old_stm_number;
	}

	public String getRemarks1() {
		return remarks1;
	}

	public void setRemarks1(String remarks1) {
		this.remarks1 = remarks1;
	}

	public String getNote2() {
		return note2;
	}

	public void setNote2(String note2) {
		this.note2 = note2;
	}

	public String getNote3() {
		return note3;
	}

	public void setNote3(String note3) {
		this.note3 = note3;
	}

	public String getCreator_sso_id() {
		return creator_sso_id;
	}

	public void setCreator_sso_id(String creator_sso_id) {
		this.creator_sso_id = creator_sso_id;
	}

	public byte getIs_deleted() {
		return is_deleted;
	}

	public void setIs_deleted(byte is_deleted) {
		this.is_deleted = is_deleted;
	}

	@Override
	public String toString() {
		return "SearchPageDao [rowIndex=" + rowIndex + ", no=" + no + ", sTM_No=" + sTM_No + ", Stm_version="
				+ Stm_version + ", link_destination=" + link_destination + ", text_short_jpn=" + text_short_jpn
				+ ", text_short_eng=" + text_short_eng + ", pdf_file=" + pdf_file + ", word_file=" + word_file
				+ ", final_drafter_Name=" + final_drafter_Name + ", old_stm_number=" + old_stm_number + ", remarks1="
				+ remarks1 + ", note2=" + note2 + ", note3=" + note3 + ", creator_sso_id=" + creator_sso_id
				+ ", is_deleted=" + is_deleted + "]";
	}
}
