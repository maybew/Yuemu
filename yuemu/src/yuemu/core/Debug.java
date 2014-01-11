package yuemu.core;

import java.util.Collection;

public class Debug {
	
	public static String collectionToString(Collection<?> set) {
		if(set == null) 
			System.out.println("Empty set!");
		StringBuilder strBuilder = new StringBuilder(
				set.getClass().toString() + "\n");
		for(Object obj: set) {
			strBuilder.append("\t");
			strBuilder.append(obj.toString());
			strBuilder.append("\n");
		}
		
		return strBuilder.toString();
		
	}
	
	public static void showCollection(Collection<?> set) {
		System.out.println(collectionToString(set));
	}
	
}
