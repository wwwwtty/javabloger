var MAX_VALUE=999999;//
var MIN_VALUE=1;
/**与服务器通信的封闭方法； 
 *注意参数的设置；
 *	@param this.url=url;
 *	@param this.params=params
 *	@param this.callBack=callback
 	@param success
 	@param failure
 */
var Connection=new function(config){
		config=config||{};
		Ext.apply(this,config)
		
		this.isMsgshow=false;
		
		this.hideMsg=function(){
			if(this.isMsgshow)
				this.msg.hide();
		}
		var that=this;
		this.ResponseCallback=function (opt,suc,rep){
			if (typeof(that.callback) == 'function') {
				that.hideMsg();
				that.callback(opt, suc, rep);
			} 
			if(suc){
				if (typeof(that.success) == 'function') {
					that.hideMsg();
					that.success(Ext.decode(rep.responseText),opt);
				}
			}else{
				if (typeof(that.failure) == 'function') {
					that.hideMsg();
					that.failure(Ext.decode(rep.responseText), opt);
				}
			}
		}

	this.submit=function(config){
		config=config||{};
		Ext.apply(this,config)
		
		if (this.model != false) {
			this.isMsgshow=true;
			this.msg = Ext.Msg.wait('正在发送并处理数据... ..', '请稍等');
		}
		
		Ext.Ajax.request({
			clientValidation: true,
			url: this.url,
			method: 'POST',
			params: this.params,
			callback: this.ResponseCallback
		});
		
	}
}();
/**对数组扩展,*/
Ext.applyIf(Array.prototype, {
	contain:function(node){
		for(var i in this){
			if(this[i]==node)
			return true;
		}
		return false;
	}
})

/**
 * 扩展NumberEditer（数字编辑框）
 */
var NumberEditer = Ext.extend(Ext.form.NumberField, {
    allowNegative: false,
    allowBlank: false,
    maxValue: MAX_VALUE,
    minValue: MIN_VALUE,
    maxErrMsg: "数字输入越界,不能大于" + MAX_VALUE,
    minErrMsg: "数字输入越界,不能小于" + MIN_VALUE,
    enableKeyEvents: true,
    onKeyUp: function(e){
        if (this.getValue() < this.minValue) {
            this.fireEvent("blur", this, e);
            Ext.Msg.alert("错误", this.minErrMsg);
            this.applyEmptyText();
        }
        else 
            if (this.getValue() > this.maxValue) {
                this.fireEvent("blur", this, e);
                Ext.Msg.alert("错误", this.maxErrMsg);
                this.applyEmptyText();
            }
            else {
                this.fireEvent('keyup', this, e);
            }
    },
	setValue : function(v){
    	v = typeof v == 'number' ? v : parseFloat(String(v).replace(this.decimalSeparator, "."));
        v = isNaN(v) ? '' : String(v).replace(".", this.decimalSeparator);
        
		this.sufix=(this.sufix===null || this.sufix===undefined ? '' : this.sufix);
        this.value = v;
        if(this.rendered){
            this.el.dom.value = (v === null || v === undefined ? '' : v)+this.sufix;
            this.validate();
    	}
    }
	
})
Ext.reg('numberfield', NumberEditer);
/*
 * 扩展Combox组件；
 */
var ComboBox = Ext.extend(Ext.form.ComboBox, {
      triggerAction : 'all',
      selectOnFocus : true,
      editable : false,
      lazyInit:true,
      mode : "local",
      listeners : {
        "focus" : function(field){
          Ext.getCmp(this.id).getStore().load();
        }
      }
    })
    Ext.reg('lazycombo',ComboBox);    

/*
 * grid行单选框；
 */

function checkModel_create(){
	return  new Ext.grid.CheckboxSelectionModel({
    header : ' ',
    singleSelect : true,
	autoShow :true
})
}

function multiCheckModel_create(){
	return  new Ext.grid.CheckboxSelectionModel({
    header : ' '
})
}

function toInteger(v){
	var nv=parseInt(v);
	return (isNaN(nv))?0:nv;
}
/** 构建一个链接；*/
function link_builder(id, value, href){
	var a='<a';
	if(Ext.isString(id)&&id!="")
		a+=' id="'+id+'" ';
//	if(Ext.isFunction(fun))
//		a+=' onclick="'+fun+'" ';
	if(Ext.isString(href)&&href!="")
		a+=' href="'+href+'"';
	
	if(Ext.isString(value)&&value!="")
		a+=">"+value;
	
	return a+"</a>"
}
/**为EditGrid置入某一个可编辑框；*/
function editCol_setEditer(colModel, colName, component){
    var index = colModel.findColumnIndex(colName);
    colModel.setEditable(index, true);
    colModel.setEditor(index, new Ext.grid.GridEditor(component));
}
/**设置EditGrid指定列的可编辑性；默认为可编辑 */
function editCol_Editable(colModel, colName, editable){
	try{
    var index = colModel.findColumnIndex(colName);
   // colModel.setEditable(index, true);
    colModel.setEditable(index, !(false==editable));
	}catch(e){log.error(e)}
}
/**设置EditGrid是否可编辑；默认为不可编辑 */
function editCol_forbit(colModel,editable){
	try {
		var totle = colModel.getColumnCount(false);
		for (var  i = 0; i < totle; i++) {
			colModel.setEditable(i, true == editable);
		}
	}catch(e){log.error(e)}
}
/** 设置Grid列是否隐藏；默认隐藏； */
function editCol_hidden(colModel, colName, hidden){
    try {
        var index = colModel.findColumnIndex(colName);
        colModel.setHidden(index, !(false == hidden));
    } catch (e) { log.error(e)}
}
/**将指定的Panel内的Field对象置为只读
 *ids可以为{ID}数组，或者ID；或者,一个Elements数组或对象 */
function panel_readOnly(ids, escapes,isRead){
	escapes=convertToArray(escapes);
	isRead=false != isRead;
	panel_commad(ids,function(cmp){
		var tag=escapes.contain(cmp.getId())?!isRead:isRead
		cmp.setReadOnly(tag)
	})
}
/**将指定的Panel内的Field对象置为只读
 *ids可以为{ID}数组，或者ID；或者:一个Elements数组;或者:一个Ext组件;
 	oppose:执行相反操作;
 	escapes:放弃执行操作;
  */
function panel_disabled(ids,oppose,escapes,disabled){
	escapes=convertToArray(escapes);
	oppose=convertToArray(oppose);
	disabled=false != disabled;
	panel_commad(ids,function(cmp){
		if(escapes.contain(cmp.getId())) return;
		var tag=oppose.contain(cmp.getId())?!disabled:disabled
		cmp.setDisabled(tag);
	})
}

/**将指定的Panel内的模板命令器,方便其它方法调用;
 *ids可以为{ID}数组，或者ID；或者,一个Container数组或对象 */
function panel_commad(panel,cmd){
	try {
		panel = convertToArray(panel);
		Ext.each(panel, function(el){
			var inputs=null;
			if(Ext.isObject(el)){
				inputs=el.getEl().dom.getElementsByTagName("input");
				inputs=convertToArray(inputs);
				var textarea=el.getEl().dom.getElementsByTagName("textarea");
				textarea=convertToArray(textarea)
				inputs=inputs.concat(textarea);
			}
			else if(Ext.isElement(el)){
				inputs=el.getElementsByTagName("input");
				inputs=convertToArray(inputs);
				var textarea=el.getElementsByTagName("textarea");
				textarea=convertToArray(textarea)
				inputs=inputs.concat(textarea);
			}
			else if(Ext.isString(el)){
				inputs = Ext.getDom(el).getElementsByTagName("input");
				inputs=convertToArray(inputs);
				var textarea=Ext.getDom(el).getElementsByTagName("textarea");
				textarea=convertToArray(textarea)
				inputs=inputs.concat(textarea);
			}
			//具体命令器执行点;
			Ext.each(inputs,function(p){
				var cmp=Ext.getCmp(p.id);
				try {
					if (!Ext.isEmpty(cmp)) {
						cmd(cmp);
					}
				}catch(e){log.error(e)}
			});
		})
	}catch(e){log.error("panel do command:"+e)}
}
/** 对面板信息进行验证 */
function panel_validate(panelIds){
	panel_commad(ids,function(cmp){
		cmp.validate();
	})
}
function panel_allowBank(panelId,escapes,bankable){
		escapes=convertToArray(escapes);
		panel_commad(ids,escapes,function(cmp){
			var tag=escapes.contain(cmp.getId())?false:false != bankable
			cmp.allowBlank(tag);
	})
}
function fields_readOnly(fields,isRead){
	try {
		fields = convertToArray(fields);
		Ext.each(fields, function(fd){
			try {
				var cmp = Ext.getCmp(fd);
				cmp.setReadOnly(false != isRead)
			}catch(e){}
		})
	}catch(e){log.warn("field readOnly:"+e)}
}
function fields_validate(fields){
    fields = convertToArray(fields);
    Ext.each(fields, function(fd){
        try {
            var tag = Ext.getCmp(fd).validate();
        } 
        catch (e) {
            log.error("field allowBank:" + e)
        }
    })
}
/**是否允许该组件为空；默认为：isRead==false;允许；*/
function fields_allowBank(fields,isRead){
	
		fields = convertToArray(fields);
		Ext.each(fields, function(fd){
			try {
				Ext.getCmp(fd).allowBlank = (true == isRead)
			}catch(e){log.error("field allowBank:"+e)}
		})
	
}
function convertToArray(obj){
	if(Ext.isEmpty(obj)){
		return new Array();
	}
	else if(Ext.isString(obj)){
		return [obj];
	}
	else if (!Ext.isObject(obj)) {
		var arr=[];
        for(var o in obj){
			if(Ext.isFunction(obj[o]))continue;
			arr.push(obj[o]);
		}
		return arr;
    }
	else if (!Ext.isArray(obj)) {
        obj = [obj];
    }
	return obj;
}
/** 收集数据 */
var panel_gatherData=function(els,escape){
	var data={};
	escape=convertToArray(escape);
		panel_commad(els,function(cmp){
			if(escape.contain(cmp.getId()))return;
			data[cmp.getName()]=cmp.getValue();
		})
	return data;	
}

/** 收集数据 */
var panel_initData=function(els,escape,data){
		escape=convertToArray(escape);
		panel_commad(els,function(cmp){
			if(escape.contain(cmp.id))return;
			cmp.setValue(data[cmp.name]);	
		})
	return data;	
}
var fields_gatherData=function(fields){
	fields=convertToArray(fields);
	var data={}
	Ext.each(fields,function(f){
		try {
			data[f] = Ext.getCmp(f).getValue();
		}catch(e){log.error("找不到项："+f+";错误信息如下："+e)}
	})
	return data;
}

/** 通过传入的Js对象,转换为Ext的Record对象; */
function Record_create_FromArray(obj){
    //防止相互之间值的影响；
    var Record = Ext.data.Record.create({});
    var ord = new Record({});
    //非相关数据的Copy
    for (var p in obj) {
        ord.data[p] = obj[p];
    }
    return ord;
}

//对象克隆
function clone(myObj){
  if(typeof(myObj) != 'object') return myObj;
  if(myObj == null) return myObj;
  
  var myNewObj = {}
  
  for(var i in myObj)
     myNewObj[i] = clone(myObj[i]);
  
  return myNewObj;
}
//对面板内的元素进行验证；
function isValid(ids){
    if (!Ext.isArray(ids)) {
        ids = [ids];
    }
    var valid = true;
    Ext.each(ids, function(id){
        var el = document.getElementById(id);
        var inputs = el.getElementsByTagName("input");
        Ext.each(inputs, function(p){
            try {
                if (!Ext.getCmp(p.id).validate()) {
                    valid = false;
                }
            }catch (e) {}
        });
        
    })
    return valid;
}
/**
方法链，可在未执行前动态添加命令；
*/
function CommandLink(){
	this.commondArray=[];
	
	this.addCommond=function(cmd){
		this.commondArray[this.commondArray.length]=cmd;
	}
	this.clear=function(){
		this.commond=[];
	}
	var that=this;
	this. doCommand=function(obj){
		var i=0;
		while(that.commondArray[i]!=null){
			try {
				if (false == that.commondArray[i++](obj)) {
					return;
				}
			}
			catch(e){log.error("注入方法执行出错："+e)}
		}
	}
	//兼容以前代码；
	this.add=function(cmd){
		this.addCommond(cmd);
	},
		//实际的执行；
	this.renderOver=function (vpt){
		this.doCommand(vpt);
	}
}


var render=new CommandLink();//页面加载完成后执行的方法链；
var beforRender=new CommandLink();//页面加载前执行的方法链；