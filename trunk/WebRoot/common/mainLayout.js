var navigateData=[
{text:'栏目管理',id:'categoryManager',isLeft:'false'},
{text:'添加栏目',id:'categoryManager_add',isLeft:'true'},
{text:'管理栏目',id:'categoryManager_manager',isLeft:'true'},
{text:'新闻管理',id:'newsManager',isLeft:'false'},
{text:'站点管理',id:'siteManager',isLeft:'false'},
{text:'留言管理',id:'msgManager',isLeft:'false'},
{text:'文件管理',id:'fileManager',isLeft:'false'}]

Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
var loader = new Ext.tree.TreeLoader({
	root:'obj',
    dataUrl: basePath+'manager/auth/AuthDataService.do?action=findFunctions'
});
var root = new Ext.tree.AsyncTreeNode({
    id: 'root',
    text: '目录树根节点'
});
var treePanel = new Ext.tree.TreePanel({
    id: 'tree-panel',
    region: 'north',
    split: true,
    height: 400,
    minSize: 150,
    autoScroll: true,
    rootVisible: true,
    lines: false,
    maskDisabled: true,
    singleExpand: false,
    useArrows: true,
    hideCollapseTool: true,
    loader: loader,
    root: root
});
loader.on('beforeload', function(treeLoader, node) {
	this.baseParams.code = node.attributes.id;
}, loader);
treePanel.on('click',function addPanel(node){
	var url=basePath+node.attributes.attributs.url;//组装页面路径;
	var mainFramePanel=Ext.getCmp('mainFramePanel');
	var panel=mainFramePanel.getItem(url);
	if(typeof(panel)=='object'){
		mainFramePanel.activate(panel)
	}else{
		var iframe=document.createElement('iframe');
   		iframe.src=url;
		var panel=new  Ext.Panel({
			title:node.attributes.text,
			closable : true,
			id:url,
			html : '<iframe id = "iframe' + url+ '" name = "iframe' + url
					+ '" style = "border:0px solid blue;width:100%;height:100%" src = "' +url  + '"/>'
		}) 
		mainFramePanel.add(panel);
		mainFramePanel.activate(panel)
	}
	
});
function initPage(){
	Ext.QuickTips.init();
	
	var detailsPanel = {
		id: 'details-panel',
        title: 'Details',
        region: 'center',
        bodyStyle: 'padding-bottom:15px;background:#eee;',
		autoScroll: true,
		html: '<p class="details-info">When you select a layout from the tree, additional details will display here.</p>'
    };
	var contentPanel = {
          //  layout: 'card',
		  	xtype:'tabpanel',
			activeItem: 1,
            region: 'center',
            id: 'mainFramePanel',
            margins: '0 0 5 5',
			activeItem: 0,
			border: false,
			items: [{
				title:"1111111",
				closable:true,
				html:"1111111111111111111111111111111111111"
			}]
        }
		var optPanel={
           	layout: 'border',
	    	id: 'layout-browser',
	        region:'west',
	        border: false,
	        split:true,
			margins: '2 0 5 5',
	        width: 275,
	        minSize: 100,
	        maxSize: 500,
			items: [treePanel,detailsPanel]
        }
      new Ext.Viewport({
        layout: 'border',
        title: 'Ext Layout Browser',
        renderTo: Ext.getBody(),
        items: [{ 
            xtype: 'box',
            region: 'north',
			applyTo: 'header',
            title: "欢迎来到TinyCms!",
            height: 80
		//	}]
        }, optPanel,contentPanel ]
    });
    
}

Ext.onReady(initPage);
