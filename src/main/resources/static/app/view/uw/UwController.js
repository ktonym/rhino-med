Ext.define('Rhino.view.uw.UwController',{
    extend: 'Ext.app.ViewController',
    alias: 'controller.underwriting',
    requires: ['Rhino.util.Util'],
    mixins: ['Rhino.util.ControllerMixin'],

    init: function(){
        this.setCurrentView('corplist');
    },
    
    onBackBtnClick: function(){
        this.setCurrentView('corplist');
    },

    onMenuClick: function(menu, item){
        if(item){
            this.setCurrentView(item.routeId, item.params);
        }
    },

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

    createDialog: function(record){
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

        // if(record === null){
        //     // rec = Ext.create('Rhino.model.uw.Corporate'); //use session instead..
        //     rec = view.getSession().createRecord('Corporate');
        // }else{
        //     rec = record;
        // }
        rec = record ? record : view.getSession().createRecord('Corporate');

        me.dialog.down('form').loadRecord(rec);

        me.dialog.show();
    },

    getRecordsSelected: function(){
        var me = this,
            grid = me.lookupReference('corpList');
        return grid.getSelection();
    },

    onAnnivsBtnClick: function(btn,e,options){

        var me = this,
            vm = me.getViewModel(),
            record = vm.get('selectedCorporate'),
            store = vm.getStore('anniversaries');

        //We're loading anniversaries only relevant to the selected corporate
        store.load({params: {'idCorporate' : record.id}});
        // me.loadCorpAnnivs('corp-anniv-list');
        // this.fireViewEvent('setview', 'corp-anniv-list');
        this.setCurrentView('corp-anniv-list');
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

});