package model.core.guide.service.core;

import model.core.guide.GuideType;
import model.core.guide.entity.CoreGuideFileEntity;
import model.core.guide.service.impl.CoreGuideFileServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Element;

import java.text.SimpleDateFormat;
import java.util.*;

public class MenuEx extends Comm{
    private static Map<String,Set<String>> mapAuth = null;
    protected Member memberEntity = new Member();
    protected CoreGuideFileServiceImpl fileService = CoreGuideFileServiceImpl.getInstance();

    public void removeCache(){
        //menuEntity.removeCache();
        memberEntity.removeCache();
        mapAuth = null;
    }

    public Map<String,Set<String>> getGuides(){
        if(mapAuth == null){
            mapAuth = new HashMap<>();
            List<CoreGuideFileEntity> guideFileList = fileService.findAll();
            //得到所有项目的权限
            for(CoreGuideFileEntity entity : guideFileList){
                if(Member.PROJECT_ID.equalsIgnoreCase(entity.getGuideId()) || Menu.PROJECT_ID.equalsIgnoreCase(entity.getGuideId())){
                    continue;
                }
                Menu menuEntity = new Menu(null,null,entity.getDocument());
                List<Element> listMem = menuEntity.findMems();
                for(Element mem : listMem){
                    String guideType = mem.getName().toString();
                    String menuId = menuEntity.getIdByItem(mem.getParent());
                    String memberId = memberEntity.getIdByItem(mem).split(" ")[0];
                    if("root".equalsIgnoreCase(menuId)){
                        List<Element> listM = menuEntity.getRoot().elements();
                        Set<String> set = mapAuth.get(memberId);
                        if(set == null)
                            set = new HashSet<>();
                        for(Element menu : listM){
                            if(Menu.MENU_TAG.equalsIgnoreCase(menu.getName().toString())){
                                menuId = menuEntity.getIdByItem(menu);
                                //得到当前菜单的所有上级菜单
                                List<Element> liMenus = menuEntity.getAncestor(menu);
                                for(Element m2 : liMenus){
                                    set.add(menuEntity.getIdByItem(m2));
                                }
                                //得到当前菜单的所有下级菜单与自己
                                liMenus = menuEntity.getDescendantAndSelf(menu,Menu.MENU_TAG);
                                for(Element m2 : liMenus){
                                    set.add(menuEntity.getIdByItem(m2));
                                    System.out.println(menuEntity.getNameByItem(m2) + " 拥有权限：" + guideType);
                                    if(m2.elements().size() == 0){
                                        //set.add(menuEntity.getIdByItem(m2) + ":" + guideType);
                                    }
                                }
                            }
                        }
                        mapAuth.put(memberId,set);
                    }else{
                        Set<String> set = mapAuth.get(memberId);
                        if(set == null)
                            set = new HashSet<>();
                        //得到当前菜单的所有上级菜单
                        List<Element> liMenus = menuEntity.getAncestor(mem.getParent());
                        for(Element m2 : liMenus){
                            set.add(menuEntity.getIdByItem(m2));
                        }
                        //得到当前菜单的所有下级菜单与自己
                        liMenus = menuEntity.getDescendantAndSelf(mem.getParent(),Menu.MENU_TAG);
                        for(Element m2 : liMenus){
                            set.add(menuEntity.getIdByItem(m2));
                            System.out.println(menuEntity.getNameByItem(m2) + " 拥有权限：" + guideType);
                            if(m2.elements().size() == 0){
                                //set.add(menuEntity.getIdByItem(m2) + ":" + guideType);
                            }
                        }
                        mapAuth.put(memberId,set);
                    }
                }
            }

            for(String memberId : mapAuth.keySet()){
                Element mem = memberEntity.getMemItem(memberId);
                if(mem.elements().size() > 0){
                    List<Element> liP = memberEntity.getDescendantPerson(mem);
                    for(Element m : liP){
                        Set<String> set = new HashSet<>();
                        set.addAll(mapAuth.get(memberId));
                        mapAuth.put(memberEntity.getIdByItem(m).split(";")[0], set);
                        System.out.println(memberEntity.getNameByItem(m) +  " 继承到权限");
                    }
                    mapAuth.remove(memberId);
                }
            }
        }
        return mapAuth;
    }

    public void addMemByMenu(String projectId,String menuId,Set<String> memberIds,String sourceType){
        Menu menuEntity = new Menu(projectId,null,null);
        Element menu = menuEntity.getMenuItem(menuId);
        for(String memberId : memberIds) {
            if(StringUtils.isBlank(memberId)){
                continue;
            }

            boolean notFirst = false;
            if (memberId.contains("notFirst")) {
                notFirst = true;
                memberId = memberId.replace("notFirst", "");
            }
            Element mem = memberEntity.getMemItem(memberId);

            select(menuEntity,menu,mem,GuideType.Read.getCode().toString());
            if(!notFirst){
                select(menuEntity,menu,mem, GuideType.Update.getCode().toString());
            }
        }

        fileService.delete(projectId);
        fileService.insert(projectId,menuEntity.getContext());
        System.out.println("新增权限("+ sourceType +")" + (new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()))+"\nprojectId："+projectId+"\nmenuId："+menuId+"\nmems："+ StringUtils.join(memberIds,";")+"\n");
    }
    public void addMemByMenu(String projectId,String menuId,String memberIds,String sourceType){
        addMemByMenu(projectId,menuId,new HashSet<>(Arrays.asList(memberIds.split("[;,]"))),sourceType);
    }

    public void removeMemByMenu(String projectId,String menuId,Set<String> memberIds,String sourceType){
        Menu menuEntity = new Menu(projectId,null,null);
        Element menu = menuEntity.getMenuItem(menuId);
        List<String> listMemName = new ArrayList<>();
        for(String memberId : memberIds) {
            String[] m = memberId.split("@");

            Element mem = memberEntity.getMemItem(m[0]);
            listMemName.add(memberEntity.getNameByItem(mem));
            if(m.length == 2 && StringUtils.isNotBlank(m[1])){
                for(String t : m[1].split(",")){
                    reverseSelect(menuEntity,menu,mem,t);
                }
            }else{
                for (GuideType g : GuideType.values()){
                    reverseSelect(menuEntity,menu,mem,g.getCode().toString());
                }
            }
        }

        fileService.delete(projectId);
        fileService.insert(projectId,menuEntity.getContext());

        String eventData = "删除权限("+ sourceType +")" + (new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()))+"\nprojectId："+projectId+"\nmenuId："+menuId+"\nmenuName："+menuEntity.getNameByItem(menu)+"\nmems："+ StringUtils.join(memberIds,";")+"\nmemNames："+ StringUtils.join(listMemName,";");
        System.out.println(eventData);
    }
    public void removeMemByMenu(String projectId,String menuId,String memberIds,String sourceType){
        removeMemByMenu(projectId,menuId,new HashSet<>(Arrays.asList(memberIds.split(";"))),sourceType);
    }

    public void updateGuideByMemChange(String memberId,String sourceType)throws Exception{
        //得到当前成员变更前的所有节点,如果时人员,则可能因为兼职原因而存在多个节点
        List<Element> oldMem = memberEntity.getMemItems(memberId);
        if(isNull(oldMem)){
            //更新成员树
            fileService.createMemberXml(sourceType + "-updateGuideByMemChange",memberId,null);
            return;
        }

        CoreGuideFileServiceImpl.IS_UPDATE = true;
        String memberName = getNameByItem(oldMem.get(0));
        //更新成员树
        fileService.createMemberXml(sourceType + "-updateGuideByMemChange",memberId,null);
        //得到当前成员变更否的节点,只可能有一个(人员被移动时,所有兼职会被删除)
        Element newMem = memberEntity.getMemItem(memberId);
        if(!isNull(newMem)){
            memberName = getNameByItem(newMem);
        }

        //得到所有项目权限
        List<CoreGuideFileEntity> guideFileList = fileService.findAll();
        List<CoreGuideFileEntity> listU = new ArrayList<>();
        for(CoreGuideFileEntity entity : guideFileList) {
            Menu menuEntity = new Menu(null,null,entity.getDocument());
            //在该项目根据旧的成员信息得到权限信息
            List<Element> oldGdMem = menuEntity.findMemsByMem(oldMem);
            for(Element ogm : oldGdMem){
                Element menu = ogm.getParent();
                String type = ogm.getName().toString();
                if("updateMem".equalsIgnoreCase(sourceType)){
                    String oldName = getNameByItem(ogm);
                    if(!oldName.equalsIgnoreCase(memberName)){
                        ogm.addAttribute("n",memberName);
                    }
                }else if("moveMem".equalsIgnoreCase(sourceType)){
                    //如果旧的权限成员ID跟新的成员ID不相同,说明本次变更的是人员,将该权限删除,并加上新的成员
                    if(!equals(ogm,newMem)){
                        menuEntity.removeMemDescendantAndSelf(menu,ogm,type);
                        select(menuEntity,menu,newMem,type);
                    }else{
                        //如果两个节点相等,说明本次调整的不是人员,判断上级菜单是否已经包含该菜单上面的权限
                        boolean isHave = false;
                        for(Element ma : menuEntity.getAncestor(menu)){
                            if(!isNull(menuEntity.findMemAncestorAndSelfByMenuAndMem(ma,ogm,type))){
                                isHave = true;
                                break;
                            }
                        }
                        if(isHave){
                            menuEntity.removeMemDescendantAndSelf(menu,ogm,type);
                        }
                    }
                }else if("deleteMem".equalsIgnoreCase(sourceType)){
                    menuEntity.removeMemDescendantAndSelf(menu,ogm,type);
                }
            }
            entity.setDocument(menuEntity.getContext().asXML());
            listU.add(entity);
        }
        if(listU.size() > 0){
            fileService.update(listU);
        }

        System.out.println("成员变更("+ sourceType +")" + (new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()))+"\nmemberId："+memberId+"\nmemberName："+ memberName);
    }

//    public int copyPro(String[] copyProjects,String projectId,String memberId){
//        Menu menuEntity = new Menu(projectId,null,null);
//        List<Element> list = menuEntity.findMemsByMemAncestorAndSelf(memberEntity.getMemItems(memberId));
//        if(list.size() > 0){
//            Element thisMem = memberEntity.getMemItem(memberId);
//            for(String cProId : copyProjects){
//                menuEntity = new Menu(cProId,null,null);
//                for(Element mem : list){
//                    String type = mem.getName().toString();
//                    String menuId = getIdByItem(mem.getParent());
//                    select(menuEntity,menuEntity.getMenuItem(menuId),thisMem,type);
//                }
//                fileService.insert(cProId,menuEntity.getContext());
//            }
//
//            System.out.println("项目复制" + (new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()))+"\nprojectId："+projectId+"\nmemberId："+memberId+"\ncopyProjects："+ StringUtils.join(copyProjects,";")+"\n");
//        }
//        return list.size();
//    }

    public int copyMem(String copy_memberIds,String projectId,String memberId){
        Menu menuEntity = new Menu(projectId,null,null);
        List<Element> list = menuEntity.findMemsByMemAncestorAndSelf(memberEntity.getMemItems(memberId));
        if(list.size() > 0){
            List<Element> listCMem = new ArrayList<>();
            for(String cMemIds : copy_memberIds.split(",")){
                String[] ids = cMemIds.split(";");
                listCMem.add(memberEntity.getMemItem(memberEntity.isPerson(ids[0]) ? cMemIds : ids[0]));
            }
            for(Element mem : list){
                String type = mem.getName().toString();
                String menuId = getIdByItem(mem.getParent());
                for(Element cMem : listCMem){
                    select(menuEntity,menuEntity.getMenuItem(menuId),cMem,type);
                }
            }
            fileService.delete(projectId);
            fileService.insert(projectId,menuEntity.getContext());
            System.out.println("成员复制" + (new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()))+"\nprojectId："+projectId+"\ncopy_memberIds："+copy_memberIds+"\nmemberId："+ memberId+"\n");
        }
        return list.size();
    }

    //选择
    public void select(Menu menuEntity,Element menu,Element mem, String type) {
        //在menu节点下新增mem节点，新增前判断是否存在mem的某个父级节点，不存在才新增,如果存在mem的下级,删除下级
        menuEntity.addMem(menu, mem,type);

        //得到menu节点的所有下级,删除mem及其下级
        List<Element> menuChildren = getDescendant(menu,MENU_TAG);
        for (Element menuCh : menuChildren) {
            menuEntity.removeMemDescendantAndSelf(menuCh, mem,type);
        }

        //处理menu节点所有上级勾选状态
        List<Element> menuAncestor = menuEntity.getAncestor(menu);
        //用反序遍历是因为Menu.getAncestor函数返回父祖级menu节点的集合是从上往下的，而这个地方需要从下往上
        for (int i = getLength(menuAncestor) - 1; i >= 0 ; i--) {
            //得到某个上级节点的子节点集合
            List<String> sonMenuAuths = new ArrayList<>();
            Element m_p = getItem(menuAncestor, i);
            List<Element> sonMenus = getChildren(m_p, MENU_TAG);
            if(getLength(sonMenus) == 0 ){
                continue;
            }
            for(Element sm : sonMenus) {
                if(getLength(menuEntity.findMemAncestorAndSelfByMenuAndMem(sm, mem,type)) != 0) {
                    sonMenuAuths.add(getCheck());
                }else{
                    sonMenuAuths.add(getUncheck());
                }
            }

            if (isAllAuth(sonMenuAuths)) {
                // console.log("该上级是全选");
                //所有的子级menu节点都具有该mem节点，则该menu节点增加该mem节点
                //Menu.addMem函数会判断该menu节点包含本级mem节点和上级mem节点的情况
                select(menuEntity,m_p, mem,type);

                // console.log("删除多余成员");
                //兄弟全部菜单包含该成员，上级菜单全选，兄弟菜单去掉该成员,兄弟的子节点不管，因为兄弟全选，其子节点必然已经去掉了该成员
                for (Element sm : sonMenus) {
                    menuEntity.removeMemDescendantAndSelf(sm, mem,type);
                }
            }else{
                //判断是否包含其下级某个mem,如果包含，在其它menu上删除该mem的下级
                List<Element> c_mems = getDescendant(mem,MEMBER_TAG);
                if(c_mems != null && getLength(c_mems) > 0){
                    Boolean isHave = false;
                    for(Element c_mem : c_mems){
                        List<String> c_sonMenuAuths = new ArrayList<>();
                        for (Element sm : sonMenus) {
                            if (getLength(menuEntity.findMemAncestorAndSelfByMenuAndMem(sm, c_mem,type)) != 0) {
                                c_sonMenuAuths.add(getCheck());
                            }else {
                                c_sonMenuAuths.add(getUncheck());
                            }
                        }
                        if (isAllAuth(c_sonMenuAuths)) {
                            // console.log("该上级是全选");
                            //所有的子级menu节点都具有该mem节点，则该menu节点增加该mem节点
                            //Menu.addMem函数会判断该menu节点包含本级mem节点和上级mem节点的情况
                            select(menuEntity,m_p, c_mem,type);
                            //删除该menu节点中本mem节点的下级mem节点
                            menuEntity.removeMemDescendant(m_p, c_mem,type);

                            // console.log("删除多余成员");
                            //兄弟全部菜单包含该成员，上级菜单全选，兄弟菜单去掉该成员,兄弟的子节点不管，因为兄弟全选，其子节点必然已经去掉了该成员
                            for (Element sm : sonMenus) {
                                menuEntity.removeMemDescendantAndSelf(sm, c_mem,type);
                            }
                            isHave = true;
                        }
                    }
                    if(!isHave){
                        break;
                    }
                }else{
                    //该上级不是全选，则不需要继续往上级循环
                    break;
                }
                // console.log("该上级不是全选，则不需要继续往上级循环");
            }
        }

        //对所有上级menu及自己的mem进行合并
        mergeMem(menuEntity,menuEntity.getAncestorAndSelf(menu),mem,type);
        //对所有下级menu的mem进行合并
        mergeMem(menuEntity,getDescendant(menu,MENU_TAG),mem,type);

        removeCache();//每次操作完成都需要清除缓存
        CoreGuideFileServiceImpl.IS_UPDATE = true;
    }
    //取消选择
    private void reverseSelect(Menu menuEntity,Element menu,Element mem,String type) {
        //处理上级菜单
        List<Element> menuAncestor = menuEntity.getAncestor(menu);
        for (Element p_item : menuAncestor) {
            menuStripping(menuEntity,menu,p_item,mem,type);
        }

        //处理自家
        //处理menu节点中含mem节点及其下级节点的情况,直接删除
        menuEntity.removeMemDescendantAndSelf(menu, mem,type);

        //处理menu节点中含mem上级节点的情况,进行剥离
        List<Element> memAncestor = menuEntity.findMemAncestorByMenuAndMem(menu, mem,type);
        for (int j = getLength(memAncestor) - 1; j >= 0; j--) {
            // 对mem节点进行剥离
            Element p_item = getItem(memAncestor, j);
            List<Element> strippingMemItems = memberEntity.stripping(p_item, mem);
            //删除原mem节点
            menuEntity.removeMem(menu, p_item,type);
            //新增剥离后的mem
            for(Element sm : strippingMemItems){
                select(menuEntity,menu, sm,type);
            }
        }

        //处理下级菜单
        List<Element> menuDescendant = getDescendant(menu,MENU_TAG);
        for (int i = getLength(menuDescendant) - 1; i >= 0; i--) {
            Element ch_item = getItem(menuDescendant, i);
            //处理menu节点中含mem节点及其下级节点的情况,直接删除
            menuEntity.removeMemDescendantAndSelf(ch_item, mem,type);

            //处理menu节点中含mem上级节点的情况,进行剥离
            memAncestor = menuEntity.findMemAncestorByMenuAndMem(ch_item, mem,type);
            for (int j = getLength(memAncestor) - 1; j >= 0; j--) {
                // 对mem节点进行剥离
                Element p_item = getItem(memAncestor, j);
                List<Element> strippingMemItems = memberEntity.stripping(p_item, mem);
                //删除原mem节点
                menuEntity.removeMem(ch_item, p_item,type);
                //新增剥离后的mem
                for(Element sm : strippingMemItems){
                    select(menuEntity,ch_item, sm,type);
                }
            }
        }

        removeCache();//每次操作完成都需要清除缓存
        CoreGuideFileServiceImpl.IS_UPDATE = true;
    }

    //从mem1中剥离出mem2
    public void menuStripping(Menu menuEntity,Element menuOld,Element menu,Element mem,String type) {
        //处理上级
        //得到所有上级以及自己，如果上级存在mem（如果上级存在mem的上级，上级留下剥离出来的人），删除mem及其下级mem，并为兄弟节点新增mem。
        List<Element> memAncestorAndSelf = menuEntity.findMemAncestorAndSelfByMenuAndMem(menu, mem,type);
        if(getLength(memAncestorAndSelf) > 0){
            for (int j = getLength(memAncestorAndSelf) - 1; j >= 0; j--) {
                Element rp_item = getItem(memAncestorAndSelf, j);
                //如果上级存在mem，删除mem及其下级mem，并为兄弟节点新增mem
                if(equals(rp_item,mem)){
                    menuEntity.removeMemDescendantAndSelf(menu, rp_item,type);
                }else{
                    //上级menu存在mem的上级，进行剥离
                    List<Element> strippingMemItems = memberEntity.stripping(rp_item, mem);
                    //删除原mem节点
                    menuEntity.removeMem(menu, rp_item,type);
                    //新增剥离剩下的mem
                    for(Element sm : strippingMemItems){
                        select(menuEntity,menu, sm,type);
                    }
                }
            }

            List<Element> xd_items = getChildren(menu,MENU_TAG);
            for(Element xd_item : xd_items){
                if(equals(xd_item,menuOld)){
                    //处理menu节点中含mem上级节点的情况,进行剥离
                    List<Element> memAncestor = menuEntity.findMemAncestorByMenuAndMem(xd_item, mem,type);
                    for (int j = getLength(memAncestor) - 1; j >= 0; j--) {
                        // 对mem节点进行剥离
                        Element p_item = getItem(memAncestor, j);
                        List<Element> strippingMemItems = memberEntity.stripping(p_item, mem);
                        //删除原mem节点
                        menuEntity.removeMem(xd_item, p_item,type);
                        //新增剥离后的mem
                        for(Element sm : strippingMemItems){
                            select(menuEntity,xd_item, sm,type);
                        }
                    }
                }else if(contains(xd_item,menuOld,MENU_TAG)){
                    menuEntity.addMem(xd_item, mem,type);
                    menuStripping(menuEntity,menuOld,xd_item,mem,type);
                }
            }
            for(Element xd_item : xd_items){
                if(!contains(xd_item,menuOld,MENU_TAG)){
                    select(menuEntity,xd_item, mem,type);
                }
            }
        }else{
            //得到该成员在该菜单中的所以下级
            List<Element> memDescendant = menuEntity.findMemDescendantByMenuAndMem(menu, mem,type);
            if(getLength(memDescendant) > 0){
                for(Element md : memDescendant){
                    //删除原mem节点
                    menuEntity.removeMem(menu, md,type);
                }

                List<Element> xd_items = getChildren(menu,MENU_TAG);
                for(Element xd_item : xd_items){
                    if(equals(xd_item,menuOld)){
                        //处理menu节点中含mem上级节点的情况,进行剥离
                        List<Element> memAncestor = menuEntity.findMemAncestorByMenuAndMem(xd_item, mem,type);
                        for (int j = getLength(memAncestor) - 1; j >= 0; j--) {
                            // 对mem节点进行剥离
                            Element p_item = getItem(memAncestor, j);
                            List<Element> strippingMemItems = memberEntity.stripping(p_item, mem);
                            //删除原mem节点
                            menuEntity.removeMem(xd_item, p_item,type);
                            //新增剥离后的mem
                            for(Element sm : strippingMemItems){
                                select(menuEntity,xd_item, sm,type);
                            }
                        }
                    }else if(contains(xd_item,menuOld,MENU_TAG)){
                        for(Element md : memDescendant){
                            menuEntity.addMem(xd_item, md,type);
                        }
                        menuStripping(menuEntity,menuOld,xd_item,mem,type);
                    }
                }
                for(Element xd_item : xd_items){
                    if(!contains(xd_item,menuOld,MENU_TAG)){
                        for(Element md : memDescendant){
                            select(menuEntity,xd_item, md,type);
                        }
                    }
                }
            }
        }
    }

    //合并菜单上的mem
    public void mergeMem(Menu menuEntity,List<Element> menus,Element mem,String type){
        for(int o = getLength(menus)-1;o >=0;o--){
            Element menuItem = getItem(menus,o);
            // console.log(menuItem);
            //对该menu节点的mem进行合并
            List<Element> mems = menuEntity.findMemsByMenu(menuItem,type);
            // console.log("现有人员：");
            // console.log(mems);
            if(getLength(mems) < 1){
                continue;
            }
            //得到该mem节点的所有上级
            List<Element> p_mems = memberEntity.getAncestor(mem);
            //从下往上循环
            for(int i = getLength(p_mems)-1;i >= 0;i--){
                mems = menuEntity.findMemsByMenu(menuItem,type);
                Element p_mem = getItem(p_mems,i);
                // console.log("上级节点：");
                // console.log(p_mem);
                List<Element> p_c_mem = getChildren(p_mem,MEMBER_TAG);

                // console.log("兄弟节点：");
                // console.log(p_c_mem);
                int j = 0;
                for(Element p_c_m : p_c_mem){
                    boolean isHave = false;
                    for(Element m : mems){
                        if(equals(p_c_m,m)){
                            j++;
                            isHave = true;
                            break;
                        }
                    }
                    if(!isHave){
                        List<Element> menus2 = menuEntity.getAncestor(menuItem);
                        for(int n = getLength(menus2)-1;n >=0;n--){
                            Element menuItem2 = getItem(menus2,n);
                            List<Element> mems2 = menuEntity.findMemsByMenu(menuItem2,type);
                            if(getLength(mems2) < 1){
                                continue;
                            }
                            for(int nn = getLength(mems2)-1;nn >=0;nn--){
                                if(equals(p_c_m,getItem(mems2,nn))){
                                    j++;
                                    isHave = true;
                                    break;
                                }
                            }
                            if(isHave){
                                break;
                            }
                        }
                    }
                }
                if(j > 0 && j == getLength(p_c_mem)){
                    select(menuEntity,menuItem, p_mem,type);
                    for(Element p_c_m : p_c_mem){
                        menuEntity.removeMem(menuItem, p_c_m,type);
                    }
                }
            }
        }
    }


    private static final String _check = "checkImg tree-icon  check";//全选
    private static final String _checkmix = "checkImg tree-icon  checkmix";//半选
    private static final String _uncheck = "checkImg tree-icon  uncheck";//不选
    private String getCheck() {
        return _check;
    }
    private String getCheckMix() {
        return _checkmix;
    }
    private String getUncheck() {
        return _uncheck;
    }

    private Boolean  isAllAuth(List<String> sonMenus) {
        if (sonMenus.size() == 0) {
            new Exception("数组为空");
        }
        for (int i = 0; i < sonMenus.size(); i++) {
            if (!sonMenus.get(i).equals(getCheck())) {
                return false;
            }
        }
        return true;
    }

    private Boolean isAllNotAuth(List<String> sonMenus) {
        if (sonMenus.size() == 0) {
            new Exception("数组为空");
        }
        for (int i = 0; i < sonMenus.size(); i++) {
            if (!sonMenus.get(i).equals(getUncheck())) {
                return false;
            }
        }
        return true;
    }
//
//    public static MenuEx getInstance() {
//        return ApplicationContext.getCurrent().getBean(MenuEx.class);
//    }
}
