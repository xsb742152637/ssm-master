/**
 * Created by xc on 2018/2/7.
 */
var Mem = (function(){
    var context;//xml对象
    var ParentsExpression;
    var ParentsAndSelfExpression;
    var SingleLevelChildrenExpression;
    var ChildrenExpression;
    var ChildrenAndSelfExpression;

    //固定的缓存对象
    var cache_root;
    var cache_mem_item = new Map();
    var cache_mem_parents = new Map();
    var cache_mem_parentsAndSelf = new Map();
    var cache_mem_singleLevelChildren = new Map();
    var cache_mem_children = new Map();
    var cache_mem_childrenAndSelf = new Map();

    //得到成员xml并转换成xsl
    function transform(callback){
        var cal = function(xsltProcessor){
            callback(xsltProcessor.transformToFragment(getContext(), document));
        };
        Comm.getXsltProcessor(cal,Config.getMemTransformPath());
    }

    //得到成员xml
    function getContext() {
        if(context === undefined) {
            var dataUrl = "/app/guide5/getXmlTree.do";
            context = Comm.load(dataUrl,{xmlType:"memberTree",projectId:"memberTree",random:Math.random()});
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
        var val = cache_mem_item.get(id);
        if(Comm.isNull(val)){
            val = getContext().evaluate("//"+memberTag+"[@id='" + id + "']", getRoot(), null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;
            cache_mem_item.set(id,val);
        }
        return val;
    }

    //得到父级节点
    function getParents(mem) {
        var id = Comm.getIdByItem(mem);
        var val = cache_mem_parents.get(id);
        if(Comm.isNull(val)){
            val = Comm.getParents(getContext(), mem, ParentsExpression);
            cache_mem_parents.set(id,val);
        }
        return val;
    }

    //得到父级节点以及自己
    function getParentsAndSelf(mem) {
        var id = Comm.getIdByItem(mem);
        var val = cache_mem_parentsAndSelf.get(id);
        if(Comm.isNull(val)){
            val = Comm.getParentsAndSelf(getContext(), mem, ParentsAndSelfExpression);
            cache_mem_parentsAndSelf.set(id,val);
        }
        return val;
    }

    //得到该节点的下一级子节点
    function getSingleLevelChildren(mem) {
        var id = Comm.getIdByItem(mem);
        var val = cache_mem_singleLevelChildren.get(id);
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
        var id = Comm.getIdByItem(mem);
        var val = cache_mem_children.get(id);
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
        var id = Comm.getIdByItem(mem);
        var val = cache_mem_childrenAndSelf.get(id);
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
        var result = getContext().evaluate(".//"+memberTag+"[@id='" + Comm.getIdByItem(mem2) + "']", mem1, null, XPathResult.ORDERED_NODE_SNAPSHOT_TYPE, null);
        return result != null && Comm.getLength(result) > 0 ? true : false;
    }

    //从mem1中剥离出mem2
    function stripping(mem1, mem2) {
        var memItems = new Array();
        //将menu树中的mem装换成mem树中的mem
        mem1 = getItem(Comm.getIdByItem(mem1))
        if (contains(mem1, mem2)) {
            //得到mem1下一级节点
            var memChildren = getSingleLevelChildren(mem1);
            for (var i = 0; i < Comm.getLength(memChildren); i++) {
                var oc = Comm.getItem(memChildren, i);
                //两个
                if (!equals(oc, mem2)) {
                    var subOrgItems = stripping(oc, mem2);
                    memItems = memItems.concat(subOrgItems);
                }
            }
        }else {
            memItems.push(mem1);
        }
        return memItems;
    }

    return {
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