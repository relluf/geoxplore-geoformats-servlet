package nl.geoxplore.geoformats;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import nl.geoxplore.geoformats.Session;
import nl.geoxplore.geoformats.SessionListener;

public final class SessionListener
		implements ServletContextListener, HttpSessionAttributeListener, HttpSessionListener {
	
	private ServletContext context = null;

	public void attributeAdded(HttpSessionBindingEvent event) {
		log("attributeAdded('" + event.getSession().getId() + "', '" + event.getName() + "', '" + event.getValue()
				+ "')");
	}

	public void attributeRemoved(HttpSessionBindingEvent event) {
		log("attributeRemoved('" + event.getSession().getId() + "', '" + event.getName() + "', '" + event.getValue()
				+ "')");
	}

	public void attributeReplaced(HttpSessionBindingEvent event) {
		log("attributeReplaced('" + event.getSession().getId() + "', '" + event.getName() + "', '" + event.getValue()
				+ "')");
	}

	public void contextDestroyed(ServletContextEvent event) {
		log("contextDestroyed()");
		this.context = null;
	}

	public void contextInitialized(ServletContextEvent event) {
		this.context = event.getServletContext();
		log("contextInitialized()");
	}

	public void sessionCreated(HttpSessionEvent event) {
		log("sessionCreated('" + event.getSession().getId() + "')");
	}

	public void sessionDestroyed(HttpSessionEvent event) {
		log("sessionDestroyed('" + event.getSession().getId() + "')");

		Session sess = (Session) event.getSession().getAttribute(Session.class.getName());
		if (sess != null) {
			sess.finalize();
		}
	}

	private void log(String message) {
		System.out.println("SessionListener: " + message);
	}
}
