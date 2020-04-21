/**
 
 @Name：layui.month 月历
 @Author：xie
 @License：MIT

 */

layui.define(['table','layer'], function(exports){
  "use strict";
  
  var $ = layui.$
  ,table = layui.table
  ,layer = layui.layer
  
  //模块名
  ,MOD_NAME = 'month'

  //外部接口
  ,month = {
    config: {}
    ,index: layui[MOD_NAME] ? (layui[MOD_NAME].index + 10000) : 0

    //设置全局项
    ,set: function(options){
      var that = this;
      that.config = $.extend({}, that.config, options);
      return that;
    }
    
    //事件监听
    ,on: function(events, callback){
      return layui.onevent.call(this, MOD_NAME, events, callback);
    }
  }

  //操作当前实例
  ,thisModule = function(){
    var that = this
    ,options = that.config
    ,id = options.id || that.index;
    
    thisModule.that[id] = that; //记录当前实例对象
    thisModule.config[id] = options; //记录当前实例配置项
    
    return {
      config: options
      //重置实例
      ,reload: function(options){
        that.reload.call(that, options);
      }
      ,getSelected: function(){
        return that.getSelected.call(that);
      }
    }
  }
  
  //获取当前实例配置项
  ,getThisModuleConfig = function(id){
    var config = thisModule.config[id];
    if(!config) hint.error('The ID option was not found in the '+ MOD_NAME +' instance');
    return config || null;
  }

  //字符常量
  ,ELEM_VIEW = 'layui-month', ICON_CLICK = 'layui-month-act'
  ,ELEM_HEAD = 'layui-month-head',ELEM_DEF_BUT = 'layui-month-defbut',ELEM_DEF_TOOLBAR = 'layui-month-toolbar',ELEM_TITLE = 'layui-month-title', ELEM_BODY = 'layui-month-body', ELEM_TH = 'layui-month-th', ELEM_TR = 'layui-month-tr', ELEM_TD = 'layui-month-td', ELEM_ITEM = 'layui-month-item'

  //构造器
  ,Class = function(options){
    var that = this;
    that.index = ++month.index;
    that.config = $.extend({}, that.config, month.config, options);
    that.render();
  };

  var d = new Date();
  //默认配置
  Class.prototype.config = {
    yearNum: d.getFullYear()  //默认年
    ,monthNum: d.getMonth()  //默认月
    ,themeColor: '#FF5722'  //主题色
    ,toolbar: '' //工具栏
    ,cellSize: 30 //单元格大小
    ,weeks: ["周日", "周一", "周二", "周三", "周四", "周五", "周六"]
  };
  
  //重载实例
  Class.prototype.reload = function(options){
    var that = this;
    
    layui.each(options, function(key, item){
      if(item.constructor === Array) delete that.config[key];
    });
    
    that.config = $.extend(true, {}, that.config, options);
    that.render();
  };

  //主体渲染
  Class.prototype.render = function(){
    var that = this
    ,options = that.config;
    
    that.checkids = [];

    var getTitle = function(){
        var str = options.yearNum + "年";
        str += options.monthNum < 9 ? ("0" + (options.monthNum + 1)) : (options.monthNum + 1);
        str += "月";
        return str;
    };

    var getDateStr = function(d){
        var str = d.getFullYear() + "-";
        str += d.getMonth() < 9 ? ("0" + (d.getMonth() + 1)) : (d.getMonth() + 1);
        str += "-";
        str += d.getDate() < 9 ? ("0" + d.getDate()) : d.getDate();
        return str;
    };
    var changeMonth = function(type){
          var d = new Date();
          d.setFullYear(options.yearNum);
          d.setMonth(options.monthNum);

          if(type){
              d.setMonth(d.getMonth() - 1);
          }else{
              d.setMonth(d.getMonth() + 1);
          }
          // console.log(text)
          //将当前坐标进行保存以进行下一次计算
        options.yearNum = d.getFullYear();
        options.monthNum = d.getMonth();
          that.render();
      };
    var render_1 = function(){
        var d = new Date();
        d.setFullYear(options.yearNum);
        d.setMonth(options.monthNum);
        d.setDate(1);
        var d2 = new Date();

        var ds = [];
        var i = 0;
        while (options.yearNum == d.getFullYear() && options.monthNum == d.getMonth()) {
            if (ds[i] == null) {
                ds[i] = [];
            }
            //第一周需要填充满
            if (i == 0 && ds[i].length == 0 && d.getDay() != 0) {
                for (var a = 0; a < d.getDay(); a++) {
                    ds[i].push(null);
                }
            }

            var dStr = d.getDate();
            var dateStr = getDateStr(d);
            if(d.getFullYear() == d2.getFullYear() && d.getMonth() == d2.getMonth() && d.getDate() == d2.getDate()){
                dStr = "今";
            }
            ds[i].push({ year: d.getFullYear(), month: d.getMonth(), day: dStr, dateStr: dateStr, cls: ''});

            if (d.getDay() != 0 && d.getDay() % 6 == 0) {
                i++;
            }
            d.setDate(d.getDate() + 1);
        }

        if (ds[ds.length - 1].length < 7) {
            for (var a = ds[ds.length - 1].length; a < 7; a++) {
                ds[ds.length - 1].push(null);
            }
        }
        console.log(ds);

      var table = $('<table class="layui-table '+ ELEM_VIEW +'" lay-filter="LAY-MONTH-'+ that.index +'"></table>');

        var thead = $('<thead></thead>').appendTo(table);
      var head = $('<div></div>').addClass(ELEM_HEAD).appendTo($('<td colspan="'+ options.weeks.length +'"></td>').appendTo($('<tr></tr>').appendTo(thead)));

      var head_left = $('<div></div>').addClass(ELEM_DEF_BUT);
        head_left.append($('<i></i>').addClass('layui-icon layui-icon-left').on('click',function(){
            changeMonth(true);
          }));
        head_left.append($('<div></div>').addClass(ELEM_TITLE).html(getTitle()));
        head_left.append($('<i></i>').addClass('layui-icon layui-icon-right').on('click',function(){
            changeMonth(false);
        }));
        head_left.appendTo(head);

        var head_right = $('<div></div>').addClass(ELEM_DEF_TOOLBAR);
        if(options.toolbar != null && options.toolbar != ''){
            head_right.append($(options.toolbar));
        }
        head_right.appendTo(head);

      var th = $('<tr></tr>').appendTo(thead);
        for(var j = 0 ; j < options.weeks.length ; j++){
            var td = $('<th></th>').appendTo(th);
            var item = options.weeks[j];
            if(item != null){
                td.append($('<div></div>').append('<div></div>').html(item));
            }
        }

        var tbody = $('<tbody></tbody>').appendTo(table);
      for(var i = 0 ; i < ds.length ; i++){
        var tr = $('<tr></tr>').addClass(ELEM_TR).appendTo(tbody);
        var days = ds[i];
        for(var j = 0 ; j < days.length ; j++){
            var td = $('<td></td>').addClass(ELEM_TD).appendTo(tr);
            var item = days[j];
            if(item != null){
                if(item.day == "今"){
                    td.addClass('layui-month-jin');
                }
              td.append($('<div></div>').append($('<div></div>').css({width: options.cellSize + 'px',height: options.cellSize + 'px'}).addClass(ELEM_ITEM).data('data',item).html(item.day)).on('click',function(){
                  $(this).parents('.' + ELEM_VIEW).find("." + ELEM_ITEM).removeClass(ICON_CLICK);
                  $(this).find('.' + ELEM_ITEM).addClass(ICON_CLICK);
                  //点击事件产生的回调
                  options.click && options.click({elem: this,data: $(this).find('.' + ELEM_ITEM).data('data')});
              }));
            }else{
                td.addClass('layui-month-null')
            }
        }
      }

      var othis = options.elem = $(options.elem);
      if(!othis[0]) return;

      //索引
      that.key = options.id || that.index;

      //插入组件结构
      that.elem = table;
      othis.html(that.elem);

      console.log(othis.width());
      let td_height = othis.width() / options.weeks.length;
      console.log(td_height);
      othis.find('th,.'+ ELEM_TR).css({height: td_height + 'px'});

      //加载完成产生的回调
      options.loadSuccess && options.loadSuccess({elem: that.elem});
    };

    render_1();
  };

  //得到选中节点
  Class.prototype.getSelected = function(){
    var that = this
    ,options = that.config;
    //遍历节点找到选中索引

    return that.elem.find('.' + ICON_CLICK).parent().data('data');
  };

  //记录所有实例
  thisModule.that = {}; //记录所有实例对象
  thisModule.config = {}; //记录所有实例配置项
  
  //重载实例
  month.reload = function(id, options){
    var that = thisModule.that[id];
    that.reload(options);
    return thisModule.call(that);
  };
  
  //获得选中的节点数据
  month.getSelected = function(id){
    var that = thisModule.that[id];
    return that.getSelected();
  };
    
  //核心入口
  month.render = function(options){
    var inst = new Class(options);
    return thisModule.call(inst);
  };

  exports(MOD_NAME, month);
});