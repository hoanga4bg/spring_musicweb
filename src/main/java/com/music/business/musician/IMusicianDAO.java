package com.music.business.musician;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.music.entity.Musician;

public interface IMusicianDAO {
	public Musician save(Musician musician);
	public void deleteById(Long id);
	public List<Musician> findAll();
	public Musician findOneById(Long id);
	public List<Musician> findAll(Pageable pageable);
	public int totalItem();
	public List<Musician> findByNameContain(String term);
	public List<Musician> findByName(String musician);
}
