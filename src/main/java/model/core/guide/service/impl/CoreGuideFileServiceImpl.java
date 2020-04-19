package model.core.guide.service.impl;

import model.core.guide.dao.CoreGuideFileDao;
import model.core.guide.entity.CoreGuideFileEntity;
import model.core.guide.service.CoreGuideFileService;
import model.core.guide.service.core.Member;
import model.core.guide.service.core.Menu;
import model.core.guide.service.core.MenuEx;
import model.core.memberinfo.MemberType;
import model.core.menutree.service.CoreMenuTreeService;
import model.core.treeinfo.TreeType;
import model.core.treeinfo.service.CoreTreeInfoService;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.context.ApplicationContext;
import util.datamanage.GenericService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class CoreGuideFileServiceImpl extends GenericService<CoreGuideFileEntity> implements CoreGuideFileService {
    public static Boolean IS_UPDATE = false;//如果成员或菜单有更新，则需要用户刷新新版授权页面
    protected Member memberEntity = new Member();

    @Autowired
    private CoreGuideFileDao dao;
    @Lazy
    @Autowired
    private CoreTreeInfoService treeService;
    @Lazy
    @Autowired
    private CoreMenuTreeService menuTreeService;
    /**
     * 获取实例
     */
    public static CoreGuideFileServiceImpl getInstance() {
        return ApplicationContext.getCurrent().getBean(CoreGuideFileServiceImpl.class);
    }

    @Override
    public String findOne(String projectId){
        String str = "";
        CoreGuideFileEntity en = dao.findOne(projectId);
        if(en != null){
            str = en.getDocument().toString();
        }
        if(StringUtils.isBlank(str)){
            try{
                if(projectId.equals(Member.PROJECT_ID)){
                    str = createMemberXml("系统",null,null);
                }else if(projectId.equals(Menu.PROJECT_ID)){
                    str = createMenuXml();
                }else{
                    str = createProjectXml(projectId);
                }
            }catch (Exception e){
                System.out.println(e);
                e.printStackTrace();
            }
        }
        return str;
    }

    @Override
    public List<CoreGuideFileEntity> findAll(){
        return dao.findAll();
    }
    @Override
    public int insert(CoreGuideFileEntity entity){
        new MenuEx().removeCache();
        return dao.insert(convertList(entity));
    }

    @Override
    @Transactional
    public CoreGuideFileEntity insert(String projectId, String str) {// 保存
        CoreGuideFileEntity en = new CoreGuideFileEntity();
        en.setGuideId(projectId);
        en.setDocument(str);

        insert(en);
        return en;
    }
    @Override
    @Transactional
    public CoreGuideFileEntity insert(String projectId, Document d) {// 保存
        return insert(projectId,d.asXML());
    }

    @Override
    @Transactional
    public CoreGuideFileEntity update(String projectId, String str) {// 保存
        CoreGuideFileEntity en = new CoreGuideFileEntity();
        en.setGuideId(projectId);
        en.setDocument(str);

        update(en);
        return en;
    }
    @Override
    @Transactional
    public int update(CoreGuideFileEntity entity) {
        new MenuEx().removeCache();
        return dao.update(convertList(entity));
    }
    @Override
    @Transactional
    public int update(List<CoreGuideFileEntity> list) {
        return dao.update(convertList(list));
    }

    @Override
    @Transactional
    public int delete(String primaryId) {
        return dao.delete(primaryId);
    }

    @Override
    @Transactional
    public int deleteAll() {
        return dao.deleteAll();
    }


    @Override
    public String createMemberXml(String sourceType,String memberId,String memberName){
        Document _document = DocumentHelper.createDocument();
        if(_document == null){
            return "";
        }

        //得到成员树
        List<Map<String,Object>> list = treeService.getMainInfo(String.valueOf(TreeType.MemberInfo.getCode()));
        addMemEle(_document,null,list);

        delete(Member.PROJECT_ID);
        insert(Member.PROJECT_ID,_document);
        memberEntity.removeCache();

        String eventData = "sourceType：" + sourceType + "；memberId：" + memberId + "；memberName：" + memberName ;
        System.out.println(eventData);
        return _document.asXML().toString();
    }

    public String createMenuXml(){
        Document _document = DocumentHelper.createDocument();
        if(_document == null){
            return "";
        }

        List<Map<String,Object>> list = menuTreeService.getMenuTree(false,true,null);
        addMenuEle(_document,null,list);

        delete(Menu.PROJECT_ID);
        insert(Menu.PROJECT_ID,_document);
        System.out.println("创建菜单树");
        return _document.asXML().toString();
    }

    public String createProjectXml(String projectId){
        String f = findOne(Menu.PROJECT_ID);
        if(StringUtils.isBlank(f)){
            f = createMenuXml();
        }
        Menu menuEntity = new Menu(null,null,f);

        Document _document = DocumentHelper.createDocument();
        if(_document == null){
            return "";
        }

        //创建跟节点
        Element root = _document.addElement(Menu.MENU_TAG);
        root.setAttributes(menuEntity.getRoot().attributes());
        List<Element> list = menuEntity.getRoot().elements();
        for(Element el : list){
            Element e = root.addElement(Menu.MENU_TAG);
            e.setAttributes(el.attributes());
            addProEle(e,el.elements());
        }

        delete(projectId);
        insert(projectId,_document);
        System.out.println("创建项目树");
        return _document.asXML().toString();
    }

    private void addMemEle(Document doc,Element parEle,List<Map<String,Object>> list){
        for(Map<String,Object> map : list) {
            Element newEle = null;
            if(parEle == null){
                newEle = doc.addElement(Member.MEMBER_TAG);
            }else{
                newEle = parEle.addElement(Member.MEMBER_TAG);
            }
            if(map.get("memberId") == null || StringUtils.isBlank(map.get("memberId").toString())){
                System.out.println("没有找到成员：" + map.get("treeName").toString());
                continue;
            }
            newEle.addAttribute("id",map.get("memberId").toString() + (String.valueOf(MemberType.Person.getCode()).equals(map.get("memberType").toString()) ? (";" + map.get("parentId").toString()) : ""));
            newEle.addAttribute("n",map.get("memberName").toString());
            newEle.addAttribute("p",map.get("memberType").toString());//是person
            if(map.get("children") != null){
                addMemEle(doc,newEle,(List<Map<String,Object>>) map.get("children"));
            }
        }
    }

    private void addMenuEle(Document doc,Element parEle,List<Map<String,Object>> list){
        for(Map<String,Object> map : list) {
            Element newEle = null;
            if(parEle == null){
                newEle = doc.addElement(Member.MENU_TAG);
            }else{
                newEle = parEle.addElement(Member.MENU_TAG);
            }
            newEle.addAttribute("id",map.get("id").toString());
            newEle.addAttribute("n",map.get("title").toString());
            if(map.get("children") != null){
                addMenuEle(doc,newEle,(List<Map<String,Object>>) map.get("children"));
            }
        }
    }

    private void addProEle(Element p,List<Element> list){
        if(list == null || list.size() < 1){
            return;
        }
        for(Element el : list){
            Element e = p.addElement(Menu.MENU_TAG);
            e.setAttributes(el.attributes());
            addProEle(e,el.elements());
        }
    }

    @Override
    @Transactional
    public void addMenu(String parId,String menuId,String menuName){
        IS_UPDATE = true;
        List<CoreGuideFileEntity> list = dao.findAll();
        List<CoreGuideFileEntity> listU = new ArrayList<>();
        for(CoreGuideFileEntity entity : list){
            if(Member.PROJECT_ID.equalsIgnoreCase(entity.getGuideId()) ){
                continue;
            }
            Menu menuEntity = new Menu(null,null,entity.getDocument());
            List<Element> listM = menuEntity.getMenuItems(menuId);
            if(listM.size() > 0) {
                for(Element e : listM){
                    e.addAttribute("n",menuName);
                    e.addAttribute("ut",Menu.sdf.format(new Date()));
                }
            }else {
                //d1794973-0b31-47a5-a24e-a9ea52c3dedc
                //d80e8493-c97e-4c51-83df-c4b143d66c5f
                listM = menuEntity.getMenuItems(parId);
                for (Element e : listM) {
                    Element m = e.addElement(Menu.MENU_TAG);
                    m.addAttribute("id", menuId);
                    m.addAttribute("n", menuName);
                    m.addAttribute("ut",Menu.sdf.format(new Date()));
                }
            }
            entity.setDocument(menuEntity.getContext().asXML());
            listU.add(entity);
        }
        update(listU);

        System.out.println("调整菜单" + "parId："+parId+"，menuId："+menuId+"，menuName："+menuName);
    }

    @Override
    @Transactional
    public void deleteMenu(String menuId){
        IS_UPDATE = true;
        String menuName = "";
        List<CoreGuideFileEntity> list = dao.findAll();
        List<CoreGuideFileEntity> listU = new ArrayList<>();
        for(CoreGuideFileEntity entity : list){
            if(Member.PROJECT_ID.equalsIgnoreCase(entity.getGuideId()) || Menu.PROJECT_ID.equalsIgnoreCase(entity.getGuideId()) ){
                continue;
            }
            Menu menuEntity = new Menu(null,null,entity.getDocument());
            List<Element> listM = menuEntity.getMenuItems(menuId);
            if(listM.size() > 0) {
                for(Element e : listM){
                    if(StringUtils.isBlank(menuName)){
                        menuName = e.attributeValue("n");
                    }
                    Element ep = e.getParent();
                    if(ep == null){
                        continue;
                    }
                    //删除菜单
                    ep.remove(e);
                }
            }
            entity.setDocument(menuEntity.getContext().asXML());
            listU.add(entity);
        }
        update(listU);
        //删除菜单模板文件
        delete(Menu.PROJECT_ID);
        //新增菜单模板文件
        createMenuXml();
        System.out.println("删除菜单" + "menuId："+menuId+"，menuName："+menuName);
    }
}
