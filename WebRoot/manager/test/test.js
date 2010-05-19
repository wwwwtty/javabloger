var  comboTree=null;
 
Ext.onReady(function(){
//	var log4j=new Log(Log.WARN,Log.consoleLogger,true);
//	log4j.info("info...")
//	log4j.warn("warn...")
//	log4j.error("error...")
//	log4j.debug("debug...")
//	log.info("asdfasdfasdf")
		var loader = new Ext.tree.TreeLoader({ url: basePath+"manager/testData.do?action=getDataById",root:"obj"});
        var root = new Ext.tree.AsyncTreeNode
        ({
            id: "root",
            leaf: false,
            loader: loader,
            text: "产品类别",
            expandable: true,
            expanded: true
        });
//        
//        Ext.QuickTips.init();
//      comboTree = new Ext.form.TreeComboBox({
//			id:"comboTree",
//			minListWidth:60,
//            onSelect: function() { alert('good'); },
//            emptyText: '请选择',
//            renderTo: 'comboxtree',
//            treeConfig:{
//	            rootVisible:true,
//	            root: root,
//	            iconCls: "icon-tree"
//			}
//        });
	
	
	comboTree = new Ext.form.ComboTree({
			id:"comboTree",
			minListWidth:60,
            onSelect: function() { alert('good'); },
            emptyText: '请选择',
            renderTo: 'comboxtree',
            root:root,
            loader:loader
//            treeConfig:{
//	            rootVisible:true,
//	            root: root,
//	            iconCls: "icon-tree"
//			}
        });
	
})




