/* 关键补丁存放,不建议所有补丁都往此文件丢 */

//Fr:v2.0 Grid单元格文本选择
if (!Ext.grid.GridView.prototype.templates) { Ext.grid.GridView.prototype.templates = {}; }
Ext.grid.GridView.prototype.templates.cell = new Ext.Template(
  '<td class="x-grid3-col x-grid3-cell x-grid3-td-{id} x-selectable {css}" style="{style}" tabIndex="0" {cellAttr}>',
  '<div class="x-grid3-cell-inner x-grid3-col-{id}" {attr}>{value}</div>',
  '</td>');

//Permission denied to access property 'dom' from a non-chrome context
//Fr:Ext 2.2.1 +  Firefox 3.0 +  Firebug 1.4 +
Ext.override(Ext.Element, {
  contains : function(el) {
    try {
      return !el ? false : Ext.lib.Dom.isAncestor(this.dom, el.dom ? el.dom : el);
    } catch (e) {
      return false;
    }
  }
});

//Permission denied to access property 'nodeType' from a non-chrome context
//Fr:Ext 2.2.1 +  Firefox 3.5  Firebug 1.4 +
Ext.lib.Event.resolveTextNode = Ext.isGecko ? function(node){
  if(!node){
    return;
  }
  var s = HTMLElement.prototype.toString.call(node);
  if(s == '[xpconnect wrapped native prototype]' || s == '[object XULElement]'){
    return;
  }
  return node.nodeType == 3 ? node.parentNode : node;
} : function(node){
  return node && node.nodeType == 3 ? node.parentNode : node;
};


/*Fr:v3.0.0 同步Ajax封装*/
Ext.Ajax.requestSyn=function(o){
  var rslt;
  jQuery.ajax({
    async: false,
    type:o.method||"POST",
    url: o.url,
    data: o.params,
    dataType: o.dataType,
    success: o.success||function(resp){ rslt=resp;},
    error: o.failure||function(resp){ rslt=resp;}
  });
  return rslt;
}

/*Fr:v3.0.0 按钮默认最小宽度*/
Ext.Button.prototype.minWidth=50;

/*Fr:v3.0.0 列排序参数要在对象生成前指定*/
Ext.override(Ext.grid.ColumnModel, {
  isSortable : function(col){
    return !!this.config[col].sortable||this.defaultSortable;
  }
});

/*调试语句无环境容错*/
if(window.console||window._firebug){
 log=console;
}else{
 log=console={log:function(){},debug:function(){},info:function(){},warn:function(){},error:function(){},assert:function(){},time:function(){}};
}

/*Fr:v3.1.1 树节点拖拽渲染尾部问题*/
Ext.override(Ext.tree.TreeNode, {
  insertBefore : function(node, refNode) {
    if (!node.render) {
      node = this.getLoader().createNode(node);
    }
    var newNode = Ext.tree.TreeNode.superclass.insertBefore.call(this, node, refNode);
    if (newNode && refNode && this.childrenRendered) {
      node.render();
    }
    this.ui.updateExpandIcon();
    return newNode;
  }
});

/**对数加载器扩展,*/
Ext.override(Ext.tree.TreeLoader, {
	processResponse : function(response, node, callback, scope){
        var json = response.responseText;
        try {
            var o = response.responseData || Ext.decode(json);
            if(Ext.isString(this.root)){
            	o=o[this.root];
            }
            log.dir(o);
            node.beginUpdate();
            for(var i = 0, len = o.length; i < len; i++){
                var n = this.createNode(o[i]);
                if(n){
                    node.appendChild(n);
                }
            }
            node.endUpdate();
            this.runCallback(callback, scope || node, [node]);
        }catch(e){
            this.handleFailure(response);
        }
    }
})


 Ext.override(Ext.form.Action.Submit,{  
    // private  
    processResponse : function(response){  
        this.response = response;  
        //增加下面几句代码就OK啦  
        ////////////////////////  
        var data = response.responseText;  
        if(data.indexOf('<pre>') != -1) {  
          response.responseText = data.substring(5, data.length-6);  
          this.response = Ext.util.JSON.decode(response.responseText);  
        }      
        ///////////////////////////       
        if(!response.responseText){  
            return true;  
        }  
        this.result = this.handleResponse(response);  
        return this.result;  
    }  
});  



Ext.TextButton = Ext.extend(Ext.Button, {
    template: new Ext.Template(
        '<table cellspacing="0" class="x-btn {3}"><tbody class="{4}">',
        '<tr><td><i></i></td><td></td><td"><i></i></td></tr>',
        '<tr><td><i></i></td><td><em class="{5}" unselectable="on"><a class=" {2}" style=" color: blue; padding-left: 3px; padding-right: 3px;">{0}</a></em></td><td><i></i></td></tr>',
        '<tr><td><i></i></td><td></td><td><i></i></td></tr>',
        '</tbody></table>').compile(),

    buttonSelector : 'a:first',

    getTemplateArgs: function() {
        return Ext.Button.prototype.getTemplateArgs.apply(this);
    },

    onClick : function(e){
        if(e.button != 0){
            return;
        }
        if(!this.disabled){
            this.fireEvent("click", this, e);
            if(this.handler){
                this.handler.call(this.scope || this, this, e);
            }
        }
    }
});

Ext.reg("textbutton", Ext.TextButton);