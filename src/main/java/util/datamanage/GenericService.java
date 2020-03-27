package util.datamanage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.CommonUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenericService{
	public Logger logger = LoggerFactory.getLogger(this.getClass());

	//将list中map的key按照驼峰命名法进行转换,如:MENU_ID > menuId
	public static List<Map<String,Object>> convertList(List<Map<String,Object>> list){
		List<Map<String,Object>> listNew = new ArrayList<>();
		for(Map<String,Object> map : list){
			Map<String,Object> mapNew = new HashMap<>();
			for(Map.Entry<String,Object> m : map.entrySet()){
				mapNew.put(CommonUtil.lineToHump(m.getKey()),m.getValue() == null ? "" : m.getValue());
			}
			listNew.add(mapNew);
		}
		return listNew;
	}
}
