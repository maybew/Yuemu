package yuemu.po;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.json.JSONException;
import org.json.JSONObject;

import yuemu.core.ProjectException;

/**
 * 资源实体...被音频,视频,图片,文档等继承.
 * 
 * @author Administrator
 * 
 */
@Entity
public class Resource implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 资源编码
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * 标题
	 */
	private String title = "";

	/**
	 * 上传时间
	 */
	private Date uploadTime = new Date();

	/**
	 * 标签
	 */
	private String tag = "";

	/**
	 * 描述
	 */
	private String description = "";

	private String sourceUrl;

	private String previewUrl;

	private String snippetUrl;

	/**
	 * 状态
	 */
	private int status = ResourceStatus.USING;

	/**
	 * 上传者
	 */
	@ManyToOne(optional = false)
	@JoinColumn
	private Account uploader;

	/**
	 * 类型
	 */

	private ResourceType type;

	private String category;

	public Resource() {
		type = ResourceType.getResourceType(this.getClass());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Account getUploader() {
		return uploader;
	}

	public void setUploader(Account uploader) {
		this.uploader = uploader;
	}

	public String getSourceUrl() {
		return sourceUrl;
	}

	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}

	public String getPreviewUrl() {
		return previewUrl;
	}

	public void setPreviewUrl(String previewUrl) {
		this.previewUrl = previewUrl;
	}

	public String getSnippetUrl() {
		return snippetUrl;
	}

	public void setSnippetUrl(String snippetUrl) {
		this.snippetUrl = snippetUrl;
	}

	public ResourceType getType() {
		return type;
	}

	public void setType(ResourceType type) {
		this.type = type;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
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
		Resource other = (Resource) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Resource [id=" + id + ", title=" + title + ", uploadTime=" + uploadTime + ", tag=" + tag
				+ ", description=" + description + ", sourceUrl=" + sourceUrl + ", previewUrl=" + previewUrl
				+ ", snippetUrl=" + snippetUrl + ", status=" + status + ", uploader=" + uploader + ", type=" + type
				+ ", category=" + category + "]";
	}

	public String getFileExtension(String path) {
		int index = path.lastIndexOf('.');

		return path.substring(index);
	}

	public JSONObject toJSON() {
		JSONObject object = new JSONObject();
		try {
			object.put("id", this.getId());
			object.put("status", this.getStatus());
			object.put("uploader", this.getUploader().toJSON());
			object.put("type", this.getType());
			object.put("title", this.getTitle());
			object.put("tags", this.getTag());
			object.put("category", this.getCategory());
			object.put("description", this.getDescription());
			object.put("date", this.getUploadTime().toString().split("\\.")[0]);
			object.put("snippet_url", "url?action=snippet&id=" + this.getId() + "&ext=name"
					+ getFileExtension(getSnippetUrl()));
			object.put("source_url", "url?action=download&id=" + this.getId() + "&ext=name"
					+ getFileExtension(getSourceUrl()));
			object.put("preview_url", "url?action=preview&id=" + this.getId() + "&ext=name"
					+ getFileExtension(getPreviewUrl()));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return object;
	}

	public static Resource getResourceFromConcreteOne(Object obj) {
		try {
			Method m = obj.getClass().getMethod("getResource");
			Resource r = (Resource) m.invoke(obj);
			return r;
		} catch (Exception ex) {
			throw new ProjectException(ex);
		}
	}
}
