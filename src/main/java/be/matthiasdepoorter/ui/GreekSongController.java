package be.matthiasdepoorter.ui;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import be.matthiasdepoorter.facade.GreekSongFacade;
import be.matthiasdepoorter.greek.Song;
import be.matthiasdepoorter.utilities.ListenerEvent;

@Service("GreekSongController")
public class GreekSongController {

	private GreekSongFacade facade;

	private GreekSongView view;
	private List<Song> songs = new ArrayList<>();

	@Autowired
	public GreekSongController(GreekSongFacade facade) {
		this.facade = facade;
		facade.addListener(event -> {
			if (event == ListenerEvent.SONGS_UPDATED) {
				view.setSongList();
			}
		});
		init();
	}

	private void init() {
		view = new GreekSongView(this);
	}

	List<String> getSongTitles() {
		songs = facade.getSongs().stream().sorted(Comparator.comparing(Song::getTitle)).collect(Collectors.toList());
		return songs.stream().map(Song::getTitle).collect(Collectors.toList());
	}

	boolean createPdf(int i, String location) {
		Song song = songs.get(i);
		return facade.createPdf(song, location);
	}

	void shutDownExecutorService() {
		facade.shutDownExecutorService();
	}
}
