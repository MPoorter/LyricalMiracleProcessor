package be.matthiasdepoorter.repositories.interfaces;

import org.springframework.data.mongodb.repository.MongoRepository;

import be.matthiasdepoorter.greek.Song;

public interface GreekSongRepository extends MongoRepository<Song, String> {

}
