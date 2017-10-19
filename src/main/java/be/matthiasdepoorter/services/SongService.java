package be.matthiasdepoorter.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import be.matthiasdepoorter.greek.Song;

@Service("SongService")
public class SongService {

	@Autowired
	private RestTemplate template;

	@Value("http://matthiasdepoorter.herokuapp.com/Greek/Song")
	private String baseURL;

	public List<String> getSongs() {
		Map<String, String> songs = template.getForObject(baseURL + "/Recent", Map.class);
		return new ArrayList<>(songs.keySet());
	}

	public Song getSong(String id) {
		Song song = template.getForObject(baseURL + "/{0}", Song.class, id);
		return song;
	}
}
