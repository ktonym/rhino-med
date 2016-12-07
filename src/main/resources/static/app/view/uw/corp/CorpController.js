Ext.define('Rhino.view.uw.corp.CorpController',{
    extend: 'Ext.app.ViewController',
    alias: 'controller.corporate',
    requires: ['Rhino.model.uw.Corporate'],

    // setCurrentView: function(view,params){
    //     var contentPanel = this.getView().lookupReference('underwriting').down('#contentPanel');
    //     console.info('CorpController: Looking at #contentPanel')
    //     console.log(contentPanel);
    //     //We skip rendering for the following scenarios:
    //     // * There is no contentPanel
    //     // * view xtype is not specified
    //     // * current view is the same
    //     if(!contentPanel || view === '' || (contentPanel.down() && contentPanel.down().xtype === view)){
    //         console.info('CorpController: Did not get a #contentPanel!!');
    //         return false;
    //     }
    //
    //     if (params && params.openWindow){
    //         var cfg = Ext.apply({
    //             xtype: 'uwwindow',
    //             items: [
    //                 Ext.apply({
    //                     xtype: view
    //                 }, params.targetCfg)
    //             ]
    //         }, params.windowCfg);
    //
    //         Ext.create(cfg);
    //     } else {
    //         Ext.suspendLayouts();
    //
    //         contentPanel.removeAll(true);
    //         contentPanel.add(
    //             Ext.apply({
    //                 xtype: view
    //             }, params)
    //         );
    //
    //         Ext.resumeLayouts(true);
    //
    //     }
    //
    // },

    // onBackBtnClick: function(){
    //     this.setCurrentView('corplist');
    // },

    // onSaveCorpClick: function(btn,e,eOpts){
    //
    //     store.sync({
    //         success: function(){
    //             store.load();
    //             Rhino.util.Util.showToast('Record saved');
    //         }
    //     });
    //
    // },

/*    beforeAnnivListRender: function(){

        console.log('About to load CorpAnnivList');

        //var me = this,
        //    vm = me.getViewModel(),
        //    record = vm.get('selectedCorporate');
        //
        ////vm.set('idCorporateFilter',);
        //me.getCorpAnnivStore().loadByCorporate(record.id);
        //console.log(me.getCorpAnnivStore());
    },
*/
    //beforeAnnivListRender : function(me,eOpts){
    //    var vm = me.getViewModel(),
    //        store = vm.getStore('anniversaries');
    //    //store.getProxy().setExtraParam('idCorporate',2);
    //    //store.load({params: {'idCorporate' : 2}});
    //    console.info('Pouring out proxy of anniversaries store..');
    //    console.log(store);
    //
    //    me.getViewModel().setData(me.config.data);
    //},

    onAddCorpBtnClick: function(btn,e,eOpts){
        this.createDialog(null);
    },

    onEditCorpBtnClick: function (btn,e,eOpts) {
        var me = this,
            vm = me.getViewModel(),
            record = vm.get('selectedCorporate');
        this.createDialog(record);
    },

    onDelCorpBtnClick: function(){
        var me = this,
            vm = me.getViewModel(),
            record = vm.get('selectedCorporate');

        Ext.Msg.confirm('Confirmation','Are you sure you want to delete this corporate?',
            function(id){
                if (id === 'yes'){
                    record.erase({
                        callback: function(record,operation,success){
                            var result = Rhino.util.Util.decodeJSON(operation.responseText);
                            if (success) {
                                Rhino.util.Util.showToast(result.msg);
                                // me.onCancel();
                                me.refresh();
                            } else {
                                Rhino.util.Util.showErrorMsg(result.msg);
                            }
                        }
                    });
                }
            }
        );

    },

    onEditCorpClick: function(btn,e,eOpts){

        var me = this,
            vm = me.getViewModel(),
            record = vm.get('selectedCorporate');


        this.setCurrentView('corp-form',
            {
                openWindow: true,
                windowCfg: {
                    header : false,
                    maxHeight: 450,
                    maxWidth: 500
                },
                targetCfg: {
                    viewModel: {
                        data: {
                            currentCorporate: record //this.lookupReference('corpDetails').getViewModel().get('currentCorporate')
                        }
                    },
                    bind: {
                        title: 'Edit: <b>{currentCorporate.name}</b> ({currentCorporate.abbreviation})'
                    }
                }
            });

    }

/*    createDialog: function(record){
        var me = this,
            view = me.getView(),
            rec;
        // console.info("Getting a peek at the session..");
        // console.log(me.getView().getSession());
        me.dialog = view.add({
                    xtype: 'corp-form',
                    session: true,
                    viewModel: {
                        schema: 'uwSchema',
                        links: {
                            currentCorporate: record || {
                                type: 'Rhino.model.uw.Corporate',
                                create: true
                            }
                        }
                    },

                    bind: {
                        title: record ? 'Edit: <b>{currentCorporate.name}</b> ({currentCorporate.abbreviation})' : 'Add Corporate'
                    }
        });

        if(record === null){
            // rec = Ext.create('Rhino.model.uw.Corporate'); //use session instead..
            rec = view.getSession().createRecord('Corporate');
        }else{
            rec = record;
        }

        me.dialog.down('form').loadRecord(rec);

        me.dialog.show();
    },

    getRecordsSelected: function(){
        var me = this,
            grid = me.lookupReference('corpList');
        return grid.getSelection();
    },

    onDelCorpBtnClick: function(){
        Ext.Msg.confirm('Confirm','Are you sure?','onCorpDelete',this);
    },

    onAnnivsBtnClick: function(btn,e,options){

        var me = this,
            vm = me.getViewModel(),
            record = vm.get('selectedCorporate'),
            store = vm.getStore('anniversaries');

        //We're loading anniversaries only relevant to the selected corporate
        store.load({params: {'idCorporate' : record.id}});

        // me.loadCorpAnnivs('corp-anniv-list');
        this.fireViewEvent('setCV','corp-anniv-list');
    },

    loadCorpAnnivs: function(child){
        var me = this,
            view = me.getView();

        view.add(
            Ext.apply({
                xtype: child
            })
        );

    },

    onMembersBtnClick: function(){
        console.log('clicked on members...');
        this.setCurrentView('corpmembers');
    },

    onCorpListItemClick: function(view, td, cellIndex, record){

    },

    onSaveCorpClick: function(btn,e,eOpts){
        var me = this;
        me.onSaveBtnClick();
    },
    refresh: function(){
        var me = this;
        me.getViewModel().data.corporates.load();
    },
    onCancel: function(){
        var me = this;
        me.dialog = Ext.destroy(me.dialog);
    }
*/

});