package yuemu.po;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.json.JSONException;
import org.json.JSONObject;

@Entity
public class Document implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 文档编号
	 */
	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * 作者
	 */
	private String author = "";

	@OneToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, 
			optional = false)
	@JoinColumn
	private Resource resource = new Resource();

	public Document() {
		resource.setType(ResourceType.getResourceType(this.getClass()));
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
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
		Document other = (Document) obj;
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
		return "Document [id=" + id + ", author=" + author + ", resource="
				+ resource + "]";
	}

	public JSONObject toJSON() {
		JSONObject object = new JSONObject();
		try {
			object.put("resource", this.getResource().toJSON());
			object.put("author", this.getAuthor());
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return object;
	}
}
