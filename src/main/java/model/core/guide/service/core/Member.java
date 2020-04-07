package model.core.guide.service.core;

import model.core.guide.service.impl.CoreGuideFileServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

public class Member extends Comm{
    public static final String PROJECT_ID = "memberTree";
    public static final String DEFAULT_ADMIN = "d456a11c-3028-46b6-8433-168b98b20c7a"; //默认管理员职务ID

    private static Document context = null;
    private static Element root = null;

    public Member(){

    }
    //清空缓存
    public void removeCache(){
        this.context = null;
        this.root = null;
    }

    //得到成员对象
    public Document getContext(){
        if(this.context == null){
            this.context = getXml(CoreGuideFileServiceImpl.getInstance().findOne(PROJECT_ID));
        }
        return this.context;
    }
    //得到成员树根节点
    public Element getRoot(){
        if(this.root == null){
            this.root = getContext().getRootElement();
        }
        return this.root;
    }
    //根据ID得到成员对象
    public Element getMemItem(String id){
        return getItem(getMemItems(id));
    }
    public List<Element> getMemItems(String id){
        if(StringUtils.isBlank(id))
            return new ArrayList<>();
        return getContext().selectNodes("//" + Comm.MEMBER_TAG + "[starts-with(@id,'"+ id +"')]");
    }

    //得到一个节点的所有兄弟节点
    public List<Element> getDescendantPerson(Element e){
        return e.selectNodes(".//" + Comm.MEMBER_TAG + "[@p='1']");
    }

    //根据成员ID判断是否为人
    public Boolean isPerson(String memberId){
        Boolean isPerson = false;
        List<Element> list = getMemItems(memberId);
        for(Element mem : list){
            String p = mem.attributeValue("p").toString();
            isPerson = StringUtils.isNotBlank(p) && Integer.parseInt(p) == 1;
            if(isPerson){
                break;
            }
        }
        return isPerson;
    }
    //从mem1中剥离出mem2
    public List<Element> stripping(Element e1,Element e2) {
        List<Element> memItems = new ArrayList<>();
        //将menu树中的mem装换成mem树中的mem
        e1 = getMemItem(getIdByItem(e1));
        if (contains(e1, e2,Comm.MEMBER_TAG)) {
            //得到mem1下一级节点
            List<Element> memChildren = getChildren(e1,Comm.MEMBER_TAG);
            for (Element oc : memChildren) {
                if (!equals(oc, e2)) {
                    List<Element> subOrgItems = stripping(oc, e2);
                    memItems.addAll(subOrgItems);
                }
            }
        }else {
            memItems.add(e1);
        }
        return memItems;
    }
}
