var initPage=function(){
	var topbar=new Ext.Toolbar({
		items:[{
			text:'保存'
		},{
			text:"修改"
		}]
	})
	new Ext.Viewport({
		layout: 'fit',
		items: [{
			xtype:'form',
			tbar: topbar,
			frame:true,
			defaults:{
				anchor:'90%'
			},
			items:[{
				fieldLabel:'标题',
				xtype:'textfield'
			},{
				fieldLabel:'来源',
				xtype:'textfield'
			},{
				xtype:'tabpanel',
				fieldLabel:'附加属性',
				height:130,
				activeItem:1,
//				defaults :{
//					collapsed :true,
//					collapsible :true
//				},
				items:[{
					title:'图片',
					html:'test.picture'
				},{
					title:'附加文件',
					html:'test.file'
				},{
					title:'其它',
					html:'test.other'
				}]
			},{
				fieldLabel:'新闻内容',
				//xtype:'ckeditor',
				xtype:"textarea",   
				name:"content",   
				value:"",   
				width:720,   
				height:600,   
				fieldLabel:"新闻内容",   
				listeners:{   
				     "render":function(f){   
				           fckEditor = new FCKeditor("content") ;//初始化FCK   
				           fckEditor.Height=600;//设置FCK编辑器的高度   
				           fckEditor.Width=720;//设置FCK编辑器的宽度   
				           fckEditor.BasePath = basePath+"resource/fckeditor/" ;//设定FCK的源文件路径，这里要注意相对和绝对路径
//				           fckEditor.SkinPath	=  fckEditor.BasePath + 'skins/silver/'; 
				           fckEditor.ReplaceTextarea() ;//用FCK编辑器替换Ext中的textarea   
				    }   
				}       
                    
			},{
			}]
		}]
	})
}

Ext.onReady(initPage);