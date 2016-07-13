
Ext.define("Rhino.view.login.Login",{
    extend: "Ext.window.Window",
    xtype: 'login',
    requires: [
        "Rhino.view.login.LoginController","Rhino.view.login.CapsLockTooltip"
    ],

    controller: "login",
    bodyPadding: 10,
    title: 'Rhino Login',
    closable: false,
    autoShow: true,
    iconCls: 'fa fa-key fa-lg',
    items: [
        {
            xtype: 'form',
            reference: 'form',
            items: [
                {
                    xtype: 'textfield',
                    name: 'username',
                    itemId: 'username',
                    fieldLabel: 'Username',
                    allowBlank: false
                },
                {
                    xtype: 'textfield',
                    inputType: 'password',
                    name: 'password',
                    itemId: 'password',
                    fieldLabel: 'Password',
                    allowBlank: false,
                    enableKeyEvents: true,
                    listeners: {
                        keypress: 'onTextFieldKeyPress',
                        specialKey: 'onTextFieldSpecialKey'
                    }
                }
            ],

            bbar: {
                //overflowHandler: 'menu',
                items: [

                    '->',
                    {
                        xtype: 'button',
                        ui: 'soft-blue',
                        text: 'Login',
                        handler: 'doLogin'
                    }

                ]
            }
        }
    ]
});
