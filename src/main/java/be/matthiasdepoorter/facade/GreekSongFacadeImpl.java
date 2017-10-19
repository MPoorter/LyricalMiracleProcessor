package be.matthiasdepoorter.facade;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.transform.TransformerException;

import org.apache.avalon.framework.configuration.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import be.matthiasdepoorter.greek.Song;
import be.matthiasdepoorter.repositories.interfaces.GreekSongRepository;
import be.matthiasdepoorter.utilities.CreatePdfUtility;
import be.matthiasdepoorter.utilities.Listener;
import be.matthiasdepoorter.utilities.ListenerEvent;

@Service
public class GreekSongFacadeImpl implements GreekSongFacade {

	private static final Logger LOGGER = LoggerFactory.getLogger(GreekSongFacadeImpl.class);
	private GreekSongRepository repository;
	private List<Listener> listeners = new ArrayList<>();

	@Autowired
	public GreekSongFacadeImpl(GreekSongRepository repository) {
		this.repository = repository;
	}

	@Override
	public boolean createPdf(final Song song, final String location) {
		File file = transformPdfLocation(location);
		try {
			CreatePdfUtility.getInstance().createPdf(Song.class, song, file);
		} catch (SAXException | ConfigurationException | JAXBException | TransformerException | IOException e) {
			LOGGER.error(e.getMessage(), e);
			return false;
		}
		return true;
	}

	@Override
	public List<Song> getSongs() {
		return repository.findAll();
	}

	@Override
	public void createOrUpdateSongs(final List<Song> songs) {
		repository.save(songs);
		listeners.forEach(listener -> listener.changed(ListenerEvent.SONGS_UPDATED));
	}

	@Override
	public void shutDownExecutorService() {
		listeners.forEach(listener -> listener.changed(ListenerEvent.WINDOW_CLOSED));
	}

	@Override
	public void addListener(final Listener listener) {
		this.listeners.add(listener);
	}

	private File transformPdfLocation(String location) {
		String result = location.substring(0, location.indexOf('.'));
		return new File(result);
	}
}
