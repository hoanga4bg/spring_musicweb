package com.music.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Account {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String username;
	private String password;
	private String email;
	private String role;
	private Boolean status;
	private String avatar;
	@Column(name="user_diamond",columnDefinition="bigint(8) default 0",nullable = true)
	private Integer diamond;
	@Lob
	private String info;
	@OneToMany(mappedBy = "account")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private List<Favorite> listFavor;
	
	
	@OneToMany(mappedBy = "account")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private List<Comment> listComment;
	
	@OneToMany(mappedBy = "listener")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private List<Listens> listListens;
	
	@OneToMany(mappedBy = "createBy")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private List<PlayList> listPlayLists;

	@OneToMany(mappedBy = "account")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private List<PayHistory> listPay;
	
	@OneToMany(mappedBy = "account")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private List<Report> listReport;
	
}
