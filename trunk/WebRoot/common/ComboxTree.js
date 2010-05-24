Ext.namespace("cms.form");
cms.form.ComboTree = function(options) {  
    cms.form.ComboTree.superclass.constructor.call(this, options);  
};  
  
Ext.extend(cms.form.ComboTree, Ext.form.TriggerField, {  
    triggerClass : 'x-form-arrow-trigger',  
    defaultAutoCreate : {tag: "input", type: "text", size: "24", autocomplete: "off"},
    shadow : 'sides',  
    lazyInit : false, 
    shadow : 'sides',
    minListWidth:90,
    initComponent : function() {  
        cms.form.ComboTree.superclass.initComponent.call(this);  
         this.addEvents(
            'expand',
            'collapse',
            'beforeselect',
            'select'
        );
    },  
  
    onRender : function(ct, position) {  
    	 if(this.hiddenName && !Ext.isDefined(this.submitValue)){
            this.submitValue = false;
        }
        Ext.form.ComboBox.superclass.onRender.call(this, ct, position);  
  
        this.hiddenField = this.el.insertSibling({  
            tag : 'input',  
            type : 'hidden',
            id: (this.id||this.name),
            name : this.name  
        }, 'before', true);  
  
        this.hiddenField.value = this.value !== undefined ? this.value : '';  
  
     //   this.el.dom.removeAttribute('name');  
     //   this.id = this.name;  
  
        if (!this.lazyInit) {  
            this.initList();  
        } else {  
            this.on('focus', this.initList, this, {  
                single : true  
            });  
        }  
    },  
  
    initList : function() {  
        if (this.list) {  
            return;  
        }  
        this.list = new Ext.Layer({  
            shadow : this.shadow,  
            cls : 'x-combo-list',  
            parentEl: this.getListParent()
            }); 
        
  		if(!this.root){
        	this.root = new Ext.tree.TreeNode({
			    id: "root",
				text:'根',
				expandable: true,
				expanded: true
        	});  
  		}
  		//服务器端获取数据;
  			var that=this;
        	var data=new Ext.Ajax.requestSyn({
        		url:this.url,
        		success:function(rep){log.info('success');
        			var data=!Ext.isEmpty(that.repRoot)?rep[that.repRoot]:rep;
        			var createTreeNode=function (obj){
        				var node=new Ext.tree.TreeNode({
							text:obj.text,
							id:obj.id,
							leaf :obj.leaf
						})
						if(obj.id===that.getValue()){
							node.select();
						}
						if(Ext.isArray(obj.childNodes)&&!Ext.isEmpty(obj.childNodes)){
							for(var i=0,len=obj.childNodes.length;i<len;i++){
								node.appendChild(createTreeNode(obj.childNodes[i]))
							}
						}
						return node;
        			}
        			
        			if(Ext.isArray(data)&&!Ext.isEmpty(data)){
        				for(var i=0,len=data.length;i<len;i++){
							that.root.appendChild(createTreeNode(data[i]));
        				}
        			}
        		},
    			error: function(resp){ log.info('error');log.dir(arguments);},
    			failure:function(rep){log.info('failure');log.dir(arguments);}
        	})
  		
  		//装载Grid;
        this.list.setWidth(this.listWidth || Math.max(this.wrap.getWidth(),  this.minListWidth));  
        this.tree = new Ext.tree.TreePanel({  
            autoScroll : true,  
            height : 200,  
            border : false, 
            rootVisible: false,
            root : that.root
        });  
  
        this.tree.on('click', this.onClick, this);  
        this.tree.render(this.list);  
        	
        this.el.dom.setAttribute('readOnly', true);  
        this.el.addClass('x-combo-noedit');  
    },  
      
//    expandNode : function(n, v){  
//        var cs = n.childNodes;  
//        for(var i = 0, len = cs.length; i < len; i++) {  
//            if(cs[i].id == v){  
//                return true;  
//            }  
//            if(expandNode(cs[i], v)){  
//                cs[i].expand();  
//                return true;  
//            }  
//        }  
//        return false;  
//    },  
   expand : function(){
        if(this.isExpanded() || !this.hasFocus){
            return;
        }
//        if(this.bufferSize){
//            this.doResize(this.bufferSize);
//            delete this.bufferSize;
//        }
//        this.list.alignTo(this.wrap, this.listAlign);
//        this.list.show();
        this.list.alignTo(this.wrap, 'tl-bl?');  
        this.list.show();  
        this.mon(Ext.getDoc(), {
            scope: this,
            mousewheel: this.collapseIf,
            mousedown: this.collapseIf
        });
        this.fireEvent('expand', this);
    },
  
//    validateValue : function(value) {  
//        return true;  
//    },  
  
//    validateBlur : function() {  
//        return !this.list || !this.list.isVisible();  
//    },  
  
    onDestroy : function() {  
        if (this.wrap) {  
            this.wrap.remove();  
        }  
        if (this.list) {  
            this.list.destroy();  
        }  
        cms.form.ComboTree.superclass.onDestroy.call(this);  
    },  
  
    isExpanded : function() {  
        return this.list && this.list.isVisible();  
    },  
  
     collapse : function(){
        if(!this.isExpanded()){
            return;
        }
        this.list.hide();
        Ext.getDoc().un('mousewheel', this.collapseIf, this);
        Ext.getDoc().un('mousedown', this.collapseIf, this);
        this.fireEvent('collapse', this);
    },

    // private
    collapseIf : function(e){
        if(!e.within(this.wrap) && !e.within(this.list)){
            this.collapse();
        }
    },
  
    onClick : function(node) {  
        this.setValue(node);  
        this.collapse();  
    },  
  
  
    setValue : function(v) {  
        var val = v;  
        if(typeof v === 'object'){  
            this.hiddenField.value = ((this.root && v.id == this.root.id) ? 0 : v.id);  
            val = v.text;  
        }  
        cms.form.ComboTree.superclass.setValue.call(this, val);  
    },  
  	getValue:function(){
  		return this.hiddenField.value;
  	},
    initEvents : function() {  
        cms.form.ComboTree.superclass.initEvents.call(this);  
    },
   getListParent : function() {
        return document.body;
    },
    initValue : function(){
        Ext.form.ComboBox.superclass.initValue.call(this);
        if(this.hiddenField){
            this.hiddenField.value =
                Ext.isDefined(this.hiddenValue) ? this.hiddenValue :
                Ext.isDefined(this.value) ? this.value : '';
        }
    },
    onTriggerClick : function() {  
        if (this.disabled) {  
            return;  
        }  
        if (this.isExpanded()) {  
            this.collapse();  
            this.el.focus();  
        } else {  
            this.onFocus({});  
            this.expand();
    		this.el.focus();
        }  
    }  
  
});  
Ext.reg('combotree', cms.form.ComboTree);   