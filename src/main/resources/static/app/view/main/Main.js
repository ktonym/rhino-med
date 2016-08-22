/**
 * This class is the main view for the application. It is specified in app.js as the
 * "mainView" property. That setting automatically applies the "viewport"
 * plugin causing this view to become the body element (i.e., the viewport).
 *
 */
Ext.define('Rhino.view.main.Main', {
    extend: 'Ext.container.Viewport',
    xtype: 'app-main',
    itemId: 'mainView',


    requires: [
        'Ext.list.Tree'
//        'Ext.plugin.Viewport',
//        'Ext.window.MessageBox',
//
//        'Rhino.view.main.MainController',
//        'Rhino.view.main.MainModel',
//        'Rhino.view.main.List',
//        'Rhino.view.uw.Underwriting'
    ],

//    plugins: 'viewport',
    controller: 'main',
    viewModel: 'main',

    layout: {
        type: 'vbox',
        align: 'stretch'
    },

    listeners: {
        render: 'onMainViewRender'
    },

    items: [
        {
            xtype: 'toolbar',
            cls: 'sencha-dash-dash-headerbar toolbar-btn-shadow',
            height: 64,
            itemId: 'headerBar',
            items: [
                {
                    xtype: 'component',
                    reference: 'rhinoLogo',
                    cls: 'rhino-logo',
                    html: '<div class="main-logo"><img src="resources/images/rhino-logo.png">Rhino-Med</div>',
                    width: 250
                },
                {
                    margin: '0 0 0 8',
                    cls: 'delete-focus-bg',
                    iconCls: 'x-fa fa-navicon',
                    id: 'main-navigation-btn',
                    handler: 'onToggleNavigationSize'
                },
                {
                    xtype: 'tbspacer',
                    flex: 1
                },
                {
                    cls: 'delete-focus-bg',
                    iconCls: 'x-fa fa-search',
                    href: '#search',
                    hrefTarget: '_self',
                    tooltip: 'See latest search'
                },
                {
                    cls: 'delete-focus-bg',
                    iconCls:'x-fa fa-envelope',
                    href: '#email',
                    hrefTarget: '_self',
                    tooltip: 'Check your email'
                },
                {
                    cls: 'delete-focus-bg',
                    xtype: 'button',
                    text: 'Logout',
                    tooltip: 'End session',
                    handler: 'onLogout'
                    // handler: function(){
                    //     console.log("Clicked logout..");
                    // }
                }
//                {
//                    xtype: 'image',
//                    cls: 'header-right-profile-image',
//                    height: 35,
//                    width: 35,
//                    alt:'current user image',
//                    src: 'resources/images/user-profile/2.png'
//                }

            ]
        },
        {
            xtype: 'maincontainerwrap',
            id: 'main-view-detail-wrap',
            reference: 'mainContainerWrap',
            flex: 1,
            items: [
                {
                    xtype: 'treelist',
                    reference: 'navigationTreeList',
                    itemId: 'navigationTreeList',
                    ui: 'navigation',
                    store: 'NavigationTree',
                    width: 250,
                    expanderFirst: false,
                    expanderOnly: false,
                    listeners: {
                        selectionchange: 'onNavigationTreeSelectionChange'
                    }
                },
                {
                    xtype: 'container',
                    flex: 1,
                    reference: 'mainCardPanel',
                    cls: 'sencha-dash-right-main-container',
                    itemId: 'contentPanel',
                    layout: {
                        type: 'card',
                        anchor: '100%'
                    }
                }
            ]
        }
    ]
});
