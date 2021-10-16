package com.music.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.music.business.account.IAccountDAO;
import com.music.business.playlist.IPlayListDAO;
import com.music.business.song.ISongDAO;
import com.music.dto.SongDTO;
import com.music.dto.convert.SongConvert;
import com.music.entity.Account;
import com.music.entity.PlayList;
import com.music.entity.Song;
import com.music.entity.SongInPlayList;

@Controller
@RequestMapping("/admin/playlist")
public class AdminPlayListController {
	@Autowired
	private IPlayListDAO playListDAO;
	
	@Autowired
	private IAccountDAO accountDAO;
	@Autowired
	private ISongDAO songDAO;
	
	@Autowired
	private SongConvert songConvert;
	@GetMapping
	public String homePlayList(Model model) {
		List<PlayList> listPlaylists=new ArrayList<PlayList>();
		listPlaylists=playListDAO.findAllByAccount(accountDAO.findByUsername("admin"));
		Collections.reverse(listPlaylists);
		
		model.addAttribute("listPlaylists", listPlaylists);
		
		return "admin/playlist/playlistHome";
		
	}
	
	@GetMapping("/add")
	public String addPlaylist(Model model) {
		PlayList playlist= new PlayList();
		model.addAttribute("playlist", playlist);
		return "admin/playlist/addPlaylist";
	}
	
	
	@PostMapping("/add")
	public String addPlaylist(PlayList playList) {
		playList.setCreateDate(new Date());
		Account account=accountDAO.findByUsername("admin");
		playList.setCreateBy(account);
		playListDAO.save(playList);
		return "redirect:/admin/playlist";
	}
	
	@GetMapping("/edit")
	public String editPlaylist(@RequestParam("id") String id,Model model) {
		PlayList playlist= playListDAO.findById(Long.parseLong(id));
		model.addAttribute("playlist", playlist);
		return "admin/playlist/addPlaylist";
	}
	
	@GetMapping("/detail")
	public String deltail(@RequestParam("id") String id,Model model) {
		List<Song> songs=new ArrayList<Song>();
		List<Song> listSongs=new ArrayList<Song>();
		PlayList playlist =  playListDAO.findById(Long.parseLong(id));
		List<SongInPlayList> addedSongs=playlist.getSongInPlayLists();
		List<Song> allSongs=songDAO.findAll();
		
		//Lay bai hat da duoc them
		for(SongInPlayList sipl: addedSongs) {
			songs.add(sipl.getSong());
		}

		//Lay bai hat chua duoc them
		for(Song song:allSongs) {
			if(songs.contains(song)==false) {
				listSongs.add(song);
			}
		}
		List<SongDTO> listSongDTO=new ArrayList<SongDTO>();
		for(Song s:listSongs) {
			listSongDTO.add(songConvert.toDTO(s));
		}
		List<SongDTO> songsDTO=new ArrayList<SongDTO>();
		for(Song s:songs) {
			songsDTO.add(songConvert.toDTO(s));
		}
		model.addAttribute("playlist", playlist);
		model.addAttribute("songs", songsDTO);
		model.addAttribute("listSongs", listSongDTO);
		return "admin/playlist/songInPlaylist";
		
	}
	@PostMapping("/addsongtoplay")
	public String addSongToPlaylist(@RequestParam("playid") String play_id,@RequestParam("songid") String song_id) {
		Song song=songDAO.findOneById(Long.parseLong(song_id));
		PlayList playlist=playListDAO.findById(Long.parseLong(play_id));
		playListDAO.addSongToPlaylist(song, playlist);
		return "redirect:/admin/playlist/detail?id="+play_id;
	}
	@PostMapping("/deletesongfromplay")
	public String deleteSongToPlaylist(@RequestParam("playid") String play_id,@RequestParam("songid") String song_id) {
		Song song=songDAO.findOneById(Long.parseLong(song_id));
		PlayList playlist=playListDAO.findById(Long.parseLong(play_id));
		playListDAO.deleteSongFromPlayList(song, playlist);
		return "redirect:/admin/playlist/detail?id="+play_id;
	}
	
	@PostMapping("/delete")
	public String deletePlayList(@RequestParam("id") String id) {
		playListDAO.deleteById(Long.parseLong(id));
		return "redirect:/admin/playlist?success=true";
	}
	
	@GetMapping("/test")
	public String test(Model model) {
		PlayList playlist= new PlayList();
		model.addAttribute("playlist", playlist);
		return "test";
	}
}
