package yuemu.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import yuemu.configure.Configure;
import yuemu.services.DatabaseService;
import yuemu.services.LogService;

@WebListener
public class GlobalListener implements ServletContextListener {
	
	public GlobalListener() {

	}

	public void contextInitialized(ServletContextEvent arg0) {
		Configure.ROOTPATH = arg0.getServletContext().getRealPath("");
		DatabaseService.insertRootAdministrator();
		LogService.log("系统开启","系统");
	}

	public void contextDestroyed(ServletContextEvent arg0) {
		
		LogService.log("系统关闭", "系统");

	}

}
