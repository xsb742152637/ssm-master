package util.quartz;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Comment：
 * Created by IntelliJ IDEA.
 * User: xiucai
 * Date: 2020/3/20 14:58
 */
public class MyQuartzTask {
    //触发器运行的任务3
    public static void doCronTaskFive(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("doCronTaskFive《30分/次》正在运行..."+sdf.format(new Date()));
    }
}
