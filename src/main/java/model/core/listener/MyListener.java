package model.core.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class MyListener implements ServletContextListener {
    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        System.out.println("\n\n容器关闭时执行\n\n");
    }
    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        System.out.println("\n\n容器启动时执行\n\n");
    }
}
