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
	
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    
    public static void main( String[] args )
    {
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

                log.info("Destroying Context...");

                try {
                    log.info("Calling MySQL AbandonedConnectionCleanupThread shutdown");
                    com.mysql.jdbc.AbandonedConnectionCleanupThread.shutdown();

                } catch (InterruptedException e) {
                    log.error("Error calling MySQL AbandonedConnectionCleanupThread shutdown {}", e);
                }

                ClassLoader cl = Thread.currentThread().getContextClassLoader();

                Enumeration<Driver> drivers = DriverManager.getDrivers();
                while (drivers.hasMoreElements()) {
                    Driver driver = drivers.nextElement();

                    if (driver.getClass().getClassLoader() == cl) {

                        try {
                            log.info("Deregistering JDBC driver {}", driver);
                            DriverManager.deregisterDriver(driver);

                        } catch (SQLException ex) {
                            log.error("Error deregistering JDBC driver {}", driver, ex);
                        }

                    } else {
                        log.trace("Not deregistering JDBC driver {} as it does not belong to this webapp's ClassLoader", driver);
                    }
                }
            }

        };
    }
}
