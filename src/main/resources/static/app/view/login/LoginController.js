Ext.define('Rhino.view.login.LoginController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.login',
    requires: [
        'Rhino.util.Util','Rhino.view.login.CapsLockTooltip'
    ],

    onTextFieldSpecialKey: function(field,e,options){
        if (e.getKey() === e.ENTER){
            this.doLogin();
        }
    },
    onTextFieldKeyPress: function(field,e,options){

        var charCode = e.getCharCode(),
            me = this;

        if((e.shiftKey && charCode >= 97 && charCode <= 122) ||
            (!e.shiftKey && charCode >= 65 && charCode <= 90)){

            if(me.capsLockTooltip === undefined){
                me.capsLockTooltip = Ext.widget('capslocktooltip');
            }
            me.capsLockTooltip.show();
        } else {
            if(me.capsLockTooltip !== undefined){
                me.capsLockTooltip.hide();
            }
        }

    },

    onLoginClick: function(button,e,opt){
        var me = this;
        console.log('clicked login!!');
        //this.getView().destroy();
        //var win = Ext.create({
        //    xtype: 'app-main'
        //});
        //win.show();
        //console.log(result.data);
        //Ext.create('Rhino.view.main.Main').show();
        //me.doLogin;
        console.log(me.lookupReference('form'));
        if(me.lookupReference('form').isValid()){
            me.doLogin;
        }
    },

    doLogin: function(){
        var me = this,
            form = me.lookupReference('form'),
            username = form.down('#username'),
            password = form.down('#password');
        this.getView().mask('Authenticating... Please wait...');

        Ext.Ajax.request({
            url: 'login',
            scope: me,

            params: {
                username: username.getValue(),
                password: password.getValue()
            },
            success: 'onLoginSuccess',
            failure: 'onLoginFailure'
        });

    },

    onLoginFailure: function(conn,response,options,eOpts){
        this.getView().unmask();
        Rhino.util.Util.showErrorMsg(conn.responseText);
    },

    onLoginSuccess: function(conn,response,options,eOpts){
        var result = Rhino.util.Util.decodeJSON(conn.responseText);
        this.getView().unmask();
        //console.log('Looking at the responseText');
        console.log(result);
        if (result.success) {
            //console.log('About to destroy this view and create app-main');
            sessionStorage.setItem("RhinoLoggedIn",true);
            this.getView().destroy();
            //var win = Ext.create({
            //    xtype: 'app-main'
            //});
            //win.show();
            console.log(result.data);
            Ext.create('Rhino.view.main.Main').show();
            //TODO set global values of username,authorities here..
        } else {
            Rhino.util.Util.showErrorMsg(result.msg);
        }
    }

    
});
