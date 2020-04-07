/**
 * Created by changqing on 2017/11/14.
 */

var Config = ( function () {

    function getMenuTransformPath() {
        return "/app/guide5/transform/menu.xsl";
    }

    function getMemTransformPath() {
        return "/app/guide5/transform/member.xsl";
    }

    function getOrgDataPath() {
        return "/app/guide5/data/org.xml";
    }

    return {
        getMenuTransformPath : getMenuTransformPath,
        getMemTransformPath : getMemTransformPath,
        getOrgDataPath : getOrgDataPath
    }

})();
