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
			dataUrl:basePath+'manager/auth/AuthDataService.do?action=findFunctions&valid=A',
			root:'obj',
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
					if(node.attributes.leaf){
						that.editPanel.getLayout().setActiveItem(that.functionEditPanel);
						panel_initData(that.functionEditPanel,null,node.attributes.attributs.entity)
					}else{
						that.editPanel.getLayout().setActiveItem(that.roleEditPanel);
						panel_initData(that.roleEditPanel,null,node.attributes.attributs.entity)
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
	rolesStore : new Ext.data.JsonStore({
			url:basePath+'manager/auth/AuthDataService.do?action=findAllRole',
			autoLoad:true,
			root:'obj',
			fields:['name','tip','value']
		}),

		editPanel:null,
		functionEditPanel:null,	
		roleEditPanel:null,	
	
	createFunctionEditPanel:function(){
		var that=this;
		
		this.functionEditPanel=new Ext.form.FormPanel({
			title: '编辑功能信息',
			height: 430,
			width: 300,
			labelWidth: 80,
			layout: 'form',
			frame: true,
			defaults: {
				xtype: 'textfield',
				allowBlank: false,
				anchor:'95%'
			},
			items: [{
					id: 'funccode_fl',
					name:'code',
					fieldLabel: '功能代码'
				}, {
					id: 'name_fl',
					name:'name',
					fieldLabel: '功能名称'
				},{
					id: 'url_FL',
					name:'url',
					fieldLabel: '功能链接'
				},{
					name:'groupCode',
					xtype:'combotree',
					minListWidth:60,
					fieldLabel: '角色分组',
					editable :false,
					repRoot:'obj',
					url:basePath+'manager/auth/AuthDataService.do?action=findAllRole&valid=valid',
		            onSelect: function() { alert('good'); },
		            emptyText: '请选择'
				},{
					id: 'enabled_FL',
					name:'enabled',
					displayField: 'name',
					valueField: 'value',
					editable :false,
					mode: 'local',
					triggerAction: 'all',
					selectOnFocus: true,
					xtype: 'combo',
					fieldLabel: '是否锁定',
					store: that.editPanel_isblockStore
				},{
					id: 'info_FL',
					name:'info',
					fieldLabel: '备注',
					xtype:'textarea',
					height: 70
				}],
			bbar: [{
					text:"新增功能点",
					handler: function(bt,e){
						this.functionEditPanel.getForm().reset();
					}.createDelegate(this)
				},{
				text: '保存',
				iconCls: 'save',
				handler: function(bt,e){
					var parent=bt.ownerCt.ownerCt;
					if(parent.getForm().isValid()){
						var data=panel_gatherData(parent);
						Connection.submit({
							url:basePath+'manager/auth/AuthDataService.do?action=saveFunction',
							params:{
								json:Ext.encode(data)
							},
							success:function(rep){
								var tip=rep.success==true?"成功":"失败"; 
								Ext.Msg.alert(tip,rep.msg);
							}
						})
					}
				}
			}]
		})
		return this.functionEditPanel;
	},
	
	createRoleEditPanel:function(){
		var that=this;
		var rp=new Ext.form.FormPanel({
			labelAlign: 'left',
			bodyStyle: 'padding:5px 5px 0',
			title: '编辑角色信息',
			height: 430,
			width: 300,
			labelWidth: 80,
			frame: true,
			defaults: {
				anchor:'95%',
				displayField: 'name',
				valueField: 'value',
				mode: 'local',
				triggerAction: 'all',
				selectOnFocus: true,
				xtype: 'combo',
				allowBlank: false
			},
			items: [{
				name:'roleId',
				xtype: 'hidden',
				fieldLabel: '角色代码'
			},{
				id: 'roleCode_fl',
				name:'roleCode',
				xtype: 'textfield',
				fieldLabel: '角色代码'
			}, {
				id: 'roleName_fl',
				name: 'roleName',
				xtype: 'textfield',
				fieldLabel: '角色名称'
			},{
				name:'ParentRoleCode',
				xtype:'combotree',
				editable :false,
//				minListWidth:60,
				fieldLabel: '角色分组',
				repRoot:'obj',
				url:basePath+'manager/auth/AuthDataService.do?action=findAllRole&valid=valid',
	            onSelect: function() { alert('good'); },
	            emptyText: '请选择'
			},{
				id: 'enabled_role_fl',
				name:'enabled',
				editable :false,
				fieldLabel: '是否锁定',
				store: that.editPanel_isblockStore
			}, {
				id: 'roleDesc_fl',
				name:'roleDesc',
				fieldLabel: '角色描述',
				xtype: 'textarea',
				disabled: true,
				height:60
			}],
			bbar: [{
					text:"新增角色",
					handler: function(bt,e){
						rp.getForm().reset();
					}
				},{
				text: '保存',
				iconCls: 'save',
				handler: function(bt,e){
					var parent=bt.ownerCt.ownerCt;
					if(parent.getForm().isValid()){
						var data=panel_gatherData(parent);
						Connection.submit({
							url:basePath+'manager/auth/AuthDataService.do?action=saveRole',
							params:{
								json:Ext.encode(data)
							},
							success:function(rep){
								var tip=rep.success==true?"成功":"失败"; 
								Ext.Msg.alert(tip,rep.msg);
							}
						})
					}
				}
			}]});
			this.roleEditPanel=rp;
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