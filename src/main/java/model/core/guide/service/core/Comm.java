package model.core.guide.service.core;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Set;

/*
selectNodes中"."与"/"的使用说明
    ".//" 表示以selectNodes前的这个节点为根节点进行全文查询
    "//" 表示在整个xml节点中全文查询
    "./" 表示只在selectNodes前的这个节点的子级中查询
 */
public class Comm {
    public static final String MEMBER_TAG = "m";//xml中成员树的标签名称
    public static final String MENU_TAG = "a";//xml中菜单树的标签名称
    public static final SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");

    //从字符串中读取xml
    public Document getXml(String x){
        try {
            Document d= DocumentHelper.parseText(x);
            return d;
        }catch (Exception e){
            System.out.println("xml解析出错："+e.getMessage());
        }
        return null;
    }

    //根据节点得到属性
    public String getIdByItem(Element e){
        return e.attributeValue("id").toString();
    }
    public String getNameByItem(Element e){
        if(e != null)
            return e.attributeValue("n").toString();
        return "";
    }

    //得到上一级的节点
    public Element getParent(Element e){
        return getItem(e.selectNodes("parent::*"));
    }
    //得到所有父节点
    public List<Element> getAncestor(Element e){
        return e.selectNodes("ancestor::*");
    }
    //得到所有父节点以及自己
    public List<Element> getAncestorAndSelf(Element e){
        return e.selectNodes("ancestor-or-self::*");
    }

    //得到下一级的节点
    public List<Element> getChildren(Element e,String tag){
        return e.selectNodes("child::" + tag);
    }
    //得到所有子节点
    public List<Element> getDescendant(Element e,String tag){
        return e.selectNodes("descendant::" + tag);
    }
    //得到所有子节点以及自己
    public List<Element> getDescendantAndSelf(Element e,String tag){
        return e.selectNodes("descendant-or-self::" + tag);
    }
    //得到一个节点的所有兄弟节点
    public List<Element> getBrothers(Element e,String tag){
        return e.selectNodes("preceding-sibling::" + tag + " | following-sibling::" + tag);
    }

    //判断一个e1节点是否包含e2节点
    public Boolean contains(Element e1,Element e2,String tag){
        if(equals(e1,e2))
            return true;
        return !isNull(e1.selectNodes(".//" + tag + "[@id='" + getIdByItem(e2) + "']"));
    }
    //判断两个节点是否相等
    public Boolean equals(Element e1,Element e2){
        return getIdByItem(e1).equalsIgnoreCase(getIdByItem(e2));
    }
    //判断一个节点集合是否为空
    public Boolean isNull(List<Element> li){
        return li == null || li.size() < 1;
    }
    //判断一个节点是否为空
    public Boolean isNull(Element e){
        return e == null;
    }
    //得到一个节点集合的长度
    public int getLength(List<Element> li){
        return isNull(li) ? 0 : li.size();
    }
    //得到一个集合的某个节点
    public Element getItem(List<Element> li,int i){
        if(li == null || li.size() <= i)
            return null;
        return li.get(i);
    }
    //得到一个集合中的第一个节点
    public Element getItem(List<Element> li){
        return getItem(li,0);
    }

    //判断两个Set是否相等
    public boolean isSetEqual(Set set1, Set set2){
        if(set1 == null || set2 == null || set1.size() != set2.size())
            return false;
        //Set会自动去重，如果不相等，set1等长度会变。
        set1.addAll(set2);
        if(set1.size() != set2.size())
            return false;

        return true;
    }
}
