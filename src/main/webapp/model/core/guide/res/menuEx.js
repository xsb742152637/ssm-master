/**
 * Created by xc on 2018/2/7.
 */

var MenuEx = ( function () {

    //选择
    function select(mem, menu,type) {
        //在menu节点下新增mem节点，新增前判断是否存在mem的某个父级节点，不存在才新增
        Menu.addMemBeforeCheck(menu, mem,type);

        //得到menu节点的所有下级,并进行同上处理
        var menuChildren = Menu.getChildren(menu);
        for (var i = 0; i < Comm.getLength(menuChildren); i++) {
            var menuCh = Comm.getItem(menuChildren, i);
            Menu.removeMemChildrenAndSelf(menuCh, mem,type);
        }

        //处理menu节点所有上级勾选状态
        var menuParents = Menu.getParents(menu);
        //用反序遍历是因为Menu.getParents函数返回父祖级menu节点的集合是从上往下的，而这个地方需要从下往上
        for (var i = Comm.getLength(menuParents) - 1; i >= 0 ; i--) {
            //得到某个上级节点的子节点集合
            var sonMenuAuths = new Array();
            var m_p = Comm.getItem(menuParents, i);
            var sonMenus = Menu.getSingleLevelChildren(m_p);
            if(Comm.getLength(sonMenus) == 0 )
                continue;
            for (var j = 0; j < Comm.getLength(sonMenus); j++) {
                if (Comm.getLength(Menu.findMemParentsAndSelf(Comm.getItem(sonMenus, j), mem,type)) != 0) {
                    sonMenuAuths.push(getCheck());
                }
                else {
                    sonMenuAuths.push(getUncheck());
                }
            }

            if (isAllAuth(sonMenuAuths)) {
                // console.log("该上级是全选");
                //所有的子级menu节点都具有该mem节点，则该menu节点增加该mem节点
                //Menu.addMemBeforeCheck函数会判断该menu节点包含本级mem节点和上级mem节点的情况
                select(mem,m_p, type);

                // console.log("删除多余成员");
                //兄弟全部菜单包含该成员，上级菜单全选，兄弟菜单去掉该成员,兄弟的子节点不管，因为兄弟全选，其子节点必然已经去掉了该成员
                for (var j = 0; j < Comm.getLength(sonMenus); j++) {
                    Menu.removeMemChildrenAndSelf(Comm.getItem(sonMenus, j), mem,type);
                }
            }else{
                //判断是否包含其下级某个mem,如果包含，在其它menu上删除该mem的下级
                var c_mems = Mem.getChildren(mem);
                if(c_mems != null && Comm.getLength(c_mems) > 0){
                    var isHave = false;
                    for(var m = 0;m < Comm.getLength(c_mems);m++){
                        var c_mem = Comm.getItem(c_mems,m);
                        var c_sonMenuAuths = new Array();
                        for (var j = 0; j < Comm.getLength(sonMenus); j++) {
                            if (Comm.getLength(Menu.findMemParentsAndSelf(Comm.getItem(sonMenus, j), c_mem,type)) != 0) {
                                c_sonMenuAuths.push(getCheck());
                            }else {
                                c_sonMenuAuths.push(getUncheck());
                            }
                        }
                        if (isAllAuth(c_sonMenuAuths)) {
                            // console.log("该上级是全选");
                            //所有的子级menu节点都具有该mem节点，则该menu节点增加该mem节点
                            //Menu.addMemBeforeCheck函数会判断该menu节点包含本级mem节点和上级mem节点的情况
                            mem,(c_mem,m_p, type);
                            //删除该menu节点中本mem节点的下级mem节点
                            Menu.removeMemChildren(m_p, c_mem,type);

                            // console.log("删除多余成员");
                            //兄弟全部菜单包含该成员，上级菜单全选，兄弟菜单去掉该成员,兄弟的子节点不管，因为兄弟全选，其子节点必然已经去掉了该成员
                            for (var j = 0; j < Comm.getLength(sonMenus); j++) {
                                Menu.removeMemChildrenAndSelf(Comm.getItem(sonMenus, j), c_mem,type);
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
        mergeMem(Menu.getParentsAndSelf(menu),mem,type);
        //对所有下级menu的mem进行合并
        mergeMem(Menu.getChildren(menu),mem,type);
    }

    //取消选择
    function reverseSelect(mem, menu,type) {
        //处理上级菜单
        var menuParents = Menu.getParents(menu);
        for (var i = 0; i < Comm.getLength(menuParents); i++) {
            var p_item = Comm.getItem(menuParents, i);
            menuStripping(menu,p_item,mem,type);
        }

        //处理自己
        //处理menu节点中含mem节点及其下级节点的情况,直接删除
        Menu.removeMemChildrenAndSelf(menu, mem,type);

        //处理menu节点中含mem上级节点的情况,进行剥离
        var memParents = Menu.findMemParents(menu, mem,type);
        for (var j = Comm.getLength(memParents) - 1; j >= 0; j--) {
            // 对mem节点进行剥离
            var p_item = Comm.getItem(memParents, j);
            var strippingMemItems = Mem.stripping(p_item, mem);
            //删除原mem节点
            Menu.removeMem(menu, p_item,type);
            //新增剥离后的mem
            for (var n = 0; n < Comm.getLength(strippingMemItems); n++) {
                select(Comm.getItem(strippingMemItems, n),menu, type);
            }
        }

        //处理下级菜单
        var menuChildren = Menu.getChildren(menu);
        for (var i = Comm.getLength(menuChildren) - 1; i >= 0; i--) {
            var ch_item = Comm.getItem(menuChildren, i);
            //处理menu节点中含mem节点及其下级节点的情况,直接删除
            Menu.removeMemChildrenAndSelf(ch_item, mem,type);

            //处理menu节点中含mem上级节点的情况,进行剥离
            memParents = Menu.findMemParents(ch_item, mem,type);
            for (var j = Comm.getLength(memParents) - 1; j >= 0; j--) {
                // 对mem节点进行剥离
                var p_item = Comm.getItem(memParents, j);
                var strippingMemItems = Mem.stripping(p_item, mem);
                //删除原mem节点
                Menu.removeMem(ch_item, p_item,type);
                //新增剥离后的mem
                for (var n = 0; n < Comm.getLength(strippingMemItems); n++) {
                    select(Comm.getItem(strippingMemItems, n),ch_item, type);
                }
            }

        }
    }

    //从mem1中剥离出mem2
    function menuStripping(menuOld,menu,mem,type) {
        //处理上级
        //得到所有上级以及自己，如果上级存在mem（如果上级存在mem的上级，上级留下剥离出来的人），删除mem及其下级mem，并为兄弟节点新增mem。
        var memParents = Menu.findMemParentsAndSelf(menu, mem,type);
        if(Comm.getLength(memParents) > 0){
            for (var j = Comm.getLength(memParents) - 1; j >= 0; j--) {
                var rp_item = Comm.getItem(memParents, j);
                //如果上级存在mem，删除mem及其下级mem，并为兄弟节点新增mem
                if(Comm.equals(rp_item,mem)){
                    Menu.removeMemChildrenAndSelf(menu, rp_item,type);
                }else{
                    //上级menu存在mem的上级，进行剥离
                    var strippingMemItems = Mem.stripping(rp_item, mem);
                    //删除原mem节点
                    Menu.removeMem(menu, rp_item,type);
                    //新增剥离剩下的mem
                    for (var i = 0; i < Comm.getLength(strippingMemItems); i++) {
                        select(Comm.getItem(strippingMemItems, i),menu, type);
                    }
                }
            }

            //得到该menu节点的兄弟节点
            var xd_items = Menu.getSingleLevelChildren(menu);
            for(var j = 0;j < Comm.getLength(xd_items);j++){
                var xd_item = Comm.getItem(xd_items,j);
                if(Comm.equals(xd_item,menuOld)){
                    //处理menu节点中含mem上级节点的情况,进行剥离
                    var memParents = Menu.findMemParents(xd_item, mem,type);
                    for (var jjj = Comm.getLength(memParents) - 1; jjj >= 0; jjj--) {
                        // 对mem节点进行剥离
                        var p_item = Comm.getItem(memParents, jjj);
                        var strippingMemItems = Mem.stripping(p_item, mem);
                        //删除原mem节点
                        Menu.removeMem(xd_item, p_item,type);
                        //新增剥离后的mem
                        for (var n = 0; n < Comm.getLength(strippingMemItems); n++) {
                            select(Comm.getItem(strippingMemItems, n),xd_item, type);
                        }
                    }
                }else if(Menu.contains(xd_item,menuOld)){
                    Menu.addMemBeforeCheck(xd_item, mem,type);
                    menuStripping(menuOld,xd_item,mem,type);
                }
            }

            for(var j = 0;j < Comm.getLength(xd_items);j++){
                var xd_item = Comm.getItem(xd_items,j);
                if(!Menu.contains(xd_item,menuOld)){
                    select(mem,xd_item, type);
                }
            }
        }else{
            //得到该成员在该菜单中的所以下级
            var memChildrens = Menu.findMemChildren(menu, mem,type);
            if(Comm.getLength(memChildrens) > 0){
                for(var m = 0;m < Comm.getLength(memChildrens);m++){
                    //删除原mem节点
                    Menu.removeMem(menu, Comm.getItem(memChildrens,m),type);
                }
                //得到该menu节点的兄弟节点
                var xd_items = Menu.getSingleLevelChildren(menu);
                for(var j = 0;j < Comm.getLength(xd_items);j++){
                    var xd_item = Comm.getItem(xd_items,j);
                    if(Comm.equals(xd_item,menuOld)){
                        //处理menu节点中含mem上级节点的情况,进行剥离
                        var memParents = Menu.findMemParents(xd_item, mem,type);
                        for (var jjj = Comm.getLength(memParents) - 1; jjj >= 0; jjj--) {
                            // 对mem节点进行剥离
                            var p_item = Comm.getItem(memParents, jjj);
                            var strippingMemItems = Mem.stripping(p_item, mem);
                            //删除原mem节点
                            Menu.removeMem(xd_item, p_item,type);
                            //新增剥离后的mem
                            for (var n = 0; n < Comm.getLength(strippingMemItems); n++) {
                                select(Comm.getItem(strippingMemItems, n),xd_item, type);
                            }
                        }
                    }else if(Menu.contains(xd_item,menuOld)){
                        for(var m = 0;m < Comm.getLength(memChildrens);m++){
                            Menu.addMemBeforeCheck(xd_item, Comm.getItem(memChildrens,m),type);
                        }
                        menuStripping(menuOld,xd_item,mem,type);
                    }
                }
                for(var j = 0;j < Comm.getLength(xd_items);j++){
                    var xd_item = Comm.getItem(xd_items,j);
                    if(!Menu.contains(xd_item,menuOld)){
                        for(var m = 0;m < Comm.getLength(memChildrens);m++){
                            select(Comm.getItem(memChildrens,m),xd_item, type);
                        }
                    }
                }
            }
        }
    }

    //合并菜单上的mem
    function mergeMem(menus,mem,type){
        for(var o = Comm.getLength(menus)-1;o >=0;o--){
            var menuItem = Comm.getItem(menus,o);
            // console.log(menuItem);
            //对该menu节点的mem进行合并
            var mems = Menu.getAllMems(menuItem,type);
            // console.log("现有人员：");
            // console.log(mems);
            if(Comm.getLength(mems) < 1)
                continue;
            //得到该mem节点的所有上级
            var p_mems = Mem.getParents(mem);
            //从下往上循环
            for(var i = Comm.getLength(p_mems)-1;i >= 0;i--){
                mems = Menu.getAllMems(menuItem,type);
                var p_mem = Comm.getItem(p_mems,i);
                // console.log("上级节点：");
                // console.log(p_mem);
                var p_c_mem = Mem.getSingleLevelChildren(p_mem);

                // console.log("兄弟节点：");
                // console.log(p_c_mem);
                var j = 0;
                for(var m = 0;m < Comm.getLength(p_c_mem);m++){
                    var isHave = false;
                    for(var n = 0;n < Comm.getLength(mems);n++){
                        if(Comm.equals(Comm.getItem(p_c_mem,m),Comm.getItem(mems,n))){
                            j++;
                            isHave = true;
                            break;
                        }
                    }
                    if(!isHave){
                        var menus2 = Menu.getParents(menuItem);
                        for(var n = Comm.getLength(menus2)-1;n >=0;n--){
                            var menuItem2 = Comm.getItem(menus2,n);
                            var mems2 = Menu.getAllMems(menuItem2,type);
                            if(Comm.getLength(mems2) < 1)
                                continue;
                            for(var nn = Comm.getLength(mems2)-1;nn >=0;nn--){
                                if(Comm.equals(Comm.getItem(p_c_mem,m),Comm.getItem(mems2,nn))){
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
                if(j > 0 && j == Comm.getLength(p_c_mem)){
                    select(p_mem,menuItem, type);
                    for(var m = 0;m < Comm.getLength(p_c_mem);m++){
                        Menu.removeMem(menuItem, Comm.getItem(p_c_mem,m),type);
                    }
                }
            }
        }
    }

    var _check = 'checkImg tree-icon  check';//全选
    var _checkmix = 'checkImg tree-icon  checkmix';//半选
    var _uncheck = 'checkImg tree-icon  uncheck';//不选

    function getCheck() {
        return _check;
    }

    function getCheckMix() {
        return _checkmix;
    }

    function getUncheck() {
        return _uncheck;
    }

    function  isAllAuth(sonMenus) {
        if (sonMenus.length == 0) {
            throw "数组为空";
        }
        for (var i = 0; i < Comm.getLength(sonMenus); i++) {
            if (Comm.getItem(sonMenus, i) != getCheck()) {
                return false;
            }
        }
        return true;
    }

    function isAllNotAuth(sonMenus) {
        if (sonMenus.length == 0) {
            throw "数组为空";
        }
        for (var i = 0; i < Comm.getLength(sonMenus); i++) {
            if (Comm.getItem(sonMenus, i) != getUncheck()) {
                return false;
            }
        }
        return true;
    }

    //更新页面勾选情况
    function setAuth(menu, mem,type) {
        var menuId = Comm.getIdByItem(menu);

        var img = "#img_"+type+"_";
        if (Comm.getLength(Menu.findMemParentsAndSelf(menu, mem,type)) != 0) {
            //当前menu节点具有mem节点或mem节点的父级，则直接得到该menu节点的所有子孙menu节点，全部状态置为勾选
            var children = Menu.getChildren(menu);
            for (var i = 0; i < Comm.getLength(children); i++) {
                $(img + Comm.getItem(children, i).attributes.getNamedItem("id").nodeValue).attr('class', getCheck());
            }
            $(img + menuId).attr('class', getCheck());
            return getCheck();
        }else {

            //得到该菜单节点的所有子节点
            var sonMenus = Menu.getSingleLevelChildren(menu);

            //该menu无下级，该menu包含mem的下级，则半选，反之则不选
            if (Comm.getLength(sonMenus) == 0) {
                if (Comm.getLength(Menu.findMemChildren(menu, mem,type)) != 0) {
                    //如果本menu节点包含mem节点的子孙级节点，则本menu节点的状态是混合
                    $(img + menuId).attr('class', getCheckMix());
                    return getCheckMix();
                }else {
                    var p_m = Menu.getParentsAndSelf(menu);
                    for(var i = 0;i < Comm.getLength(p_m);i++){
                        if(Comm.getLength(Menu.findMemChildren(Comm.getItem(p_m,i), mem,type)) != 0){
                            $(img + menuId).attr('class', getCheckMix());
                            return getCheckMix();
                        }
                    }
                    //如果本menu节点不包含mem节点的子孙级节点，则本menu节点的状态是不勾选
                    $(img + menuId).attr('class', getUncheck());
                    return getUncheck();
                }
            }else {
                //根据子级menu节点的状态决定本级menu节点的状态
                var sonMenuAuths = new Array();
                for (var i = 0; i < Comm.getLength(sonMenus); i++) {
                    //递归循环子级menu节点，得到子级menu节点的状态
                    sonMenuAuths.push(setAuth(Comm.getItem(sonMenus, i), mem,type));
                }
                var allAuth = isAllAuth(sonMenuAuths);
                //判断子级menu节点是否都是包含了mem节点或其父祖级
                if (allAuth == true) {
                    //都包含了，则本级menu节点状态是勾选
                    $(img + menuId).attr('class', getCheck());
                    return getCheck();
                }
                else {
                    var allNotAuth = isAllNotAuth(sonMenuAuths);
                    //判断子级menu节点是否都不包含mem节点或其上级
                    if (allNotAuth == true) {
                        //判断该菜单是否包含该成员的下级成员
                        var p_m = Menu.getParentsAndSelf(menu);
                        for(var i = 0;i < Comm.getLength(p_m);i++){
                            if(Comm.getLength(Menu.findMemChildren(Comm.getItem(p_m,i), mem,type)) != 0){
                                $(img + menuId).attr('class', getCheckMix());
                                return getCheckMix();
                            }
                        }
                        //都不包含，则本级menu节点状态是不勾选
                        $(img + menuId).attr('class', getUncheck());
                        return getUncheck();
                    }
                    else {
                        //子级menu节点的状态是混合的，则本级menu节点状态也是混合
                        $(img + menuId).attr('class', getCheckMix());
                        return getCheckMix();
                    }
                }
            }

        }

    }

    return {
        getCheck : getCheck,
        getCheckMix : getCheckMix,
        getUncheck : getUncheck,
        select : select,
        reverseSelect : reverseSelect,
        setAuth : setAuth
    }

})();


