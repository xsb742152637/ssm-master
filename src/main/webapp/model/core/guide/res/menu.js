/**
 * Created by xc on 2018/2/7.
 */
let Menu = (function(){

    let context;//菜单xml对象
    let ParentsExpression;
    let ParentsAndSelfExpression;
    let SingleLevelChildrenExpression;
    let ChildrenExpression;
    let ChildrenAndSelfExpression;
    let BrothersExpression;

    let AllMemsExpression_r;
    let AllMemsExpression_u;
    let AllMemsExpression_s;

    //固定的缓存对象
    let cache_menu_items = new Map();
    let cache_menu_parents = new Map();
    let cache_menu_parentAndSelfs = new Map();
    let cache_menu_SingleLevelChildren = new Map();
    let cache_menu_children = new Map();
    let cache_menu_childrenAndSelf = new Map();
    let cache_menu_brothers = new Map();

    //非固定的缓存对象，会因为勾选情况相应更新
    let cache_root;
    let cache_menu_allMems = new Map();
    let cache_menu_mem = new Map();
    let cache_menu_memParents = new Map();
    let cache_menu_memParentsAndSelf = new Map();
    let cache_menu_memChildren = new Map();
    let cache_menu_memChildrenAndSelf = new Map();

    //如果菜单上的成员发生改变，相应的要改变缓存中的值
    function removeCache(){
        cache_menu_items = new Map();
        cache_menu_parents = new Map();
        cache_menu_parentAndSelfs = new Map();
        cache_menu_SingleLevelChildren = new Map();
        cache_menu_children = new Map();
        cache_menu_childrenAndSelf = new Map();
        cache_menu_brothers = new Map();

        cache_root = null;
        cache_menu_allMems = new Map();
        cache_menu_mem = new Map();
        cache_menu_memParents = new Map();
        cache_menu_memParentsAndSelf = new Map();
        cache_menu_memChildren = new Map();
        cache_menu_memChildrenAndSelf = new Map();

        // console.log("-------------清空缓存-----------------");
    }

    //根据xml文件路径得到文件内容并转换成xsl文件格式
    function transform() {
        context = null;//每次切换项目时，需要重新请求，所有将此对象设置为空
        ParentsExpression = undefined;
        ParentsAndSelfExpression = undefined;
        SingleLevelChildrenExpression = undefined;
        ChildrenExpression = undefined;
        ChildrenAndSelfExpression = undefined;
        BrothersExpression = undefined;

        AllMemsExpression_r = undefined;
        AllMemsExpression_u = undefined;
        AllMemsExpression_s = undefined;
        return Comm.getXsltProcessor(Config.getMenuTransformPath()).transformToFragment(getContext(), document);
    }


    //根据xml文件路径得到文件内容
    function getContext() {
        if(context === undefined || context == null) {
            let dataUrl = "/core/guide/getXmlTree.do";
            let projectId = $("#projectId").val();
            context = Comm.load(dataUrl,{xmlType:"caiDanTree",projectId: projectId,random:Math.random()});
        }
        return context;
    }

    //得到根节点
    function getRoot() {
        if(cache_root == undefined || cache_root == null){
            cache_root = getContext().documentElement;
        }
        return cache_root;
    }

    //根据id得到一个menu节点
    function getItem(id) {
        let val = cache_menu_items.get(id);
        if(Comm.isNull(val)){
            val = getContext().evaluate("//"+menuTag+"[@id='" + id + "']", getRoot(), null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;
            cache_menu_items.set(id,val);
        }
        return val;
    }

    //得到一个menu节点的所有父级节点，先得到距自己最近的父级节点
    function getParents(menu) {
        let id = Comm.getIdByItem(menu);
        let val = cache_menu_parents.get(id);
        if(Comm.isNull(val)){
            val = Comm.getParents(getContext(), menu, ParentsExpression);
            cache_menu_parents.set(id,val);
        }
        return val;
    }

    //得到一个menu节点的所有父级节点，先得到自己，再得到距自己最近的父级节点
    function getParentsAndSelf(menu) {
        let id = Comm.getIdByItem(menu);
        let val = cache_menu_parentAndSelfs.get(id);
        if(Comm.isNull(val)){
            val = Comm.getParentsAndSelf(getContext(), menu, ParentsAndSelfExpression);
            cache_menu_parentAndSelfs.set(id,val);
        }
        return val;
    }

    //得到menu节点的下一级节点
    function getSingleLevelChildren(menu) {
        let id = Comm.getIdByItem(menu);
        let val = cache_menu_SingleLevelChildren.get(id);
        if(Comm.isNull(val)){
            if (SingleLevelChildrenExpression === undefined) {
                SingleLevelChildrenExpression = getContext().createExpression("child::"+menuTag, null);
            }
            val = SingleLevelChildrenExpression.evaluate(menu, XPathResult.ORDERED_NODE_SNAPSHOT_TYPE, null);
            cache_menu_SingleLevelChildren.set(id,val);
        }
        return val;
    }

    //得到一个menu节点的所有子节点，先得到距自己最近的子级节点
    function getChildren(menu) {
        let id = Comm.getIdByItem(menu);
        let val = cache_menu_children.get(id);
        if(Comm.isNull(val)){
            if (ChildrenExpression === undefined) {
                ChildrenExpression = getContext().createExpression("descendant::"+menuTag, null);
            }
            val = ChildrenExpression.evaluate(menu, XPathResult.ORDERED_NODE_SNAPSHOT_TYPE, null);
            cache_menu_children.set(id,val);
        }
        return val;
    }

    //得到一个menu节点的所有子节点以及自己，再得到距自己最近的子级节点
    function getChildrenAndSelf(menu) {
        let id = Comm.getIdByItem(menu);
        let val = cache_menu_childrenAndSelf.get(id);
        if(Comm.isNull(val)){
            if (ChildrenAndSelfExpression === undefined) {
                ChildrenAndSelfExpression = getContext().createExpression("descendant-or-self::"+menuTag, null);
            }
            val = ChildrenAndSelfExpression.evaluate(menu, XPathResult.ORDERED_NODE_SNAPSHOT_TYPE, null);
            cache_menu_childrenAndSelf.set(id,val);
        }
        return val;
    }

    //得到一个menu节点的所有兄弟节点
    function getBrothers(menu) {
        let id = Comm.getIdByItem(menu);
        let val = cache_menu_brothers.get(id);
        if(Comm.isNull(val)){
            if (BrothersExpression === undefined) {
                BrothersExpression = getContext().createExpression("preceding-sibling::"+menuTag+" | following-sibling::"+menuTag, null);
            }
            val = BrothersExpression.evaluate(menu, XPathResult.ORDERED_NODE_SNAPSHOT_TYPE, null);
            cache_menu_brothers.set(id,val);
        }
        return val;
    }

    //得到一个menu节点包含的所有mem节点
    function getAllMems(menu,type) {
        let id = Comm.getIdByItem(menu)+type;
        let val = cache_menu_allMems.get(id);
        if(Comm.isNull(val)){
            let AllMemsExpression = "";
            if(codeRead == type){
                if (AllMemsExpression_r === undefined) {
                    AllMemsExpression_r = getContext().createExpression(type, null);
                }
                AllMemsExpression = AllMemsExpression_r;
            }else if(codeReadSelf == type){
                if (AllMemsExpression_s === undefined) {
                    AllMemsExpression_s = getContext().createExpression(type, null);
                }
                AllMemsExpression = AllMemsExpression_s;
            }else{
                if (AllMemsExpression_u === undefined) {
                    AllMemsExpression_u = getContext().createExpression(type, null);
                }
                AllMemsExpression = AllMemsExpression_u;
            }

            val = AllMemsExpression.evaluate(menu, XPathResult.ORDERED_NODE_SNAPSHOT_TYPE, null);
            cache_menu_allMems.set(id,val);
        }
        return val;
    }

    //根据一组id找到一个menu节点包含的特定一组mem节点
    function findMems(menu, mems,type) {
        let val = new Array();
        for (let i = 0; i < Comm.getLength(mems); i++) {
            let memItem = findMem(menu, Comm.getItem(mems, i),type);
            if (memItem != null) {
                val.push(memItem);
            }
        }
        return val;
    }

    //根据mem及其上级找到其所有存在的menu
    function findMenuByMemParentAndSelf(mem,type){
        let val = new Array();
        let mems = Mem.getParentsAndSelf(mem);
        for (let i = 0; i < Comm.getLength(mems); i++) {
            let menuItem = getContext().evaluate("//"+type+"[@id='" + Comm.getIdByItem(Comm.getItem(mems, i)) + "']//parent::"+menuTag, getRoot(), null, XPathResult.ORDERED_NODE_SNAPSHOT_TYPE, null);
            if (menuItem != null) {
                for(let m = 0;m < Comm.getLength(menuItem);m++){
                    val.push(Comm.getItem(menuItem,m));
                }
            }
        }
        return val;
    }
    //在menu节点中得到mem节点
    function findMem(menu, mem,type) {
        let id = Comm.getIdByItem(menu)+Comm.getIdByItem(mem)+type;
        let val = cache_menu_mem.get(id);
        if(Comm.isNull(val)){
            val = getContext().evaluate(type+"[@id='" + Comm.getIdByItem(mem) + "']", menu, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;
            cache_menu_mem.set(id,val);
        }
        return val;
    }

    //得到一个menu节点包含的某个mem节点所有的父节点
    function findMemParents(menu, mem,type) {
        let id = Comm.getIdByItem(menu)+Comm.getIdByItem(mem)+type;
        let val = cache_menu_memParents.get(id);
        if(Comm.isNull(val)){
            val = findMems(menu, Mem.getParents(mem),type);
            cache_menu_memParents.set(id,val);
        }
        return val;
    }

    //得到一个menu节点包含的某个mem节点所有的父节点（含自己）
    function findMemParentsAndSelf(menu, mem,type) {
        let id = Comm.getIdByItem(menu)+Comm.getIdByItem(mem)+type;
        let val = cache_menu_memParentsAndSelf.get(id);
        if(Comm.isNull(val)){
            val = findMems(menu, Mem.getParentsAndSelf(mem),type);
            cache_menu_memParentsAndSelf.set(id,val);
        }
        return val;
    }

    //得到一个menu节点包含的某个mem节点所有的子节点
    function findMemChildren(menu, mem,type) {
        let id = Comm.getIdByItem(menu)+Comm.getIdByItem(mem)+type;
        let val = cache_menu_memChildren.get(id);
        if(Comm.isNull(val)){
            val = findMems(menu, Mem.getChildren(mem),type);
            cache_menu_memChildren.set(id,val);
        }
        return val;
    }

    //得到一个menu节点包含的某个mem节点所有的子节点（含自己）
    function findMemChildrenAndSelf(menu, mem,type) {
        let id = Comm.getIdByItem(menu)+Comm.getIdByItem(mem)+type;
        let val = cache_menu_memChildrenAndSelf.get(id);
        if(Comm.isNull(val)){
            val = findMems(menu, Mem.getChildrenAndSelf(mem),type);
            cache_menu_memChildrenAndSelf.set(id,val);
        }
        return val;
    }

    //向menu节点中添加mem节点
    function addMem(menu, mem,type) {
        removeMemChildren(menu, mem,type);
        let newMem = getContext().createElement(type);
        newMem.setAttribute("id", mem.attributes.getNamedItem("id").nodeValue);
        newMem.setAttribute("n", mem.attributes.getNamedItem("n").nodeValue);
        menu.appendChild(newMem);
        removeCache();
    }

    //将一个mem节点加入menu节点中,加入之前先检查是否存在
    function addMemBeforeCheck(menu, mem,type) {
        //查找本级是否已经有mem或其上级，没有的话才增加
        let result = findMemParentsAndSelf(menu, mem,type);
        if (Comm.getLength(result) == 0) {
            addMem(menu, mem,type);
        }
    }

    //将一个mem节点从menu节点中移除
    function removeMem(menu, mem,type) {
        let memItem = findMem(menu, mem,type);
        if (memItem != null) {
            menu.removeChild(memItem);
        }
        removeCache();
    }
    //将一个mem节点从menu节点中移除
    function removeAllMem(menu, mems) {
        if (mems != null) {
            for(let i = 0;i < mems.length;i++){
                menu.removeChild(mems[i]);
            }
        }
        removeCache();
    }

    //将一个menu节点中的mem下级节点删除
    function removeMemChildren(menu, mem,type) {
        //得到mem节点的所有下级
        let memChildren = findMemChildren(menu, mem,type);
        for (let i = Comm.getLength(memChildren) - 1; i >= 0; i--) {
            removeMem(menu, Comm.getItem(memChildren, i),type);
        }
        removeCache();
    }

    //将一个menu节点中的mem下级及自己节点删除
    function removeMemChildrenAndSelf(menu, mem,type) {
        removeMem(menu, mem,type);
        //得到mem节点的所有下级
        let memChildren = findMemChildren(menu, mem,type);
        for (let i = Comm.getLength(memChildren) - 1; i >= 0; i--) {
            removeMem(menu, Comm.getItem(memChildren, i),type);
        }
    }

    //判断一个menu节点是否包含另一个menu节点
    function contains(menu1, menu2) {
        if(Comm.equals(menu1,menu2)){
           return true;
        }
        let result = getContext().evaluate(".//"+menuTag+"[@id='" + Comm.getIdByItem(menu2) + "']", menu1, null, XPathResult.ORDERED_NODE_SNAPSHOT_TYPE, null);
        return result != null && Comm.getLength(result) > 0 ? true : false;
    }

    // function removeAllCache(){
    //     cache_menu_items = new Map();
    //     cache_menu_parents = new Map();
    //     cache_menu_parentAndSelfs = new Map();
    //     cache_menu_SingleLevelChildren = new Map();
    //     cache_menu_children = new Map();
    //     cache_menu_childrenAndSelf = new Map();
    //     cache_menu_brothers = new Map();
    // }

    return {
        transform : transform,
        getContext : getContext,
        getRoot : getRoot,
        getItem : getItem,
        getParents : getParents,
        getParentsAndSelf : getParentsAndSelf,
        getSingleLevelChildren : getSingleLevelChildren,
        getChildren : getChildren,
        getChildrenAndSelf : getChildrenAndSelf,
        getBrothers : getBrothers,
        getAllMems : getAllMems,
        findMem : findMem,
        findMems : findMems,
        addMem : addMem,
        addMemBeforeCheck : addMemBeforeCheck,
        removeMem : removeMem,
        removeAllMem : removeAllMem,
        removeMemChildren : removeMemChildren,
        removeMemChildrenAndSelf : removeMemChildrenAndSelf,
        removeCache : removeCache,
        findMemParents : findMemParents,
        findMemParentsAndSelf : findMemParentsAndSelf,
        findMemChildren : findMemChildren,
        findMemChildrenAndSelf : findMemChildrenAndSelf,
        findMenuByMemParentAndSelf : findMenuByMemParentAndSelf,
        contains : contains
    }

})();