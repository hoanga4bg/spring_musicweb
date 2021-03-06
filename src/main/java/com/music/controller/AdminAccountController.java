package com.music.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.music.business.account.IAccountDAO;
import com.music.business.pay.IPayDAO;
import com.music.entity.Account;

@Controller
@RequestMapping("/admin/account")
public class AdminAccountController {
	
	@Autowired
	private IAccountDAO accountDAO;
	@Autowired
	private IPayDAO payDAO;

	
	@GetMapping("/active")
	public String homeNormal(Model model) {
		List<Account> listAccounts=accountDAO.getActive();
		Collections.reverse(listAccounts);
		model.addAttribute("listAccounts", listAccounts);
		return "admin/account/accountHome";
	}
	
	
	@GetMapping("/block")
	public String homeBlock(Model model) {
		List<Account> listAccounts=accountDAO.getBlock();
		Collections.reverse(listAccounts);
		model.addAttribute("listAccounts", listAccounts);
		return "admin/account/accountHome";
	}
	
	@PostMapping("/block")
	public String blockAccount(@RequestParam("id") String id) {
		Account account=accountDAO.findById(Long.parseLong(id));
		if(account.getStatus()==true) {
			account.setStatus(false);
		}
		else {
			account.setStatus(true);
		}
		accountDAO.save(account);
	
		return "redirect:/admin/account/block"; 
	}

}
