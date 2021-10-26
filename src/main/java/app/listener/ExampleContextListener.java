package app.listener;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;

import app.jobs.JobScheduler;
import app.jobs.SimpleFilePruneJob;

/**
 * Application Lifecycle Listener implementation class ExampleContextListener
 *
 */
@WebListener
public class ExampleContextListener implements ServletContextListener {
	
	 private final static String className = ExampleContextListener.class.getSimpleName();
	 private static Logger logger = LogManager.getLogger(ExampleContextListener.class);
	
    /**
     * Default constructor. 
     */
    public ExampleContextListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent sce)  { 
         
    	String methodName = className + ".contextDestroyed()";

    	try {
        	logger.info(String.format("%1$s: >>>>>> pausing all jobs in context...", methodName));
    		Scheduler sch = JobScheduler.getSchedulerFactory().getScheduler();
    		sch.pauseAll();
    	} catch (SchedulerException e) {
    		logger.error(String.format("%1$s: error pausing jobs.", methodName), e);
    	}

    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent sce)  { 

    	String methodName = className + ".contextInitialized()";
    	
    	// Recogemos parámetros de web.xml
    	Map<String, String> parameters = new HashMap<String, String>();
    	parameters.put(SimpleFilePruneJob.PARAM_FILENAME_FROM_WEB, sce.getServletContext().getInitParameter(SimpleFilePruneJob.PARAM_FILENAME_FROM_WEB));

    	try {
        	logger.info(String.format("%1$s: >>>>>> scheduling in context...", methodName));
    		//JobScheduler.scheduleJob(SimpleFilePruneJob.class);
        	JobScheduler.scheduleJob(SimpleFilePruneJob.class, parameters);
    	} catch (SchedulerException e) {
    		logger.error(String.format("%1$s: error scheduling job %2$s.", methodName, SimpleFilePruneJob.class.getSimpleName()), e);
    	}
    	
    }
	
}
