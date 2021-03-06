package com.music.config;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.music.business.report.IReportDAO;
import com.music.business.report.ReportDAO;
import com.music.entity.Account;
import com.music.entity.Report;





public class MyUserDetails implements UserDetails{
	
	private String username;
	private String password;
	private Boolean status;
	private String avatar;
	private List<GrantedAuthority> authorities;
	private boolean vip;
	private long diamond;
	private Long id;
	public MyUserDetails(Account account) {
		this.username=account.getUsername();
		this.password=account.getPassword();
		this.status=account.getStatus();
		this.authorities=Arrays.stream(account.getRole().split(","))
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());
		this.avatar=account.getAvatar();
		this.diamond=account.getDiamond();
		this.id=account.getId();
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return this.status;
	}
	

	
	public String getAvatar() {
		return this.avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar=avatar;
	}
	public long getDiamond() {
		return this.diamond;
	}
	public void setDiamond(long diamond) {
		this.diamond=diamond;
	}
	public Long getId() {
		return this.id;
	}
}
