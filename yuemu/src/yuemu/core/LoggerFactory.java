package yuemu.core;

import java.util.logging.Logger;

public class LoggerFactory {
	
	public static Logger getProjectLogger() {
		return Logger.getLogger("yuemu.main");
	}
	
	public static Logger getDebugLogger() {
		return Logger.getLogger("yuemu.debug");
	}
	
}
