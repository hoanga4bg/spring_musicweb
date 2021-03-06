package com.music.business.ranking;

import com.music.entity.RankingTable;
import com.music.entity.*;
import java.util.List;
public interface IRankingDAO {
	public boolean existMonthRank(int month, int year,Region region);
	public void createRegionRankingTable(Region region,int month,int year);
	public RankingTable getRankByRegionAndTime(Region region,int month, int year);
	public List<Song> getTheMostListenedSongs();
}
