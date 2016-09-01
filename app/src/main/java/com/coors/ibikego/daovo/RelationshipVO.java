package com.coors.ibikego.daovo;

public class RelationshipVO implements java.io.Serializable{
	private Integer mem_no_main;
	private Integer rel_mem_no;
	private Integer rel_status;
	private Integer rel_follow;
	public Integer getMem_no_main() {
		return mem_no_main;
	}
	public void setMem_no_main(Integer mem_no_main) {
		this.mem_no_main = mem_no_main;
	}
	public Integer getRel_mem_no() {
		return rel_mem_no;
	}
	public void setRel_mem_no(Integer rel_mem_no) {
		this.rel_mem_no = rel_mem_no;
	}
	public Integer getRel_status() {
		return rel_status;
	}
	public void setRel_status(Integer rel_status) {
		this.rel_status = rel_status;
	}
	public Integer getRel_follow() {
		return rel_follow;
	}
	public void setRel_follow(Integer rel_follow) {
		this.rel_follow = rel_follow;
	}
}