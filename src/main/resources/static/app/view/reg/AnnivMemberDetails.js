/**
 * Created by akipkoech on 14/11/2016.
 */
Ext.define('Rhino.view.reg.AnnivMemberDetails',{
    extend: 'Ext.panel.Panel',
    alias: 'widget.anniv-member-details',
    requires: [ 'Rhino.view.reg.AnnivMemberModel', 'Rhino.view.reg.AnnivMemberController'
    ],
    controller: 'anniv-member',
    viewModel: {
        type: 'anniv-member'
    },
    listeners: {
        onAfterrender: 'onRenderView'
    }

});