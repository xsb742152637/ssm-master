package util.context;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;

@Service("eiis.util.spring.applicationcontext")
public class ApplicationContext implements ApplicationContextAware {
    private static org.springframework.context.ApplicationContext currentApplicationContext = null;

    public ApplicationContext() {
    }

    public static org.springframework.context.ApplicationContext getCurrent() {
        if (currentApplicationContext == null) {
            currentApplicationContext = ContextLoader.getCurrentWebApplicationContext();
            if (currentApplicationContext == null) {
                currentApplicationContext = new ClassPathXmlApplicationContext("spring/spring-mvc.xml");
            }
        }

        return currentApplicationContext;
    }

    public void setApplicationContext(org.springframework.context.ApplicationContext applicationContext) throws BeansException {
        currentApplicationContext = applicationContext;
    }
}
