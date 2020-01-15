//(function (undefined) {
/**
 * 创建回调函数，该函数可以保留最初在对象创建期间使用的参数。<br />
 *  var a = objVar.createCallback(method,context);<br />
 * 使用 createCallback 函数创建回调函数，该函数可以保留最初在对象创建期间使用的参数。 虽然不带参数使用回调，但此函数将调用包含参数的实际函数。 createCallback 函数在为必须保留参数的 DOM 事件设置处理程序时很有用，而不管 DOM 事件处理程序需要具有仅作为参数的事件对象的函数。 这样允许在调用函数时，将事件作为第一个参数，并将上下文作为第二个参数。 如果回调使用任意参数列表调用，则附加上下文。<br />
 * @param method  为其创建回调的函数。
 * @param context  函数的参数。
 * @returns {Function} 回调函数。
 *
 */
Function.createCallback = function (method, context) {
    return function () {
        var l = arguments.length;
        if (l > 0) {
            var args = [];
            for (var i = 0; i < l; i++) {
                args[i] = arguments[i];
            }
            args[l] = context;
            return method.apply(this, args);
        }
        return method.call(this, context);
    }
}

/**
 * 创建委托函数，该函数可以保留首次在对象创建期间使用的上下文。 <br />
 * var a = objVar.createDelegate(instance,method); <br />
 * 在设置事件处理程序以指向对象方法时，使用 createDelegate 函数。 设置事件处理程序以指向必须在其范围内使用 this 指针的对象方法时，createDelegate 函数很有用。 createDelegate 函数也可用于空实例。
 * @param instance 将成为该函数上下文的对象实例。
 * @param method 从中创建委托的函数。
 * @returns {Function} 委托函数。
 */
Function.createDelegate = function (instance, method) {
    return function () {
        return method.apply(instance, arguments);
    };
};

/**
 * 不执行任何操作的函数。
 * var a = objVar.emptyMethod();
 * @type {Function}
 */
Function.emptyFunction = Function.emptyMethod = function () {
}


//判断两个数组是否相等
Function.isEquals = function (arr1, arr2) {
    var flag = true;
    if (arr1.length !== arr2.length) {
        flag = false
    } else {
        arr1.forEach(function(item){
            if (arr2.indexOf(item) === -1) {
                flag = false
            }
        })
    }
    return flag;
}
String.Empty = "";

/**
 * 确定 String 对象的末尾是否与指定的字符串匹配。
 * 使用 endsWith 函数可确定 String 对象的末尾是否与指定的字符串匹配。 endsWith 函数区分大小写。
 * @param suffix 要与 String 对象的末尾进行匹配的字符串。
 * @returns {boolean}  如果 String 对象的末尾与 suffix 匹配，则为 true；否则为 false。
 */
String.prototype.endsWith = function (suffix) {
    return (this.substr(this.length - suffix.length) === suffix);
}
/**
 * 确定 String 对象的末尾是否与指定的字符串匹配。
 * 使用 endsWithIgnoreCase 函数可确定 String 对象的末尾是否与指定的字符串匹配。 endsWithIgnoreCase 函数不区分大小写。
 * @param suffix 要与 String 对象的末尾进行匹配的字符串。
 * @returns {boolean}  如果 String 对象的末尾与 suffix 匹配，则为 true；否则为 false。
 */
String.prototype.endsWithIgnoreCase = function (suffix) {
    return this.toLowerCase().endsWith(suffix.toLowerCase());
}
/**
 * 确定 String 对象的开头部分是否与指定的字符串匹配。<br />
 * 使用 startsWith 函数可确定 String 对象的开头部分是否与指定的字符串匹配。 startsWith 函数区分大小写。
 * @param prefix 要与 String 对象的开头部分进行匹配的字符串。
 * @returns {boolean} 如果 String 对象的开头部分与 prefix 匹配，则该值为 true；否则为 false。
 */
String.prototype.startsWith = function (prefix) {
    return (this.substr(0, prefix.length) === prefix);
}
/**
 * 确定 String 对象的开头部分是否与指定的字符串匹配。<br />
 * 使用 startsWithIgnoreCase 函数可确定 String 对象的开头部分是否与指定的字符串匹配。 startsWithIgnoreCase 函数不区分大小写。
 * @param prefix 要与 String 对象的开头部分进行匹配的字符串。
 * @returns {boolean} 如果 String 对象的开头部分与 prefix 匹配，则该值为 true；否则为 false。
 */
String.prototype.startsWithIgnoreCase = function (prefix) {
    return this.toLowerCase().startsWith(prefix.toLowerCase());
}
/**
 * 从 String 对象移除前导空白字符和尾随空白字符。<br />
 * 使用 trim 函数可以从当前 String 对象移除前导空白字符和尾随空白字符。 空格和制表符都属于空白字符。
 * @returns {string} 一个字符串副本，其中从该字符串的开头和末尾移除了所有空白字符。
 */
String.prototype.trim = function () {
    return this.replace(/^\s+|\s+$/g, "");
}
/**
 * 从 String 对象移除尾随空白字符。<br />
 * 使用 trimEnd 函数可以从 String 对象移除尾随空白字符。 空格和制表符都属于空白字符。
 * @returns {string} 字符串的一个副本，其中移除了该字符串末尾的所有空白字符。
 */
String.prototype.trimEnd = function () {
    return this.replace(/\s+$/, '');
}
/**
 * 从 String 对象中移除前导空白字符。<br />
 * 使用 trimStart 函数可以从当前 String 对象中移除前导空白字符。 空格和制表符都属于空白字符。
 * @returns {string} 字符串副本，原字符串开始位置处的所有空白字符都已移除。
 */
String.prototype.trimStart = function () {
    return this.replace(/^\s+/, '');
}
/**
 * 指示指定的字符串是 null 还是 Empty 字符串。
 * @param value 要测试的字符串。
 * @returns {boolean} 如果 value 参数为 null 或空字符串 ("")，则为 true；否则为 false。
 */
String.isNullOrEmpty = function (value) {
    return value === undefined || value === null || value.length === 0;
};
/**
 * 指示指定的字符串是 null、空还是仅由空白字符组成。
 * @param value 要测试的字符串。
 * @returns {boolean} 如果 value 参数为 null 或 String.Empty，或者如果 value 仅由空白字符组成，则为 true。
 */
String.isNullOrWhiteSpace = function (value) {
    return String.isNullOrEmpty(value) || $.trim(value).length === 0;
};

/**
 * 将元字符全部替换成现在的字符
 * @param oldVal 旧的
 * @param newVal 新的
 */
String.prototype.replaceAll = function (oldVal, newVal) {
    var tmpUnicodeSB = new StringBuilder();
    for (var i = 0, j = oldVal.length; i < j; i++) {
        var tmpUnicode = oldVal.charCodeAt(i).toString(16);
        tmpUnicodeSB.append("\\u");
        for (x = tmpUnicode.length; x < 4; x++) {
            tmpUnicodeSB.append("0");
        }
        tmpUnicodeSB.append(tmpUnicode);
    }
    return this.replace(new RegExp(tmpUnicodeSB.toString(), "gm"), newVal);
};

/**
 * 添加getBytesLength方法，用于得到字节数。中文为2个字节
 */
String.prototype.lengthb = function () {
    var cArr = this.match(/[^\x00-\xff]/ig);
    return this.length + (cArr == null ? 0 : cArr.length);
};

/**
 * 查找字符不区分大小写并返回真 true /false
 */
String.prototype.find = function (searchValue) {
    var rel = "";
    if (this.search(new RegExp(searchValue, "i")) != -1) {
        var len = this.search(new RegExp(searchValue, "i"));
        var elen = searchValue.length + len;
        rel = this.substring(len, elen);
    }
    return !rel.isEmpty();
};
/**
 * 是否为空
 *
 * @returns {Boolean}
 */
String.prototype.isEmpty = function () {
    return this.trim().length < 1;
};
/**
 * 是否为数字
 *
 * @returns {Boolean}
 */
String.prototype.isNumber = function () {
    //return !this.isEmpty() && !isNaN(this);
    return !isNaN(parseFloat(this)) && isFinite(this);
};
/**
 * 是否为int类型
 */
String.prototype.isInt = function () {
    return this.isNumber() && /^[1-9]*[1-9][0-9]*$/.test(this);
};
/**
 * 是否为double类型
 */
String.prototype.isDouble = function () {
    return this.isNumber() && /^[-\+]?\d+(\.\d+)?$/.test(this);
};
/**
 * 是否事件类型
 */
String.prototype.isDate = function () {
    return !this.isEmpty()
        && ((this.isNumber() && Date.parseDate(this).getTime()) || (new Date(
            this).getTime()));
};
/**
 * 是否email类型
 */
String.prototype.isEmail = function () {
    return !this.isEmpty() && /^\w*@\w*\.\w*$/.test(this);
};
/**
 * 是否邮编类型
 */
String.prototype.isZipCode = function () {
    return this.isNumber() && /(^$)|(^\d{6}$)/gi.test(this);
};
/**
 * 是否移动电话（手机）
 */
String.prototype.isMobile = function () {
    return this.isNumber() && /^(1[3,5,8,7]{1}[\d]{9})$/.test(this);
};
/**
 * 是否电话
 */
String.prototype.isPhone = function () {
    return this.isNumber()
        && (/^[1-9]{1}[0-9]{5,8}$/.test(this) || this.length > 9
            && /^[0][1-9]{2,3}-[0-9]{5,10}$/.test(this));
};
/**
 * 是否网站地址
 */
String.prototype.isURL = function () {
    return !this.isEmpty()
        && /^http:\/\/[w]+\.[w]+[\/=\?%\-&_~`@[\]\':+!]*([^<>\"\"])*$/
            .test(this);
};
/**
 * 是否英文
 */
String.prototype.isEnglish = function () {
    return !this.isEmpty() && /^[A-Za-z]+$/.test(this);
};
/**
 * 是否中文
 */
String.prototype.isChinese = function () {
    return !this.isEmpty() && /^[\u0391-\uFFE5]+$/.test(this);
};
/**
 * 字符比较不区分大小写 类型[boolean,number,string]
 */
String.prototype.equalsIgnoreCase = function () {
    return arguments[0] != null && this.toUpperCase() == ("" + arguments[0]).toUpperCase();
}
/**
 * 字符比较区分大小写 类型[boolean,number,string]
 */
String.prototype.equals = function () {
    return arguments[0] != null && this == "" + arguments[0];
}

/**
 * 将 String 对象中的每个格式项替换为相应对象值的文本等效项。
 * @param format 格式字符串。
 * @param args... 要设置其格式的列表。
 * @returns {*} 具有所应用格式设置的字符串副本。
 */
String.format = function () {
    if (arguments.length == 0)
        return null;

    var str = arguments[0];
    for (var i = 1; i < arguments.length; i++) {
        var re = new RegExp('\\{' + (i - 1) + '\\}', 'g');
        str = str.replace(re, arguments[i]);
    }
    return str;
}
/**
 * 占位符替换源字符内容s='{0}'
 */
String.prototype.format = function () {
    if (arguments.length > 0) {
        for (var s = this, i = 0; i < arguments.length; i++)
            s = s.replace(new RegExp("\\{" + i + "\\}", "g"), arguments[i]);
        return s;
    }
    return this;
};

/**
 * 将逻辑值的字符串表示形式转换为其 Boolean 等效对象。 此函数是静态的，可在不创建对象实例的情况下调用。
 * @param value true 或 false 的字符串表示形式。
 * @returns {*} 对应于 value 参数的布尔值（true 或 false）。
 */
Boolean.parse = function (value) {
    switch (typeof (value)) {
        case 'number':
            if (value == 1) return true;
            break;
        case 'string':
            switch (value.trim().toLowerCase()) {
                case 't':
                    return true;
                    break;
                case 'true':
                    return true;
                    break;
                case '1':
                    return true;
                    break;
            }
            break;
        case 'boolean':
            return value;
    }
    return false;
};

/**
 * 将一个元素添加到 Array 对象的末尾。
 * @param item  要添加到数组的对象。
 * @returns {*}
 */
Array.prototype.add = function (item) {
    this[this.length] = item;
    return this;
}
/**
 * 将指定的 Array 对象的所有元素复制到数组的末尾。
 * @param items 要追加的项的数组。
 * @returns {*}
 */
Array.prototype.addRange = function (items) {
    this.push.apply(this, items);
    return this;
}
/**
 * 从 Array 实例移除所有元素。
 * @returns {*}
 */
Array.prototype.clear = function () {
    this.length = 0;
    return this;
}
/**
 * 创建 Array 对象的浅表副本。
 * @returns {Array}
 */
Arrays = {
    clone: function (arr) {
        return arr.length === 1 ? [arr[0]] : Array.apply(null, arr);
    }
};
Array.prototype.clone = function () {
    //return this.length === 1 ? [this[0]] : this.apply(null, this);
    return Arrays.clone(this);
}

/**
 * 将单个项插入 Array 对象中的指定索引处。
 * @param index 应插入 item 的索引位置。
 * @param item  要添加到数组的对象。
 * @returns {*}
 */
Array.prototype.insert = function (index, item) {
    this.splice(index, 0, item);
    return this;
}
/**
 * 确定指定对象是否是 Array 对象中的元素。
 * @param item 要在数组中查找的对象。
 * @returns {boolean}
 */
Array.prototype.contains = function (item) {
    return (this.indexOf(item) >= 0);
}

/**
 * 去重复
 * @returns {Array}
 */
Array.prototype.unique = function () {
    var results = [];
    var _remove = [];
    for (var i = 0, j = this.length; i < j; i++) {
        if (results.indexOf(this[i]) == -1) {
            results.push(this[i]);
        } else {
            _remove.push(this[i]);
        }
    }
    this.length = 0;
    this.push.apply(this, results);
    return _remove;
};
/**
 * 去重复
 *
 * @return
 */
Array.prototype.distinct = function () {
    var i = 0;
    while (i < this.length) {
        var current = this[i];
        for (var k = this.length; k > i; k--) {
            if (this[k] === current) {
                this.splice(k, 1);
            }
        }
        i++;
    }
};
/**
 * 从 Array 对象中移除指定的所有项。
 * @param value
 * @returns {Array}
 */
Array.prototype.remove = function (value) {
    var _remove = [];
    var i = this.indexOf.apply(this, arguments);
    while (i >= 0) {
        _remove.push(this.splice(i, 1));
        i = this.indexOf.apply(this, arguments);
    }
    return _remove;
};
/**
 * 搜索 Array 对象的指定元素并返回该元素的索引。
 * @param value
 * @returns {number}
 */
Array.prototype.indexOf = function (value) {
    if (arguments.length > 1 && arguments[1] == true) {
        var tmp = value.toString().toLowerCase();
        for (var i = 0, j = this.length; i < j; i++) {
            if (this[i].toString().toLowerCase() == tmp) return i;
        }
    } else {
        for (var i = 0, j = this.length; i < j; i++) {
            if (this[i] == value) return i;
        }
    }
    return -1;
};
/**
 * 给number Array添加获取对象对大值的方法
 */
Array.prototype.max = function () {
    var i, max = this[0];
    for (i = 1; i < this.length; i++) {
        if (max < this[i]) {
            max = this[i];
        }
    }
    return max;
};
/**
 * 给number Array添加获取对象对小值的方法
 */
Array.prototype.min = function () {
    var i, min = this[0];
    for (i = 1; i < this.length; i++) {
        if (min > this[i]) {
            min = this[i];
        }
    }
    return min;
};

/**
 * 对Date的扩展，将 Date 转化为指定格式的 String <br />
 * 月(M)、日(d)、12小时(h)、24小时(H)、分(m)、秒(s)、周(E)、季度(q) 可以用 1-2 个占位符 <br />
 * 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字) <br />
 * eg:<br />
 * (new Date()).pattern("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423 <br />
 * (new Date()).pattern("yyyy- MM-dd E HH:mm:ss") ==> 2009-03-10 二 20:09:04  <br />
 * (new Date()).pattern("yyyy-MM- dd EE hh:mm:ss") ==> 2009-03-10 周二 08:09:04  <br />
 * (new Date()).pattern("yyyy- MM-dd EEE hh:mm:ss") ==> 2009-03-10 星期二 08:09:04  <br />
 * (new Date()).pattern("yyyy-M-d h:m:s.S") ==> 2006-7-2 8:9:4.18 <br />

 * @param fmt
 * @returns {*}
 */
Date.prototype.pattern = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours() % 12 == 0 ? 12 : this.getHours() % 12, //小时
        "H+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    var week = {
        "0": "\u65e5",
        "1": "\u4e00",
        "2": "\u4e8c",
        "3": "\u4e09",
        "4": "\u56db",
        "5": "\u4e94",
        "6": "\u516d"
    };
    if (/(y+)/.test(fmt)) {
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
    if (/(E+)/.test(fmt)) {
        fmt = fmt.replace(RegExp.$1, ((RegExp.$1.length > 1) ? (RegExp.$1.length > 2 ? "\u661f\u671f" : "\u5468") : "") + week[this.getDay() + ""]);
    }
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(fmt)) {
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        }
    }
    return fmt;
};

Date.prototype.format = function (mask) {
    var d = this;
    var zeroize = function (value, length) {
        if (!length) {
            length = 2;
        }
        value = String(value);
        for (var i = 0, zeros = ""; i < (length - value.length); i++) {
            zeros += "0";
        }
        return zeros + value;
    };
    return mask
        .replace(
        /"[^"]*"|'[^']*'|\b(?:d{1,4}|m{1,4}|yy(?:yy)?|([hHMstT])\1?|[lLZ])\b/g,
        function ($0) {
            switch ($0) {
                case "d":
                    return d.getDate();
                case "dd":
                    return zeroize(d.getDate());
                case "ddd":
                    return [ "Sun", "Mon", "Tue", "Wed", "Thr", "Fri",
                        "Sat" ][d.getDay()];
                case "dddd":
                    return [ "Sunday", "Monday", "Tuesday",
                        "Wednesday", "Thursday", "Friday",
                        "Saturday" ][d.getDay()];
                case "M":
                    return d.getMonth() + 1;
                case "MM":
                    return zeroize(d.getMonth() + 1);
                case "MMM":
                    return [ "Jan", "Feb", "Mar", "Apr", "May", "Jun",
                        "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" ][d
                        .getMonth()];
                case "MMMM":
                    return [ "January", "February", "March", "April",
                        "May", "June", "July", "August",
                        "September", "October", "November",
                        "December" ][d.getMonth()];
                case "yy":
                    return String(d.getFullYear()).substr(2);
                case "yyyy":
                    return d.getFullYear();
                case "h":
                    return d.getHours() % 12 || 12;
                case "hh":
                    return zeroize(d.getHours() % 12 || 12);
                case "H":
                    return d.getHours();
                case "HH":
                    return zeroize(d.getHours());
                case "m":
                    return d.getMinutes();
                case "mm":
                    return zeroize(d.getMinutes());
                case "s":
                    return d.getSeconds();
                case "ss":
                    return zeroize(d.getSeconds());
                case "l":
                    return zeroize(d.getMilliseconds(), 3);
                case "L":
                    var m = d.getMilliseconds();
                    if (m > 99) {
                        m = Math.round(m / 10);
                    }
                    return zeroize(m);
                case "tt":
                    return d.getHours() < 12 ? "am" : "pm";
                case "TT":
                    return d.getHours() < 12 ? "AM" : "PM";
                case "Z":
                    return d.toUTCString().match(/[A-Z]+$/);
                default:
                    return $0.substr(1, $0.length - 2);
            }
        });
};

/**
 * 将dateString日期字符串转换为javascript日期
 * @param dateString:日期字符串
 * @param formatString:日期格式，默认值为yyyy-MM-dd HH:mm:ss
 */
Date.parseDate = function (dateString, formatString) {
    if (typeof(dateString) != 'undefined') {
        if (typeof(formatString) == 'undefined')
            formatString = 'yyyy-MM-dd HH:mm:ss';

        if (dateString == "") {
            return;
        }

        var _y4 = "([0-9]{4})";
        /** year : /yyyy/ */
        var _y2 = "([0-9]{2})";
        /** year : /yy/ */
        var _yi = -1;
        /** index year */
        var _M2 = "(0[1-9]|1[0-2])";
        /** month : /MM/ */
        var _M1 = "([1-9]|1[0-2])";
        /** month : /M/ */
        var _Mi = -1;
        /** index month */
        var _d2 = "(0[1-9]|[1-2][0-9]|30|31)";
        /** day : /dd/ */
        var _d1 = "([1-9]|[1-2][0-9]|30|31)";
        /** day : /d/ */
        var _di = -1;
        /** index day */
        var _H2 = "([0-1][0-9]|20|21|22|23)";
        /** hour : /HH/ */
        var _H1 = "([0-9]|1[0-9]|20|21|22|23)";
        /** hour : /H/ */
        var _Hi = -1;
        /** index hour */
        var _m2 = "([0-5][0-9])";
        /** minute : /mm/ */
        var _m1 = "([0-9]|[1-5][0-9])";
        /** minute : /m/ */
        var _mi = -1;
        /** index minute */
        var _s2 = "([0-5][0-9])";
        /** second : /ss/ */
        var _s1 = "([0-9]|[1-5][0-9])";
        /** second : /s/ */
        var _si = -1;
        /** index month */
        var regexp;
        //

        var reg = formatString;
        reg = reg.replace(/yyyy/, _y4);
        reg = reg.replace(/yy/, _y2);
        reg = reg.replace(/MM/, _M2);
        reg = reg.replace(/M/, _M1);
        reg = reg.replace(/dd/, _d2);
        reg = reg.replace(/d/, _d1);
        reg = reg.replace(/HH/, _H2);
        reg = reg.replace(/H/, _H1);
        reg = reg.replace(/mm/, _m2);
        reg = reg.replace(/m/, _m1);
        reg = reg.replace(/ss/, _s2);
        reg = reg.replace(/s/, _s1);
        reg = new RegExp("^" + reg + "$");
        regexp = reg;

        if (reg.test(dateString)) {
            var now = new Date();
            var vals = regexp.exec(dateString);
            var ia = new Array();
            var i = 0;
            _yi = formatString.search(/yyyy/);
            if (_yi < 0) {
                _yi = formatString.search(/yy/);
            }
            if (_yi >= 0) {
                ia[i] = _yi;
                i++;
            }

            _Mi = formatString.search(/MM/);
            if (_Mi < 0) {
                _Mi = formatString.search(/M/);
            }
            if (_Mi >= 0) {
                ia[i] = _Mi;
                i++;
            }

            _di = formatString.search(/dd/);
            if (_di < 0) {
                _di = formatString.search(/d/);
            }
            if (_di >= 0) {
                ia[i] = _di;
                i++;
            }

            _Hi = formatString.search(/HH/);
            if (_Hi < 0) {
                _Hi = formatString.search(/H/);
            }
            if (_Hi >= 0) {
                ia[i] = _Hi;
                i++;
            }

            _mi = formatString.search(/mm/);
            if (_mi < 0) {
                _mi = formatString.search(/m/);
            }
            if (_mi >= 0) {
                ia[i] = _mi;
                i++;
            }

            _si = formatString.search(/ss/);
            if (_si < 0) {
                _si = formatString.search(/s/);
            }
            if (_si >= 0) {
                ia[i] = _si;
                i++;
            }

            var index = new Array(_yi, _Mi, _di, _Hi, _mi, _si);
            for (i = 0; i < ia.length - 1; i++) {
                for (var j = 0; j < ia.length - 1 - i; j++) {
                    if (ia[j] > ia[j + 1]) {
                        temp = ia[j];
                        ia[j] = ia[j + 1];
                        ia[j + 1] = temp;
                    }
                }
            }
            for (i = 0; i < ia.length; i++) {
                for (j = 0; j < index.length; j++) {
                    if (ia[i] == index[j]) {
                        index[j] = i;
                    }
                }
            }
            var year = index[0] >= 0 ? vals[index[0] + 1] : now.getFullYear();
            var month = index[1] >= 0 ? (vals[index[1] + 1] - 1) : now.getMonth();
            var day = index[2] >= 0 ? vals[index[2] + 1] : now.getDate();
            var hour = index[3] >= 0 ? vals[index[3] + 1] : "";
            var minute = index[4] >= 0 ? vals[index[4] + 1] : "";
            var second = index[5] >= 0 ? vals[index[5] + 1] : "";
            var validate;
            if (hour == "") {
                validate = new Date(year, month, day);
            } else {
                validate = new Date(year, month, day, hour, minute, second);
            }
            if (validate.getDate() == day) {
                return validate;
            }
        }
    }

};

Date.parseObj = function (obj) {
    var date = obj.date;
    var mon = obj.month+1;
    var year = obj.year+1900;
    var str = year+"-"+mon+"-"+date+" "+obj.hours+":"+obj.minutes+":"+obj.seconds;
    return Date.parseDate(str,"yyyy-M-d H:m:s");
}

/**
 * 实现日期相加
 * @param date:日期
 * @param interval:字符串表达式，表示要添加的时间间隔

 * @param number:数值表达式，表示要添加的时间间隔的个数
 */
Date.prototype.add = function (interval, number) {
    switch (interval) {
        case "y":
        {
            this.setFullYear(this.getFullYear() + number);
            return this;
            break;
        }
        case "m":
        {
            this.setMonth(this.getMonth() + number);
            return this;
            break;
        }
        case "d":
        {
            this.setDate(this.getDate() + number);
            return this;
            break;
        }
        case "H":
        {
            this.setHours(this.getHours() + number);
            return this;
            break;
        }
        case "i":
        {
            this.setMinutes(this.getMinutes() + number);
            return this;
            break;
        }
        case "s":
        {
            this.setSeconds(this.getSeconds() + number);
            return this;
            break;
        }
        case "q":
        {
            this.setMonth(this.getMonth() + number * 3);
            return this;
            break;
        }
        case "w":
        {
            this.setDate(this.getDate() + number * 7);
            return this;
            break;
        }
        default:
        {
            this.setDate(this.getDate() + number);
            return this;
            break;
        }
    }
};


/**
 * 提供一种用于串联字符串的机制。<br />
 * 创建 StringBuilder 的新实例，并（可选）接受要串联的初始文本。
 * @param initialText （可选）用于初始化实例值的字符串。 如果值为 null，则新的 StringBuilder 实例将包含一个空字符串 ("")。
 * @constructor
 */
var StringBuilder = function (initialText) {
    this._parts = (typeof(initialText) !== 'undefined' && initialText !== null && initialText !== '') ?
        [initialText.toString()] : [];
    this._value = {};
    this._len = 0;
}

StringBuilder.prototype = {
    /**
     * 将指定字符串的副本追加到 StringBuilder 实例的末尾。<br />
     * 使用 append 方法可以将指定字符串的副本追加到 StringBuilder 实例的末尾。 如果 text 为空字符串、null 或 undefined，则 StringBuilder 实例保持不变。
     * @param text 要追加到 StringBuilder 实例的末尾的字符串。
     * @returns {*}
     */
    append: function (text) {
        this._parts.push(text);
        return this;
    },

    /**
     * 将一个字符串和一个行结束符一起追加到 StringBuilder 实例的末尾。<br />
     * 使用 appendLine 方法可将一个指定字符串和一个行结束符追加到 Stringbuilder 实例的末尾。 行结束符是一个回车符和一个换行符的组合。 如果 text 中未指定任何字符串，则只追加行结束符。
     * @param text （可选）要与一个行结束符一起追加到 StringBuilder 实例的末尾的字符串。
     * @returns {*}
     */
    appendLine: function (text) {
        this._parts.push(
            ((typeof(text) === 'undefined') || (text === null) || (text === '')) ?
                '\r\n' : (text + '\r\n'));
        return this;
    },
    /**
     * 清除 StringBuilder 实例的内容。
     */
    clear: function () {
        this._parts = [];
        this._value = {};
        this._len = 0;
    },
    /**
     * 确定 StringBuilder 对象是否具有内容。<br />
     * 使用 isEmpty 方法可以确定 StringBuilder 实例是否具有内容。 如果将空字符串、null 或 undefined 值追加到空的 StringBuilder 实例，则该实例保持为空，不会发生变化。
     * @returns {boolean} 如果 StringBuilder 实例不包含任何元素，则该值为 true；否则为 false。
     */
    isEmpty: function () {
        return (!this._parts.length || !this.toString());
    },
    /**
     * 从 StringBuilder 实例的内容创建一个字符串，并可以根据需要在所创建的字符串的各个元素之间插入分隔符。
     * @param separator （可选）要在返回的字符串的各个元素之间追加的一个字符串。
     * @returns {*}  StringBuilder 实例的字符串表示形式。 如果指定了 separator，则在返回的字符串的各个元素之间插入分隔符字符串。
     */
    toString: function (separator) {
        separator = separator || '';
        var parts = this._parts;
        if (this._len !== parts.length) {
            this._value = {};
            this._len = parts.length;
        }
        var val = this._value;
        var ret = val[separator];
        if (typeof(ret) === 'undefined') {
            if (separator !== '') {
                for (var i = 0; i < parts.length;) {
                    var part = parts[i];
                    if ((typeof(part) === 'undefined') || (part === '') || (part === null)) {
                        parts.splice(i, 1);
                    }
                    else {
                        i++;
                    }
                }
            }
            val[separator] = ret = parts.join(separator);
        }
        return ret;
    }
}
/*四舍五入问题*/
Number.prototype.toFixed = function(s){
    if(s<0) s=0;
    var s1 = s+1;
    var that = this,changenum,index;
    // 负数
    if(this < 0)that = -that;
    changenum = that.toString();
    changenum = changenum.replace("E",'e');
    if(changenum.indexOf('e')>0){
        var k1 = changenum.split('e')[0];
        var k2 = parseInt(changenum.split('e')[1]);
        if(k1.indexOf(".")<0){
            k1 +='.';
        }
        if(k2>0){
            for(var i=0;i<k2+1;i++){
                k1+='0';
            }
            changenum = k1.split(".")[0]+k1.split(".")[1].substr(0,k2)+'.'+k1.split(".")[1].substr(k2);
        }else if(k2<0){
            k2 = 0-k2;
            for(var i=0;i<k2;i++){
                k1='0'+k1;
            }
            var a1 = k1.split(".")[0];
            var a2 = k1.split(".")[1];
            changenum = a1.substr(0,a1.length-k2)+'.'+a1.substr(a1.length-k2,k2)+a2;
        }
    }
    if(changenum.indexOf(".")<0){
        changenum +='.';
    }
    for (var i = 0; i < s+1; i++) {
        changenum = changenum + "0";
    }
    var z_l = changenum.indexOf(".");
    changenum = changenum.replace('.','');
    changenum = changenum.substr(0,z_l+s)+'.'+changenum.substr(z_l+s,1);
    var a = parseFloat(changenum)+0.5;
    var b = parseInt(a).toString();
    var str;
    if(s==0){
        str = b;
    }else{
        if( b.length<z_l+s){
            for(i=b.length;i<z_l+s;i++){
                b = '0'+b;
            }
        }
        str = b.substring(0,b.length-s)+'.'+b.substring(b.length-s);
    }
    if(this<0 && parseFloat(str)!=0){
        return "-"+str;
    }else{
        return str;
    }
}
/**
 * 给js增加ArrayList集合对象
 */
var ArrayList = function () {
    this.elements = new Array();
    /**
     * 返回此列表中的元素数。
     * @returns {*}
     */
    this.size = function () {
        return this.elements.length;
    };
    /**
     * 如果此列表中没有元素，则返回 true
     * @returns {boolean}
     */
    this.isEmpty = function () {
        return (this.elements.length < 1);
    };
    /**
     * 移除此列表中的所有元素。
     */
    this.clear = function () {
        this.elements.length = 0;
    };
    /**
     * 将指定的元素添加到此列表的尾部。
     * @param _object
     */
    this.add = function (_object) {
        this.elements[this.elements.length] = _object;
    };
    /**
     * 返回此列表中指定位置上的元素。
     * @param _index
     * @returns {*}
     */
    this.get = function (_index) {
        if (this.elements.length > 0 && _index < this.elements.length)
            return this.elements[_index];
        return null;
    };
    /**
     * 用指定的元素替代此列表中指定位置上的元素。
     * @param _index
     * @param _object
     */
    this.set = function (_index, _object) {
        if (_index < this.elements.length)
            this.elements[_index] = object;
    };
    /**
     * 移除此列表中指定位置上的元素或移除此列表中首次出现的指定元素（如果存在）。
     * @param _index
     * @returns {boolean}
     */
    this.remove = function (_index) {
        if (isNaN(_index)) {
            this.elements.remove(_index);
            return true;
        } else {
            try {
                for (var i = 0; i < this.elements.length; i++) {
                    if (this.elements[i] === _index) {
                        this.elements.remove(_index);
                        return true;
                    }
                }
            } catch (e) {
                return false;
            }
        }
    };
    /**
     * 返回此列表中首次出现的指定元素的索引，或如果此列表不包含元素，则返回 -1。
     * @param _object
     * @returns {number}
     */
    this.indexOf = function (_object) {
        try {
            for (var i = 0; i < this.elements.length; i++) {
                if (this.elements[i] === _object)
                    return i;
            }
        } catch (e) {
            return -1;
        }
        return -1;
    };
    /**
     * 返回此列表中最后一次出现的指定元素的索引，或如果此列表不包含索引，则返回 -1。
     * @param _object
     * @returns {number}
     */
    this.lastIndexOf = function (_object) {
        try {
            for (var i = this.elements.length - 1; i >= 0; i--) {
                if (this.elements[i] === _object)
                    return i;
            }
        } catch (e) {
            return -1;
        }
        return -1;
    };
    /**
     * 如果此列表中包含指定的元素，则返回 true。
     * @param _object
     * @returns {boolean}
     */
    this.contains = function (_object) {
        return this.indexOf(_object) != -1;
    };
    this.containsAll = function (_ArrayList) {
        if (isEmpty(_ArrayList) || !_ArrayList.isArrayList())
            return false;
        if (this.elements.length != _ArrayList.length)
            return false;
        try {
            for (var i = 0; i < this.elements.length; i++) {
                if (this.elements[i] != _ArrayList[i]) {
                    return false;
                }
            }
        } catch (e) {
            return false;
        }
        return true;
    };
    this.toString = function () {
        this.elements.toString();
    };
}
/**
 * 给js增加HashMap集合对象
 */
var HashMap = function () {
    this.elements = new Array();
    this.size = function () {
        return this.elements.length;
    };
    this.isEmpty = function () {
        return (this.elements.length < 1);
    };
    this.put = function (_key, _value) {
        this.elements.push({
            key: _key,
            value: _value
        });
    };
    this.clear = function () {
        this.elements.length = 0;
    };
    this.remove = function (_key) {
        try {
            for (var i = 0; i < this.elements.length; i++) {
                if (this.elements[i].key === _key) {
                    this.elements.splice(i, 1);
                    return true;
                }
            }
        } catch (e) {
            return false;
        }
        return false;
    };
    this.get = function (_key) {
        try {
            for (var i = 0; i < this.elements.length; i++) {
                if (this.elements[i].key === _key) {
                    return this.elements[i].value;
                }
            }
        } catch (e) {
            return null;
        }
        return null;
    };
    this.element = function (_index) {
        if (_index < 0 || _index >= this.elements.length) {
            return null;
        }
        return this.elements[_index];
    };
    this.containsKey = function (_key) {
        try {
            for (var i = 0; i < this.elements.length; i++) {
                if (this.elements[i].key === _key)
                    return true;
            }
        } catch (e) {
            return false;
        }
        return false;
    };
    this.containsValue = function (_value) {
        try {
            for (var i = 0; i < this.elements.length; i++) {
                if (this.elements[i].value === _value)
                    return true;
            }
        } catch (e) {
            return false;
        }
        return false;
    };
    this.values = function () {
        var arr = new Array();
        for (var i = 0; i < this.elements.length; i++) {
            arr.push(this.elements[i].value);
        }
        return arr;
    };
    this.keys = function () {
        var arr = new Array();
        for (var i = 0; i < this.elements.length; i++) {
            arr.push(this.elements[i].key);
        }
        return arr;
    };
    this.toString = function () {
        var str = "";
        for (var i = 0; i < this.elements.length; i++) {
            str += "&" + this.elements[i].key + "=" + this.elements[i].value;
        }
        return str;
    };
}
//})();

