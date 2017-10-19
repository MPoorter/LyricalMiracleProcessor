package be.matthiasdepoorter.facade;

import java.util.List;

import be.matthiasdepoorter.greek.Song;
import be.matthiasdepoorter.utilities.Listener;

public interface GreekSongFacade {

	boolean createPdf(Song song, String location);

	List<Song> getSongs();

	void createOrUpdateSongs(List<Song> songs);

	void shutDownExecutorService();

	void addListener(Listener listener);
}
