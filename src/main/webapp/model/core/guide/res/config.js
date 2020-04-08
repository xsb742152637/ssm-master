/**
 * Created by changqing on 2017/11/14.
 */

let Config = ( function () {

    function getMenuTransformPath() {
        return "/model/core/guide/transform/menu.xsl";
    }

    function getMemTransformPath() {
        return "/model/core/guide/transform/member.xsl";
    }

    return {
        getMenuTransformPath : getMenuTransformPath,
        getMemTransformPath : getMemTransformPath
    }

})();
