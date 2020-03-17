package util.message;

import java.awt.*;

/**
 * 向windows系统发送通知
 */
public class WindowsMessageUtil {
    public static void sendMessage(String title,String context) throws Exception {
        //获取系统托盘对象
        SystemTray tray = SystemTray.getSystemTray();
        //为了不显示这个托盘，选择创建一个空图标
        Image image = Toolkit.getDefaultToolkit().createImage("aa.png");
        //创建一个无名称无图片的托盘
        TrayIcon trayIcon = new TrayIcon(image, "");
        //在系统托盘区域添加一个应用的托盘
        tray.add(trayIcon);
        //通过这个托盘给系统发送一条消息。
        //如果没有收到通知，请检查是否打开了通知功能。或把tray.remove(trayIcon)删除试试
        trayIcon.displayMessage(title, context, TrayIcon.MessageType.NONE);
        //从系统托盘区域删除这个托盘
        tray.remove(trayIcon);
    }
}
