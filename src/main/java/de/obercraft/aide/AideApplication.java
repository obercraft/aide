package de.obercraft.aide;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class AideApplication
{
	
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
              
    public static void main( String[] args ) {    	    
 
    	SpringApplication.run(AideApplication.class, args);
    }
        
        
    @Bean
    protected ServletContextListener listener() {

        return new ServletContextListener() {

            @Override
            public void contextInitialized(ServletContextEvent sce) {
                
            }

            @Override
            public final void contextDestroyed(ServletContextEvent sce) {

                logger.info("Destroying Context...");

                try {
                    logger.info("Calling MySQL AbandonedConnectionCleanupThread shutdown");
                    com.mysql.jdbc.AbandonedConnectionCleanupThread.shutdown();

                } catch (InterruptedException e) {
                    logger.error("Error calling MySQL AbandonedConnectionCleanupThread shutdown {}", e);
                }

                ClassLoader cl = Thread.currentThread().getContextClassLoader();

                Enumeration<Driver> drivers = DriverManager.getDrivers();
                while (drivers.hasMoreElements()) {
                    Driver driver = drivers.nextElement();

                    if (driver.getClass().getClassLoader() == cl) {

                        try {
                            logger.info("Deregistering JDBC driver {}", driver);
                            DriverManager.deregisterDriver(driver);

                        } catch (SQLException ex) {
                            logger.error("Error deregistering JDBC driver {}", driver, ex);
                        }

                    } else {
                        logger.trace("Not deregistering JDBC driver {} as it does not belong to this webapp's ClassLoader", driver);
                    }
                }
            }

        };
    }
}
