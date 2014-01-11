package yuemu.po;

public enum ResourceType {
	DOCUMENT, IMAGE, MUSIC, VIDEO;

	public static ResourceType getResourceType(Class<?> clazz) {
		if (clazz == Document.class)
			return DOCUMENT;
		else if (clazz == Image.class)
			return IMAGE;
		else if (clazz == Music.class)
			return MUSIC;
		else if (clazz == Video.class)
			return VIDEO;
		else
			return null;
	}
}
