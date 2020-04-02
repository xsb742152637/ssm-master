package util.datamanage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.CommonUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenericService<T>{
	public Logger logger = LoggerFactory.getLogger(this.getClass());

	public Map<String,Object> convertList(List<T> list){
		List<String> fields = new ArrayList<>();
		List<List<Object>> reList = new ArrayList<>();
		try{
			if(list == null || list.size() < 1){
				throw new Exception("对象转换失败！");
			}
			int i = 0;
			for(Object obj : list){
				List<Object> valList = new ArrayList<>();
				for(Field field : obj.getClass().getDeclaredFields()){
					if(i == 0){
						fields.add(field.getName().toString());
					}
					//设置为可访问
					field.setAccessible(true);
					valList.add(field.get(obj));
				}
				reList.add(valList);
				i++;
			}

		}catch (Exception e){
			e.printStackTrace();
		}

		Map<String,Object> map = new HashMap<>();
		//将驼峰命名的字段名转为下划线
		map.put("fields", humpToUnderline(fields));
		map.put("list", reList);
		return map;
	}


	//将list中map的key按照驼峰命名法进行转换,如:MENU_ID > menuId
	public List<Map<String,Object>> underlineToHump(List<Map<String,Object>> list){
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

	//驼峰命名转为下划线命名,如：menuId > MENU_ID
	public List<String> humpToUnderline(List<String> fields){
		List<String> list = new ArrayList<>();
		for(String field : fields){
			StringBuilder sb=new StringBuilder(field);
			int temp=0;//定位
			if (!field.contains("_")) {
				for(int i=0;i<field.length();i++){
					if(Character.isUpperCase(field.charAt(i))){
						sb.insert(i+temp, "_");
						temp+=1;
					}
				}
			}
			list.add(sb.toString().toUpperCase());
		}
		return list;
	}
}
