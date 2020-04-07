package model.core.guide.service.impl;

import model.core.guide.dao.CoreGuideFileDao;
import model.core.guide.entity.CoreGuideFileEntity;
import model.core.guide.service.CoreGuideFileService;
import model.core.guide.service.core.Member;
import model.core.guide.service.core.Menu;
import model.core.memberinfo.entity.CoreMemberInfoEntity;
import model.core.memberinfo.service.CoreMemberInfoService;
import model.core.menutree.service.CoreMenuTreeService;
import model.core.treeinfo.TreeType;
import model.core.treeinfo.service.CoreTreeInfoService;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.context.ApplicationContext;
import util.datamanage.GenericService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CoreGuideFileServiceImpl extends GenericService<CoreGuideFileEntity> implements CoreGuideFileService {
    public static Boolean IS_UPDATE = false;//如果成员或菜单有更新，则需要用户刷新新版授权页面
    protected Member memberEntity = new Member();

    @Autowired
    private CoreGuideFileDao dao;
    @Autowired
    private CoreTreeInfoService treeService;
    @Autowired
    private CoreMemberInfoService memberService;
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
        if(en != null)
            str = en.getDocument().toString();
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
        return dao.insert(convertList(entity));
    }

    @Override
    public CoreGuideFileEntity insert(String projectId, String str) {// 保存
        CoreGuideFileEntity en = new CoreGuideFileEntity();
        en.setGuideId(projectId);
        en.setDocument(str);

        insert(en);
        return en;
    }
    @Override
    public CoreGuideFileEntity insert(String projectId, Document d) {// 保存
        return insert(projectId,d.asXML());
    }

    @Override
    public int update(CoreGuideFileEntity entity) {
        return dao.update(convertList(entity));
    }

    @Override
    public int delete(String mainId) {
        return dao.delete(mainId);
    }

    @Override
    public int deleteAll() {
        return dao.deleteAll();
    }


    public String createMemberXml(String sourceType,String memberId,String memberName)throws Exception{
        Document _document = DocumentHelper.createDocument();
        if(_document == null)
            return "";

        //得到全部成员
        List<CoreMemberInfoEntity> memList= memberService.findAll();
        Map<String,CoreMemberInfoEntity> memMap = new HashMap<>();
        for(CoreMemberInfoEntity entity : memList){
            memMap.put(entity.getTreeId(),entity);
        }

        //得到成员树
        List<Map<String,Object>> list = treeService.getMainInfo(String.valueOf(TreeType.MemberInfo.getCode()),null);
        addMemEle(_document,null,list,memMap);

        insert(Member.PROJECT_ID,_document);
        memberEntity.removeCache();

        String eventData = "sourceType：" + sourceType + "；memberId：" + memberId + "；memberName：" + memberName ;
        System.out.println(eventData);
        return _document.asXML().toString();
    }

    public String createMenuXml()throws Exception{
        Document _document = DocumentHelper.createDocument();
        if(_document == null)
            return "";

        List<Map<String,Object>> list = menuTreeService.getMenuTree(true,null);
        addMenuEle(_document,null,list);

        insert(Menu.PROJECT_ID,_document);
        System.out.println("创建菜单树");
        return _document.asXML().toString();
    }

    public String createProjectXml(String projectId)throws Exception{
        String f = findOne(Menu.PROJECT_ID);
        if(StringUtils.isBlank(f))
            f = createMenuXml();
        Menu menuEntity = new Menu(null,null,f);

        Document _document = DocumentHelper.createDocument();
        if(_document == null)
            return "";

        //创建跟节点
        Element root = _document.addElement(Menu.MENU_TAG);
        root.setAttributes(menuEntity.getRoot().attributes());
        List<Element> list = menuEntity.getRoot().elements();
        for(Element el : list){
            Element e = root.addElement(Menu.MENU_TAG);
            e.setAttributes(el.attributes());
            addProEle(e,el.elements());
        }

        insert(projectId,_document);
        System.out.println("创建项目树");
        return _document.asXML().toString();
    }

    private void addMemEle(Document doc,Element parEle,List<Map<String,Object>> list,Map<String,CoreMemberInfoEntity> memMap){
        for(Map<String,Object> map : list) {
            String treeId = map.get("treeId").toString();
            CoreMemberInfoEntity entity = memMap.get(treeId);
            if (entity == null) {
                continue;
            }
            Element newEle = null;
            if(parEle == null){
                newEle = doc.addElement(Member.MEMBER_TAG);
            }else{
                newEle = parEle.addElement(Member.MEMBER_TAG);
            }
            newEle.addAttribute("id",entity.getMemberId());
            newEle.addAttribute("n",entity.getMemberName());
            newEle.addAttribute("p",String.valueOf(entity.getMemberType()));//是person
            if(map.get("children") != null){
                addMemEle(doc,newEle,(List<Map<String,Object>>) map.get("children"),memMap);
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
        if(list == null || list.size() < 1)
            return;
        for(Element el : list){
            Element e = p.addElement(Menu.MENU_TAG);
            e.setAttributes(el.attributes());
            addProEle(e,el.elements());
        }
    }

}
