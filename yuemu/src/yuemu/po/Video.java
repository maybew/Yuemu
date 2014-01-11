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
public class Video implements Serializable {

	/*
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * 视频编号
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, 
			optional = false)
	@JoinColumn
	private Resource resource = new Resource();

	public Video() {
		resource.setType(ResourceType.getResourceType(this.getClass()));
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Video other = (Video) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Video [id=" + id + ", resource=" + resource + "]";
	}

	public JSONObject toJSON() {
		JSONObject object = new JSONObject();
		try {
			object.put("resource", this.getResource().toJSON());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return object;
	}
}
