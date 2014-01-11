package yuemu.core;

import java.lang.reflect.Method;
import java.util.Collection;

import org.json.JSONArray;
import org.json.JSONObject;

public class JSONUtils {
	
	/**返回一个对象的JSON表示， 其中需要该对象有toJSON方法。 
	 * 
	 * @param obj
	 * @return
	 */
	public static JSONObject toJSON(Object obj) {
		try {
			Method m = obj.getClass().getMethod("toJSON");
			Object value = m.invoke(obj);
			return (JSONObject)value;
		} catch (Exception ex) {
			throw new ProjectException(ex);
		}
	}
	
	/**返回一个集合的JSON数组， 其中需要每个元素需要有方法toJSON。
	 * 
	 * @param c
	 * @return
	 */
	public static JSONArray toJSONArray(Collection<?> c) {
		JSONArray array = new JSONArray();
		for(Object obj: c) {
			array.put(toJSON(obj));
		}
		return array;
	}
	
}
