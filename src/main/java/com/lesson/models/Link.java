package com.lesson.models;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_link")
public class Link {
	private Long linkId;
	private String linkSrc;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "link_id")
	public Long getLinkId() {
		return linkId;
	}
	public void setLinkId(Long linkId) {
		this.linkId = linkId;
	}
	
	@Basic
	@Column(name = "link_src")
	public String getLinkSrc() {
		return linkSrc;
	}
	public void setLinkSrc(String linkSrc) {
		this.linkSrc = linkSrc;
	}
	

}
