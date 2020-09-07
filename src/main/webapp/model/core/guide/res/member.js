/**
 * Created by xc on 2018/2/7.
 */
let Mem = (function(){
    let dataUrl = "/core/guide/getXmlTree.do";

    let context;//xml对象
    let ParentsExpression;
    let ParentsAndSelfExpression;
    let SingleLevelChildrenExpression;
    let ChildrenExpression;
    let ChildrenAndSelfExpression;

    //固定的缓存对象
    let cache_root;
    let cache_mem_item = new Map();
    let cache_mem_parents = new Map();
    let cache_mem_parentsAndSelf = new Map();
    let cache_mem_singleLevelChildren = new Map();
    let cache_mem_children = new Map();
    let cache_mem_childrenAndSelf = new Map();

    //如果菜单上的成员发生改变，相应的要改变缓存中的值
    function removeCache(){
        cache_root = null;
        cache_mem_item = new Map();
        cache_mem_parents = new Map();
        cache_mem_parentsAndSelf = new Map();
        cache_mem_singleLevelChildren = new Map();
        cache_mem_children = new Map();
        cache_mem_childrenAndSelf = new Map();

        // console.log("-------------清空缓存-----------------");
    }

    //得到成员xml并转换成xsl
    function transform(url,param){
        if(!String.isNullOrWhiteSpace(url)){
            dataUrl = url
        }
        context = null;
        ParentsExpression = undefined;
        ParentsAndSelfExpression = undefined;
        SingleLevelChildrenExpression = undefined;
        ChildrenExpression = undefined;
        ChildrenAndSelfExpression = undefined;
        removeCache();
        return Comm.getXsltProcessor(Config.getMemTransformPath()).transformToFragment(getContext(param), document);
    }

    //得到成员xml
    function getContext(param) {
        if(context === undefined || context == null) {
            let p = {xmlType:"memberTree",projectId:"memberTree"};
            if(param){
                p = $.extend({}, p, param);
            }
            context = Comm.load(dataUrl, p);
        }
        return context;
    }

    //得到成员树根节点
    function getRoot() {
        if(cache_root == undefined || cache_root == null){
            cache_root = getContext().documentElement;
        }
        return cache_root;
    }

    //根据ID得到成员对象
    function getItem(id) {
        let val = cache_mem_item.get(id);
        if(Comm.isNull(val)){
            val = getContext().evaluate("//"+memberTag+"[@id='" + id + "']", getRoot(), null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;
            cache_mem_item.set(id,val);
        }
        return val;
    }

    //得到父级节点
    function getParents(mem) {
        let id = Comm.getIdByItem(mem);
        let val = cache_mem_parents.get(id);
        if(Comm.isNull(val)){
            val = Comm.getParents(getContext(), mem, ParentsExpression);
            cache_mem_parents.set(id,val);
        }
        return val;
    }

    //得到父级节点以及自己
    function getParentsAndSelf(mem) {
        let id = Comm.getIdByItem(mem);
        let val = cache_mem_parentsAndSelf.get(id);
        if(Comm.isNull(val)){
            val = Comm.getParentsAndSelf(getContext(), mem, ParentsAndSelfExpression);
            cache_mem_parentsAndSelf.set(id,val);
        }
        return val;
    }

    //得到该节点的下一级子节点
    function getSingleLevelChildren(mem) {
        let id = Comm.getIdByItem(mem);
        let val = cache_mem_singleLevelChildren.get(id);
        if(Comm.isNull(val)){
            if (SingleLevelChildrenExpression === undefined) {
                SingleLevelChildrenExpression = getContext().createExpression("child::"+memberTag, null);
            }
            val = SingleLevelChildrenExpression.evaluate(mem, XPathResult.ORDERED_NODE_SNAPSHOT_TYPE, null);
            cache_mem_singleLevelChildren.set(id,val);
        }
        return val;
    }

    //得到所有子节点
    function getChildren(mem) {
        let id = Comm.getIdByItem(mem);
        let val = cache_mem_children.get(id);
        if(Comm.isNull(val)){
            if (ChildrenExpression === undefined) {
                ChildrenExpression = getContext().createExpression("descendant::"+memberTag, null);
            }
            val = ChildrenExpression.evaluate(mem, XPathResult.ORDERED_NODE_SNAPSHOT_TYPE, null);
            cache_mem_children.set(id,val);
        }
        return val;
    }

    //得到所有子节点以及自己
    function getChildrenAndSelf(mem) {
        let id = Comm.getIdByItem(mem);
        let val = cache_mem_childrenAndSelf.get(id);
        if(Comm.isNull(val)){
            if (ChildrenAndSelfExpression === undefined) {
                ChildrenAndSelfExpression = getContext().createExpression("descendant-or-self::"+memberTag, null);
            }
            val = ChildrenAndSelfExpression.evaluate(mem, XPathResult.ORDERED_NODE_SNAPSHOT_TYPE, null);
            cache_mem_childrenAndSelf.set(id,val);
        }
        return val;
    }

    //根据节点id，判断一个节点是否与另一个节点相等
    function equals(mem1, mem2) {
        return Comm.equals(mem1, mem2);
    }

    //判断一个mem1节点是否包含另一个mem2节点
    function contains(mem1, mem2) {
        let result = getContext().evaluate(".//"+memberTag+"[@id='" + Comm.getIdByItem(mem2) + "']", mem1, null, XPathResult.ORDERED_NODE_SNAPSHOT_TYPE, null);
        return result != null && Comm.getLength(result) > 0 ? true : false;
    }

    //从mem1中剥离出mem2
    function stripping(mem1, mem2) {
        let memItems = new Array();
        //将menu树中的mem装换成mem树中的mem
        mem1 = getItem(Comm.getIdByItem(mem1))
        if (contains(mem1, mem2)) {
            //得到mem1下一级节点
            let memChildren = getSingleLevelChildren(mem1);
            for (let i = 0; i < Comm.getLength(memChildren); i++) {
                let oc = Comm.getItem(memChildren, i);
                //两个
                if (!equals(oc, mem2)) {
                    let subOrgItems = stripping(oc, mem2);
                    memItems = memItems.concat(subOrgItems);
                }
            }
        }else {
            memItems.push(mem1);
        }
        return memItems;
    }

    return {
        removeCache : removeCache,
        transform : transform,
        getContext : getContext,
        getRoot : getRoot,
        getItem : getItem,
        //getParent : getParent,
        getParents : getParents,
        getParentsAndSelf : getParentsAndSelf,
        getSingleLevelChildren : getSingleLevelChildren,
        getChildren : getChildren,
        getChildrenAndSelf : getChildrenAndSelf,
        stripping : stripping,
        equals : equals,
        contains : contains
    }
})();