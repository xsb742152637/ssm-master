package util.datamanage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.CommonUtil;

import javax.persistence.Id;
import java.lang.reflect.Field;
import java.util.*;

public class GenericService<T>{
	public Logger logger = LoggerFactory.getLogger(this.getClass());

	public Map<String,Object> convertList(T obj){
		List<T> list = new ArrayList<>();
		list.add(obj);
		return convertList(list);
	}
	public Map<String,Object> convertList(List<T> list){
		String primaryKey = "";
		List<String> fields = new ArrayList<>();
		List<LinkedHashMap<String,Object>> reList = new ArrayList<>();
		List<Object> primaryIds = new ArrayList<>();
		try{
			if(list == null || list.size() < 1){
				throw new Exception("对象转换失败！");
			}
			int i = 0;
			for(Object obj : list){
				LinkedHashMap<String,Object> valMap = new LinkedHashMap<>();
				Class c = obj.getClass();
				for(Field field : c.getDeclaredFields()){
					//设置为可访问
					field.setAccessible(true);
					String name = humpToUnderline(field.getName().toString());
					Object val = field.get(obj);
					boolean isMain = false;

					//通过该字段的get方法判断其是否存在@Id注解
					if(c.getMethod(underlineToHump("GET_" + name)).isAnnotationPresent(Id.class)){
						primaryIds.add(val);
						isMain = true;
					}
					if(i == 0){
						if(isMain){
							primaryKey = name;
						}
						fields.add(name);
					}
					valMap.put(name,val);
				}
				reList.add(valMap);
				i++;
			}

		}catch (Exception e){
			e.printStackTrace();
		}

		Map<String,Object> map = new HashMap<>();
		//将驼峰命名的字段名转为下划线
		map.put("primaryKey", primaryKey);
		map.put("primaryIds", primaryIds);
		map.put("fields", fields);
		map.put("list", reList);
		return map;
	}

	public Map<String,Object> entityToMap(T obj){
		Map<String,Object> map = new HashMap<>();
		Class c = obj.getClass();
		try{
			for(Field field : c.getDeclaredFields()){
				//设置为可访问
				field.setAccessible(true);
				map.put(field.getName().toString(),field.get(obj));
			}
		}catch (Exception e){
			e.printStackTrace();
		}
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

	public String underlineToHump(String field){
		return CommonUtil.lineToHump(field);
	}
	//驼峰命名转为下划线命名,如：menuId > MENU_ID
	public String humpToUnderline(String field){
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
		return sb.toString().toUpperCase();
	}
}
