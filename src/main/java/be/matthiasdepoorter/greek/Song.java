package be.matthiasdepoorter.greek;

import java.util.Collection;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.data.annotation.Id;

@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement
public class Song {

	@Id
	private String youtubeID;

	private String title;

	private String lyrics;

	private int version;

	private Date dateAdded;

	private Collection<Lemma> lemmas;

	public Song() {

	}

	public String getYoutubeID() {
		return youtubeID;
	}

	public void setYoutubeID(final String youtubeID) {
		this.youtubeID = youtubeID;
	}

	public String getLyrics() {
		return lyrics;
	}

	public void setLyrics(final String lyrics) {
		this.lyrics = lyrics;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public Date getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(final Date dateAdded) {
		this.dateAdded = dateAdded;
	}

	public Collection<Lemma> getLemmas() {
		return lemmas;
	}

	public void setLemmas(final Collection<Lemma> lemmas) {
		this.lemmas = lemmas;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(final int version) {
		this.version = version;
	}
}
