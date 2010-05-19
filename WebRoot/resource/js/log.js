	var Level = {
	    NONE: 0,
	    INFO: 1,
	    DEBUG: 2,
	    INFO: 3,
	    WARN: 4,
	    ERROR: 5,
	    ALL: 10
	}
	
	var DefaultNullLogger = {
	    log: function(){},
	    debug: function(){},
	    info: function(){},
	    warn: function(){},
	    error: function(){ },
	    assert: function(){},
	    dir: function(){},
		test:function(){},
	    dirxml: function(){},
	    trace: function(){},
	    group: function(){},
	    groupCollapsed: function(){ },
	    groupEnd: function(){},
	    time: function(){},
	    timeEnd: function(){},
	    profile: function(){},
	    profileEnd: function(){},
	    count: function(){}
	}
	
	 function DefaultConsoleLogger(){
		this.level=Level.ALL;
		this.delegate=window._firebug;
		this.setLevel = function(Level){
			this.level = Level;
		};
		this.info= function(){
			if(this.level>=Level.INFO){
				this.delegate.notifyFirebug(arguments, 'info', 'firebugAppendConsole');
			}
	    };
		this.log=function(){
			if(this.level>=Level.NONE){
				this.delegate.notifyFirebug(arguments, 'log', 'firebugAppendConsole');
			}
	    };
		this.debug= function(){
			if(this.level>=Level.DEBUG){
				this.delegate.notifyFirebug(arguments, 'debug', 'firebugAppendConsole');
			}
	    };
		this.warn= function(){
			if(this.level>=Level.WARN){
				this.delegate.notifyFirebug(arguments, 'debug', 'firebugAppendConsole');
			}
	    };
		this.error=function(){
			if(this.level>=Level.ERROR){
				this.delegate.notifyFirebug(arguments, 'error', 'firebugAppendConsole');
			}
	    };
		this.test=function(msg,obj){
		  	if(this.level>=Level.INFO){
				this.delegate.notifyFirebug(arguments, 'info', 'firebugAppendConsole');
			}
			this.dir(obj);
		  }

	};
	DefaultConsoleLogger.prototype=window.console;

	function LogRepository(){
        if (!Ext.isIE && window.console||window._firebug) {
            return new DefaultConsoleLogger();
        }
        else {
            return DefaultNullLogger
        }
    }
	
	var log=LogRepository();