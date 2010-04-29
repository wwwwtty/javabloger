var extendsConfig={
	
	initComponent: function(config) {
		if(this.functionTreePanel==null){
			this.functionTreePanel=this.createFunctionTreePanel();
		}
		if(this.editPanel===null){
			this.editPanel=this.createEditPanel();
		}
		Ext.apply(this, {
	        layout: 'fit',
	        items: [{
				labelAlign :'left',
				bodyStyle : 'padding:13px 70px 0',
				height :540,
				labelWidth :60,
				layout:'column',
				frame:true,
				items:[this.functionTreePanel,this.editPanel]
			}]
    	})
		cms.auth.AuthManagerPanel.superclass.initComponent.apply(this);
	},
	functionTreePanel:null,
	createFunctionTreePanel:function(){
		var functionTreePanel_loader = new Ext.tree.TreeLoader( {
			dataUrl:basePath+'manager/auth/AuthDataService.do?action=findFunctions',
			listeners:{
				'beforeload': function(treeLoader, node) {
					this.baseParams.code = node.attributes.id;
				}
			}
		});
		var functionTreePanel_root = new Ext.tree.AsyncTreeNode( {
			id :'root',
			text :'目录树根节点'
		});
		var that=this;
		return new Ext.tree.TreePanel({
			title: '功能树',
			bodyStyle: 'padding:7px 7px 0',
			id: 'tree-panel',
			checkModel: 'cascade', //对树的级联多选   
			onlyLeafCheckable: false,//对树所有结点都可选   
			split: true,
			width: 230,
			height: 430,
			autoScroll: true,
			rootVisible: false,
			lines: false,
			enableDD: true,
			maskDisabled: true,
			singleExpand: false,
			frame: true,
			animate: true,
			useArrows: true,
			hideCollapseTool: true,
			collapsed: false,
			collapsible: true,
			loader: functionTreePanel_loader,
			root: functionTreePanel_root,
			listeners: {
				movenode: saveNewTree,
				click: function(node,e){
					log.dir(that.editPanel);
					if(node.attributes.leaf){
						that.editPanel.getLayout().setActiveItem(that.functionEditPanel);
					//	that.roleEditPanel.hide();
					//	that.functionEditPanel.show();
					//	that.functionEditPanel.getEl().mask("请等待,正在加载!")
						panel_initData(that.functionEditPanel,null,node.attributes.attributs.entity)
					//	that.functionEditPanel.getEl().unmask() ;
					}else{
						that.editPanel.getLayout().setActiveItem(that.roleEditPanel);
//						that.functionEditPanel.hide();
//						that.roleEditPanel.show();
					//	that.roleEditPanel.getEl().mask("请等待,正在加载!")
						panel_initData(that.roleEditPanel,null,node.attributes.attributs.entity)
					//	that.roleEditPanel.getEl().unmask() ;
					}
									}
			}
		})
	},
	editPanel_isblockStore: new Ext.data.JsonStore({
	       data: [{name:'否',value:'N'},
		   			{name:'是',value:'Y'}],
		   fields:['name','value']
	    }),
		
	editPanel:null,
	functionEditPanel:null,	
	roleEditPanel:null,	
	rolesCombox:null,
	createRolesCombox:function(){
        var rolesStore = new Ext.data.JsonStore({
            url: 'manager/BaseDataService.do?action=findEntity',
            baseParams: {
                entity: 'org.cms.doamin.auth.Role',
                columns: 'roleName:name;roleCode:value',
                filter: 'enabled:' + true,
            }
        })
		    var resultTpl = new Ext.XTemplate(
		        '<tpl for="."><div class="search-item">',
		            '<h3><span>{lastPost:date("M j, Y")}<br />by {author}</span>{title}</h3>',
		            '{excerpt}',
		        '</div></tpl>'
		    );
		    
		   return new Ext.form.ComboBox({
		        store: rolesStore,
		        displayField:'name',
				displayValue:'value',
		        typeAhead: false,
		        loadingText: 'Searching...',
		        width: 570,
		        pageSize:10,
		        hideTrigger:true,
		        tpl: resultTpl,
		        applyTo: 'search',
		        itemSelector: 'div.search-item',
		    });
	},
	createFunctionEditPanel:function(){
		var that=this;
		this.functionEditPanel=new Ext.Panel({
			labelAlign: 'left',
			bodyStyle: 'padding:5px 5px 0',
			title: '编辑功能信息',
			height: 430,
			width: 300,
			labelWidth: 80,
			layout: 'form',
			frame: true,
			defaults: {
				displayField: 'name',
				valueField: 'value',
				width: 190,
				listWidth: 190,
				mode: 'local',
				triggerAction: 'all',
				selectOnFocus: true,
				xtype: 'combo',
				allowBlank: false,
			},
			items: [{
				id: 'funccode_fl',
				name:'code',
				xtype: 'textfield',
				fieldLabel: '功能代码',
			}, {
				id: 'name_fl',
				name:'name',
				xtype: 'textfield',
				fieldLabel: '功能名称',
			}, that.rolesCombox, {
				id: 'enabled',
				name:'enabled',
				fieldLabel: '是否锁定',
				store: that.editPanel_isblockStore,
			}, that.createRolesCombox(),{
				id: 'url',
				name:'url',
				fieldLabel: '功能链接',
				xtype: 'textfield',
			}, {
				id: 'info',
				name:'info',
				xtype: 'textfield',
				fieldLabel: '备注',
				height: 70
			}],
			bbar: [{
				text: '保存',
				iconCls: 'save',
				handler: function(){
				//	 commit();
				}
			}]
		})
		return this.functionEditPanel;
	},
	
	createRoleEditPanel:function(){
		var that=this;
		this.roleEditPanel=new Ext.Panel({
			labelAlign: 'left',
			bodyStyle: 'padding:5px 5px 0',
			title: '编辑角色信息',
			height: 430,
			width: 300,
			labelWidth: 80,
			layout: 'form',
			frame: true,
			defaults: {
				displayField: 'name',
				valueField: 'value',
				width: 190,
				listWidth: 190,
				mode: 'local',
				triggerAction: 'all',
				selectOnFocus: true,
				xtype: 'combo',
				allowBlank: false,
			},
			items: [{
				id: 'roleCode_fl',
				name:'roleCode',
				xtype: 'textfield',
				fieldLabel: '角色代码',
			}, {
				id: 'roleName_fl',
				name: 'roleName',
				xtype: 'textfield',
				fieldLabel: '角色名称',
			},that.createRolesCombox(), {
				id: 'enabled_role_fl',
				name:'enabled',
				fieldLabel: '是否锁定',
				store: that.editPanel_isblockStore,
			}, {
				id: 'roleDesc_fl',
				name:'roleDesc',
				fieldLabel: '角色描述',
				xtype: 'textfield',
				disabled: true,
				height:60
			}],
			bbar: [{
				text: '保存',
				iconCls: 'save',
				handler: function(){
				//	 commit();
				}
			}]});
		return this.roleEditPanel;
	},
	
	createEditPanel: function(){
		var that=this;
		this.editPanel= new Ext.Panel({
			bodyStyle: 'padding:0;maggin:0;',
			height: 430,
			width: 300,
			layout: 'card',
			activeItem: 0,
			frame: true,
			items:[that.createFunctionEditPanel(),that.createRoleEditPanel()]
		});
		return this.editPanel;
	}
	
};

Ext.namespace("cms.auth");

cms.auth.AuthManagerPanel= Ext.extend(Ext.Viewport,extendsConfig);

Ext.onReady(function(){
	new cms.auth.AuthManagerPanel();
});