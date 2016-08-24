package com.coors.ibikego.daovo;

import java.sql.Date;

public class TravelVO implements java.io.Serializable{
	private Integer tra_no;
	private Integer mem_no;
	private Integer loc_no;
	private Integer tra_class_status;
	private String tra_name;
	private String tra_content;
	private String tra_tel;
	private String tra_add;
    private Date tra_cre;
	private Double tra_lati;
	private Double tra_longi;
	public Integer getTra_no() {
		return tra_no;
	}
	public void setTra_no(Integer tra_no) {
		this.tra_no = tra_no;
	}
	public Integer getMem_no() {
		return mem_no;
	}
	public void setMem_no(Integer mem_no) {
		this.mem_no = mem_no;
	}
	public Integer getLoc_no() {
		return loc_no;
	}
	public void setLoc_no(Integer loc_no) {
		this.loc_no = loc_no;
	}
	public Integer getTra_class_status() {
		return tra_class_status;
	}
	public void setTra_class_status(Integer tra_class_status) {
		this.tra_class_status = tra_class_status;
	}
	public String getTra_name() {
		return tra_name;
	}
	public void setTra_name(String tra_name) {
		this.tra_name = tra_name;
	}
	public String getTra_content() {
		return tra_content;
	}
	public void setTra_content(String tra_content) {
		this.tra_content = tra_content;
	}
	public String getTra_tel() {
		return tra_tel;
	}
	public void setTra_tel(String tra_tel) {
		this.tra_tel = tra_tel;
	}
	public String getTra_add() {
		return tra_add;
	}
	public void setTra_add(String tra_add) {
		this.tra_add = tra_add;
	}
	public Date getTra_cre() {
		return tra_cre;
	}
	public void setTra_cre(Date tra_cre) {
		this.tra_cre = tra_cre;
	}
	public Double getTra_lati() {
		return tra_lati;
	}
	public void setTra_lati(Double tra_lati) {
		this.tra_lati = tra_lati;
	}
	public Double getTra_longi() {
		return tra_longi;
	}
	public void setTra_longi(Double tra_longi) {
		this.tra_longi = tra_longi;
	}
}