/**
 * The main application class. An instance of this class is created by app.js when it
 * calls Ext.application(). This is the ideal place to handle application launch and
 * initialization details.
 */
Ext.define('Rhino.Application', {
    extend: 'Ext.app.Application',
    name: 'Rhino',
    stores: [
        'Rhino.store.NavigationTree'
    ],
    views: [
        'Rhino.view.login.Login',
        'Rhino.view.main.Main'
    ],
    launch: function () {
        var loggedIn;
        loggedIn = sessionStorage.getItem("RhinoLoggedIn");
        Ext.create({
            xtype: loggedIn ? 'app-main' : 'login'
        });
    },

    //init: function(application){
    //    Ext.Ajax.on('beforerequest', function (conn, options) {
    //        if (!(/^http:.*/.test(options.url) || /^https:.*/.test(options.url))) {
    //            if (typeof(options.headers) == "undefined") {
    //                options.headers = {'X-CSRFToken': Ext.util.Cookies.get('csrftoken')};
    //            } else {
    //                options.headers.extend({'X-CSRFToken': Ext.util.Cookies.get('csrftoken')});
    //            }
    //        }
    //    }, this);
    //},

    onAppUpdate: function () {
        Ext.Msg.confirm('Application Update', 'This application has an update, reload?',
            function (choice) {
                if (choice === 'yes') {
                    window.location.reload();
                }
            }
        );
    }


});

Ext.Ajax.on('requestexception', function(con,response,op,e){
    if(response.status === 401){
        var win = new Ext.Window({
            id: 'login-window',
            layout: 'fit',
            width: 300,
            height: 150,
            closable: false,
            plain: true,
            border: false,
            modal: true,
            defaultFocus: 'username',
            items: [ {
                xtype: 'login-form'
            } ]
        });
        win.show();
    } else if (response.status === 403){
        Ext.Msg.alert('Insufficient privileges','You do not have the stones to access this content');
    } else if (response.status === 500){
        var result = Ext.JSON.decode(response.responseText,true);
        if (!result){
            result = {};
            result.success = false;
            result.msg = response.responseText;
        }
        Ext.Msg.alert('Something went wrong',result.msg);
    }
});

//Ext.Ajax.on('beforerequest', function (conn, options) {
//    if (!(/^http:.*/.test(options.url) || /^https:.*/.test(options.url))) {
//        if (typeof(options.headers) == "undefined") {
//            options.headers = {'X-CSRFToken': Ext.util.Cookies.get('csrftoken')};
//        } else {
//            options.headers.extend({'X-CSRFToken': Ext.util.Cookies.get('csrftoken')});
//        }
//    }
//}, this);


