/**
 * Created by changqing on 2016/12/30.
 */

let Comm = ( function () {

    let xmlHttp;

    //根据路径发起请求
    function load(url,data) {
        if(url == null || url == "")
            return;
        if( xmlHttp === undefined ){
            xmlHttp = new XMLHttpRequest();
        }
        if(data != null){
            let i = 0;
            for(let j in data){

                if(i == 0)
                    url +="?";
                else
                    url += "&";
                url += j+"="+data[j];
                i++
            }
        }

        xmlHttp.open("get", url, false);
        xmlHttp.send(null);

        let str = xmlHttp.responseXML;
        return str
    }
    //xml转换成xsl
    function getXsltProcessor(url) {
        let xsltProcessor = new XSLTProcessor();
        xsltProcessor.importStylesheet(Comm.load(url));
        return xsltProcessor;
    }

    function getIdByItem(item){
        return item.attributes.getNamedItem("id").nodeValue
    }
    function getNameByItem(item){
        return item.attributes.getNamedItem("n").nodeValue
    }

    //得到上一级的节点
    function getParent(context, node, expression) {
        if (expression === undefined) {
            expression = context.createExpression("parent::*", null);
        }
        return expression.evaluate(node, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;
    }

    //得到所有父节点
    function getParents(context, node, expression) {
        if (expression === undefined) {
            expression = context.createExpression("ancestor::*", null);
        }
        return expression.evaluate(node, XPathResult.ORDERED_NODE_SNAPSHOT_TYPE, null);
    }

    //得到所有父节点以及自己
    function getParentsAndSelf(context, node, expression) {
        if (expression === undefined) {
            expression = context.createExpression("ancestor-or-self::*", null);
        }
        return expression.evaluate(node, XPathResult.ORDERED_NODE_SNAPSHOT_TYPE, null);
    }

    //根据节点id，判断一个节点是否与另一个节点相等
    function equals(item1, item2) {
        return getIdByItem(item1) == getIdByItem(item2);
    }

    function isNull(val){
        if(val == undefined || val == null || (val.length != undefined && val.length < 1)){
            return true;
        }
        return false;
    }
    function getLength(result) {
        // console.log("result");
        // console.log(result);
        if(result == null){
            return 0;
        }else if (result.length != undefined) {
            return result.length;
        }else if (result.snapshotLength != undefined) {
            return result.snapshotLength;
        }else {
            throw "result不是集合对象";
        }
    }

    function getItem(result, i) {
        if (result.length != undefined) {
            return result[i];
        }
        else if (result.snapshotLength != undefined) {
            return result.snapshotItem(i);
        }
        else {
            throw "result不是集合对象";
        }
    }

    return {
        load : load,//发起请求
        getXsltProcessor : getXsltProcessor,
        getIdByItem : getIdByItem,
        getNameByItem : getNameByItem,
        getParent : getParent,
        getParents : getParents,
        getParentsAndSelf : getParentsAndSelf,
        getLength : getLength,
        getItem : getItem,
        isNull : isNull,
        equals : equals
    }

})();





