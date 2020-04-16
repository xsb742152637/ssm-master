package util.context;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;

import java.util.Properties;

@Component
public class ApplicationContext implements ApplicationContextAware {
    private static org.springframework.context.ApplicationContext currentApplicationContext = null;

    public ApplicationContext() {
    }

    public static org.springframework.context.ApplicationContext getCurrent() {
        if (currentApplicationContext == null) {
            System.out.println("applicationContext == null");
            currentApplicationContext = ContextLoader.getCurrentWebApplicationContext();
            if (currentApplicationContext == null) {
                currentApplicationContext = new ClassPathXmlApplicationContext("spring/spring-base.xml");
            }
        }

        return currentApplicationContext;
    }

    /**
    * 服务器启动，Spring容器初始化时，当加载了当前类为bean组件后，
    * 将会调用下面方法注入ApplicationContext实例
    */
    @Override
    public void setApplicationContext(org.springframework.context.ApplicationContext applicationContext) throws BeansException {
        System.out.println("初始化applicationContext");
        currentApplicationContext = applicationContext;
    }

    private static Properties properties = null;
    private static Properties getProperties(){
        if(properties == null){
            properties = getCurrent().getBean("applicationSettings",Properties.class);
        }
        return properties;
    }

    public static String get(String key) {
        return getProperties().getProperty(key);
    }

    public static String get(String key, String defaultValue) {
        return getProperties().getProperty(key, defaultValue);
    }


    /**
     * 外部调用这个getBean方法就可以手动获取到bean
     * 用bean组件的name来获取bean
     * @param beanName
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T>T getBean(String beanName) {
        return (T) currentApplicationContext.getBean(beanName);
    }

}
