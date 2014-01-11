package yuemu.dao.statistics;

public class ResourceStatistics {

	public Long resourceId;
	
	public long count;
	
	public ResourceStatistics() {
	}
	
	public ResourceStatistics(Long resourceId, long count) {
		this.resourceId = resourceId;
		this.count = count;
	}

	public String toString() {
		return "ResourceStatistics [resourceId=" + resourceId + ", count="
				+ count + "]";
	}
	
}
