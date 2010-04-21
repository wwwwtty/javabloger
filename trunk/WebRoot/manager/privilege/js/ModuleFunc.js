function getModlueMess(node){
	node.getEl().mask("数据传输")
	log.dir(node);	
	Connection.submit( {
		url :basePath+'manager/BaseDataService.do?action=findEntity',
		params : {
		   entity :'org.cms.doamin.auth.Role',
		   filter:'grade:'+node.attributes.id
		},
		success : function(msg, opt) {
				
		}
	 });
}
function commit(){
 var funccode = Ext.getCmp('funccode').getValue();	 
 var text = Ext.getCmp('text').getValue();
 var parentcode = Ext.getCmp('parentcode').getValue();
 var independent = Ext.getCmp('independent').getValue();
 var sequence = Ext.getCmp('sequence').getValue();
 var leaf = Ext.getCmp('leaf').getValue();
 var action = Ext.getCmp('action').getValue();
 var url = Ext.getCmp('url').getValue();
 var isblock = Ext.getCmp('isblock').getValue();
 var info = Ext.getCmp('info').getValue();	
 var createuser = Ext.getCmp('createuser').getValue();
 var createdate = Ext.getCmp('createdate').getValue();
 var lastdate = Ext.getCmp('lastdate').getValue();
 var lastuser = Ext.getCmp('lastuser').getValue();
 var logid = Ext.getCmp('logid').getValue();
 var tablename = Ext.getCmp('tablename').getValue();
 if (funccode == "" || funccode.length == 0) {
	Ext.Msg.alert("提示", "功能代码不能为空!");
	return;
 }
 if (text == "" || text.length == 0) {
	Ext.Msg.alert("提示", "功能名称不能为空!");
	eturn;
 }	
  if(createuser==""){
		var addData = [{
			funccode :funccode,
			text :text,
			leaf:leaf,
			sequence:sequence,
			parentcode :parentcode,
			independent :independent,
			action :action,
			url :url,
			isblock :isblock,
			logMeta : '',
			info :info
		}];
	}else{
		var addData = [{
			funccode :funccode,
			text :text,
			leaf:leaf,
			parentcode :parentcode,
			sequence:sequence,
			independent :independent,
			action :action,
			url :url,
			isblock :isblock,
			logMeta : {
				'createuser' :createuser,
				'createdate' :createdate,
				'lastdate' :lastdate,
				'lastuser' :lastuser,
				'logid' :logid,
				'tablename' :tablename
			},
			info :info
		}];
	}
    var addMessage =  Ext.encode(addData);
	var bl = Ext.MessageBox.confirm("提示", "确定保存", function(btn) {
		if (btn == 'yes') {
			Ext.Ajax.request( {
				url :'./authrizeAction.do?method=saveModuleMess',
				params : {
					addMessage :addMessage,
					funccode :funccode
				},
				success : function() {
					authTreeData = new Array();
					loader.load(root);
					Ext.Msg.alert("提示", "保存成功!");
				},
				failure : function() {
					Ext.Msg.alert("提示", "系统错误,请联系管理员!");
				}
			});

		}
	});
}
function addModule(){
	var selectedNode = treePanel.getSelectionModel().getSelectedNode();
    Ext.getCmp('funccode').setValue('');	 
    Ext.getCmp('funccode').enable();
    Ext.getCmp('text').setValue();
    Ext.getCmp('leaf').setValue('1');
    Ext.getCmp('parentcode').setValue(selectedNode.id);
    Ext.getCmp('parentname').setValue(selectedNode.text);
    Ext.getCmp('independent').setValue('N');
    Ext.getCmp('action').setValue('');
    Ext.getCmp('sequence').setValue('');
    Ext.getCmp('url').setValue('');
    Ext.getCmp('isblock').setValue('N');
    Ext.getCmp('info').setValue('');	
    Ext.getCmp('createuser').setValue('');
    Ext.getCmp('createdate').setValue('');
    Ext.getCmp('lastdate').setValue('');
    Ext.getCmp('lastuser').setValue('');
    Ext.getCmp('logid').setValue('');
    Ext.getCmp('tablename').setValue('');
}
function saveNewTree(tree, node, oldParent, newParent, index) {
  Ext.Ajax.request({
    url : './authrizeAction.do?method=saveNewTree',
    params : {
      node : node.id,
      oldParent : oldParent.id,
      newParent : newParent.id,
      childs : processNode(newParent.childNodes)
    }
  });
}
function processNode(nodes) {
  var arr = "";
  for (var i = 0; i < nodes.length; i++) {
    var node = nodes[i];
    if (node.getDepth() > 0) {
      arr += node.id + ";";
    }
  }
  return arr;
}