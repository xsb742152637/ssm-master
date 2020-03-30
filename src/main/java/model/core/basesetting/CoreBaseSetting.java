package model.core.basesetting;

import org.apache.commons.lang3.StringUtils;
import util.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
public class CoreBaseSetting {

    private static List<String> list = new ArrayList<>();
    public static void removeBaseSetting(){ list = null; }
    public static String getBaseSetting(){
        if(list == null || list.size() == 0){
            list.add(ApplicationContext.get("butSize"));
            list.add(ApplicationContext.get("butColor"));
            list.add(ApplicationContext.get("radius"));
        }
        return StringUtils.join(list," ");
    }
}
