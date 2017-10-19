package be.matthiasdepoorter.services;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import be.matthiasdepoorter.facade.GreekSongFacade;
import be.matthiasdepoorter.greek.Song;
import be.matthiasdepoorter.utilities.ListenerEvent;

@Service
public class SongExecutorService extends Thread {

	private static final Logger LOGGER = LoggerFactory.getLogger(SongExecutorService.class);
	private SongService songService;
	private GreekSongFacade facade;
	private boolean allowedToFetch = true;

	@Autowired
	public SongExecutorService(SongService songService, GreekSongFacade facade) {
		this.songService = songService;
		this.facade = facade;
		this.start();
		facade.addListener(event -> {
			if (event == ListenerEvent.WINDOW_CLOSED) {
				shutdownService();
			}
		});
	}

	@Override
	public void run() {
		ExecutorService executorService = Executors.newCachedThreadPool();
		while (allowedToFetch) {
			run(executorService);
			try {
				Thread.sleep(600_000);
			} catch (InterruptedException e) {
				LOGGER.warn(e.getMessage(), e);
			}
		}
		executorService.shutdown();
	}

	private void run(ExecutorService executorService) {
		Future<List<Song>> result = executorService.submit(() -> {
			List<String> songIds = songService.getSongs();
			List<Song> songs = songIds.stream().map(id -> songService.getSong(id)).collect(Collectors.toList());
			return songs;
		});

		try {
			facade.createOrUpdateSongs(result.get());
		} catch (InterruptedException | ExecutionException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	private void shutdownService() {
		this.allowedToFetch = false;
		if (!this.isInterrupted()) {
			this.interrupt();
		}
	}
}
