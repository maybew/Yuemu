package yuemu.po;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.json.JSONException;
import org.json.JSONObject;

@Entity
public class Music implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 音乐编号
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * 歌手
	 */
	private String singer = "";

	/**
	 * 流派
	 */

	private String genre = "";

	/**
	 * 专辑
	 */
	private String album = "";
	/*
	 * 歌曲
	 */
	private String song = "";

	@OneToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, 
			optional = false)
	@JoinColumn
	private Resource resource = new Resource();

	public Music() {
		resource.setType(ResourceType.getResourceType(this.getClass()));
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public String getSong() {
		return song;
	}

	public void setSong(String song) {
		this.song = song;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSinger() {
		return singer;
	}

	public void setSinger(String singer) {
		this.singer = singer;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return "Music [id=" + id + ", singer=" + singer + ", genre=" + genre
				+ ", album=" + album + ", song=" + song + ", resource="
				+ resource + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Music other = (Music) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public JSONObject toJSON() {
		JSONObject object = new JSONObject();
		try {
			object.put("resource", this.getResource().toJSON());
			object.put("song", this.getSong());
			object.put("singer", this.getSinger());
			object.put("album", this.getAlbum());
			object.put("genre", this.getGenre());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return object;
	}
}
