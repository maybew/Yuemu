package yuemu.web;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpSession;


public class SessionSet {

	private final static Set<HttpSession> sessionSet = new 
			HashSet<HttpSession>();
	
	public synchronized static void addSession(HttpSession session) {
		sessionSet.add(session);
	}
	
	public synchronized static void removeSession(HttpSession session) {
		sessionSet.remove(session);
	}
	
	public synchronized static void emptySessions() {
		Set<HttpSession> temp = new HashSet<HttpSession>(sessionSet);
		sessionSet.clear();
		for(HttpSession session: temp)
			session.invalidate();
	}
	
}
