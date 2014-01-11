package yuemu.listeners;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionActivationListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import yuemu.web.SessionSet;

/**
 * Application Lifecycle Listener implementation class SessionListener
 *
 */
@WebListener
public class SessionListener implements HttpSessionListener, HttpSessionActivationListener {

    /**
     * Default constructor. 
     */
    public SessionListener() {}

	/**
     * @see HttpSessionActivationListener#sessionDidActivate(HttpSessionEvent)
     */
    public void sessionDidActivate(HttpSessionEvent e) {
        SessionSet.addSession(e.getSession());
        System.out.println("活化Session: " + e.getSession().getId());
    }

	/**
     * @see HttpSessionActivationListener#sessionWillPassivate(HttpSessionEvent)
     */
    public void sessionWillPassivate(HttpSessionEvent e) {
        SessionSet.removeSession(e.getSession());
        System.out.println("钝化Session: " + e.getSession().getId());
    }

	/**
     * @see HttpSessionListener#sessionCreated(HttpSessionEvent)
     */
    public void sessionCreated(HttpSessionEvent e) {
        SessionSet.addSession(e.getSession());
        System.out.println("创建Session: " + e.getSession().getId());
    }

	/**
     * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
     */
    public void sessionDestroyed(HttpSessionEvent e) {
        SessionSet.removeSession(e.getSession());
        System.out.println("删除Session: " + e.getSession().getId());
    }
	
}
