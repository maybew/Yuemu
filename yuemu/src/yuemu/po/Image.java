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
public class Image implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 音乐编号
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	/**
	 * 图片宽度
	 */
	private int width;

	/**
	 * 图片高度
	 */
	private int height;

	@OneToOne(cascade={CascadeType.MERGE, CascadeType.PERSIST}, 
			optional = false)
	@JoinColumn
	private Resource resource = new Resource();

	public Image() {
		resource.setType(ResourceType.getResourceType(this.getClass()));
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Image other = (Image) obj;
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

	@Override
	public String toString() {
		return "Image [id=" + id + ", width=" + width + ", height=" + height
				+ ", resource=" + resource + "]";
	}
	
	public JSONObject toJSON() {
		JSONObject object = new JSONObject();
		try {
			object.put("resource", this.getResource().toJSON());
			object.put("width", this.getWidth());
			object.put("height", this.getHeight());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return object;
	}

}
