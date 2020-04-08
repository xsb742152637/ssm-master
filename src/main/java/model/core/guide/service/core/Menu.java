package model.core.guide.service.core;

import model.core.guide.GuideType;
import model.core.guide.service.impl.CoreGuideFileServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Menu extends Comm{
    public static final String PROJECT_ID = "caiDanTree";

    private static Member member = new Member();
    private Document context = null;
    private Element root = null;

    private String projectId = PROJECT_ID;
    public Menu (){
        removeCache();
        this.projectId = PROJECT_ID;
    }

    public Menu (String projectId,Document context,String contextStr){
        removeCache();
        if(StringUtils.isNotBlank(projectId)){
            this.projectId = projectId;
        }else if(context != null){
            setContext(context);
        }else if(StringUtils.isNotBlank(contextStr)){
            setContext(contextStr);
        }
    }

    //清空缓存
    public void removeCache(){
        member = new Member();
        this.context = null;
        this.root = null;

    }

    //得到成员对象
    public Document getContext(){
        if(this.context == null){
            setContext(CoreGuideFileServiceImpl.getInstance().findOne(this.projectId));
        }
        return this.context;
    }
    private void setContext(String contextStr){
        removeCache();
        this.context = getXml(contextStr);
    }
    private void setContext(Document context){
        removeCache();
        this.context = context;
    }

    //得到成员树根节点
    public Element getRoot(){
        if(this.root == null){
            this.root = getContext().getRootElement();
        }
        return this.root;
    }

    public List<Element> getMenuItems(String id){
        if(StringUtils.isBlank(id))
            return new ArrayList<>();
        return getContext().selectNodes("//" + MENU_TAG + "[@id='"+ id +"']");
    }
    public Element getMenuItem(String id){
        return getItem(getMenuItems(id));
    }

    //得到一个menu节点包含的所有mem节点
    public List<Element> findMemsByMenu(Element menu,String type){
        if(menu == null)
            return new ArrayList<>();
        return menu.selectNodes("./" + type);
    }
    public List<Element> findMemsByMenu(Element menu){
        if(menu == null)
            return new ArrayList<>();
        List<Element> val = new ArrayList<>();
        for(GuideType gt : GuideType.values()){
            val.addAll(findMemsByMenu(menu,gt.getCode().toString()));
        }
        return val;
    }

    public Element findMemByMenuAndMem(Element menu,Element mem,String type) {
        if(menu == null || mem == null)
            return null;
        return getItem(menu.selectNodes("./" + type + "[@id='" + getIdByItem(mem) + "']"));
    }
    //得到一个成员在整个xml文件中存在的权限
    public List<Element> findMems() {
        List<Element> val = new ArrayList<>();
        for (GuideType gt : GuideType.values()) {
            List<Element> val1 = getContext().selectNodes("//" + gt.getCode().toString());
            if (val1 != null) {
                val.addAll(val1);
            }
        }
        return val;
    }
    //得到一个成员在整个xml文件中存在的权限
    public List<Element> findMemsByMem(List<Element> mems) {
        List<Element> val = new ArrayList<>();
        if(mems != null && mems.size() > 0){
            for (GuideType gt : GuideType.values()) {
                String type = gt.getCode().toString();
                for(Element mem : mems){
                    List<Element> val1 = getContext().selectNodes("//" + type  + "[@id='" + getIdByItem(mem) + "']");
                    if (val1 != null) {
                        val.addAll(val1);
                    }
                }
            }
        }
        return val;
    }

    public List<Element> findMemsByMemAncestorAndSelf(List<Element> mems){
        List<Element> val = new ArrayList<>();
        if(mems != null && mems.size() > 0){
            for(Element mem : mems) {
                val.addAll(findMemsByMem(getAncestorAndSelf(mem)));
            }
        }
        return val;
    }

    public List<Element> findMemsByMenuAndMems(Element menu,List<Element> mems,String type) {
        List<Element> val = new ArrayList<>();
        if(menu != null && mems != null && mems.size() > 0){
            for (Element mem : mems) {
                Element memItem = findMemByMenuAndMem(menu, mem,type);
                if (memItem != null) {
                    val.add(memItem);
                }
            }
        }
        return val;
    }

    //得到一个menu节点包含的某个mem节点所有的父节点
    public List<Element> findMemAncestorByMenuAndMem(Element menu,Element mem,String type) {
        if(menu == null || mem == null)
            return new ArrayList<>();
        return findMemsByMenuAndMems(menu, member.getAncestor(mem),type);
    }
    //得到一个menu节点包含的某个mem节点所有的父节点（含自己）
    public List<Element> findMemAncestorAndSelfByMenuAndMem(Element menu,Element mem,String type) {
        if(menu == null || mem == null)
            return new ArrayList<>();
        return findMemsByMenuAndMems(menu, member.getAncestorAndSelf(mem),type);
    }

    public List<Element> findMemDescendantByMenuAndMem(Element menu,Element mem,String type) {
        if(menu == null || mem == null)
            return new ArrayList<>();
        return findMemsByMenuAndMems(menu, getDescendant(mem,MEMBER_TAG),type);
    }

    public List<Element> findMemDescendantAndSelfByMenuAndMem(Element menu,Element mem,String type) {
        if(menu == null || mem == null)
            return new ArrayList<>();
        return findMemsByMenuAndMems(menu, getDescendantAndSelf(mem,MEMBER_TAG),type);
    }

    //根据mem及其上级找到其所有存在的menu
    public List<Element> findMenusByMemAncestorAndSelf(Element mem,String type){
        if(mem == null)
            return new ArrayList<>();
        List<Element> val = new ArrayList<>();
        List<Element> mems = getAncestorAndSelf(mem);
        for (Element m : mems) {
            List<Element> menuItem = getContext().selectNodes("//" + type + "[@id='" + getIdByItem(m) + "']//parent::" + MENU_TAG);
            if (menuItem != null) {
                val.addAll(menuItem);
            }
        }
        return val;
    }

    //将一个mem节点加入menu节点中,加入之前先检查是否存在
    public void addMem(Element menu,Element mem,String type) {
        if(menu == null || mem == null)
            return;
        boolean isHave = true;
        for(Element pMenu : getAncestorAndSelf(menu)){
            //查找本级是否已经有mem或其上级，没有的话才增加
            List<Element> result = findMemAncestorAndSelfByMenuAndMem(pMenu, mem,type);
            if (getLength(result) > 0) {
                isHave = false;
                break;
            }
        }

        if (isHave) {
            removeMemDescendant(menu,mem,type);

            Element newMem = menu.addElement(type);
            newMem.addAttribute("id",mem.attributeValue("id").toString());
            newMem.addAttribute("n",mem.attributeValue("n").toString());
            newMem.addAttribute("ut",sdf.format(new Date()));

            setContext(menu.getDocument());
        }
    }
    //将一组mem节点加入menu节点中,加入之前先检查是否存在
    public void addMem(Element menu,List<Element> memItems,String type) {
        if(menu == null || memItems == null || memItems.size() < 1)
            return;
        for (Element mem : memItems) {
            addMem(menu, mem,type);
        }
    }

    //将一个mem节点从menu节点中移除
    public void removeMem(Element menu,Element mem,String type) {
        if(menu == null || mem == null)
            return;
        Element memItem = findMemByMenuAndMem(menu, mem,type);
        if (memItem != null) {
            menu.remove(memItem);
            setContext(menu.getDocument());
        }
    }
    //将一个mem节点从menu节点中移除
    public void removeMems(Element menu,List<Element> mems) {
        if (mems != null) {
            for(Element mem : mems){
                menu.remove(mem);
            }
            setContext(menu.getDocument());
        }
    }

    //将一个menu节点中的mem下级节点删除
    public void removeMemDescendant(Element menu,Element mem,String type) {
        if(menu == null || mem == null)
            return;
        //得到mem节点的所有下级
        List<Element> mems = findMemDescendantByMenuAndMem(menu, mem,type);
        for (int i = getLength(mems) - 1; i >= 0; i--) {
            removeMem(menu, getItem(mems, i),type);
        }
        setContext(menu.getDocument());
    }

    //将一个menu节点中的mem下级及自己节点删除
    public void removeMemDescendantAndSelf(Element menu,Element mem,String type) {
        if(menu == null || mem == null)
            return;
        removeMem(menu, mem,type);
        //得到mem节点的所有下级
        List<Element> mems = findMemDescendantAndSelfByMenuAndMem(menu, mem,type);
        for (int i = getLength(mems) - 1; i >= 0; i--) {
            removeMem(menu, getItem(mems, i),type);
        }
    }
}
