package com.music.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.music.business.ranking.IRankingDAO;
import com.music.business.ranking.RankingDAO;
import com.music.business.region.IRegionDAO;
import com.music.entity.RankingTable;
import com.music.entity.Region;
import com.music.entity.SongRank;

@Controller
@RequestMapping("/rank")
public class RankingController {
	
	@Autowired
	private IRankingDAO rankingDAO;
	
	@Autowired
	private IRegionDAO regionDAO;
	@GetMapping
	public String ranking(@RequestParam("region") String regionId,
			@RequestParam("month") String month,
			@RequestParam("year") String year, Model model) {
		List<Region> listRegions=regionDAO.findAll();
		Region region=null;
		RankingTable rankTable=null;
		//Nếu không xác định được region thì chọn region đầu tiên
		if(regionId==null||regionId.equals("")) {
			region=listRegions.get(0);
			
		}
		else {
			region=regionDAO.findOneById(Long.parseLong(regionId));
		}
		
		if(month==null||year==null||month.equals("")||year.equals("")) {
			//Lấy bảng xếp hạng mới nhất
			rankTable=region.getListRankingTables().get(region.getListRankingTables().size()-1);
			
		}
		else {
			rankTable=rankingDAO.getRankByRegionAndTime(region, Integer.parseInt(month), Integer.parseInt(year));
		}
		
		model.addAttribute("listRegions", listRegions);
		model.addAttribute("region", region);
		model.addAttribute("rankingTable", rankTable.getListSongRanks());
		return "web/rank/rank";

	}
	

}