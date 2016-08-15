Ext.define('Rhino.view.uw.corp.CorpController',{
    extend: 'Rhino.view.uw.UwController',
    alias: 'controller.corporate',
    requires: ['Rhino.model.uw.Corporate'],
    
    onBackBtnClick: function(){
        this.setCurrentView('corplist');
    },
    
    onDiscardClick: function(button,e,eOpts){
        console.log('Canceled corporate creation/editing');
    },
    
    onSaveCorpClick: function(btn,e,eOpts){

        store.sync({
            success: function(){
                store.load();
                Rhino.util.Util.showToast('Record saved');
            }
        });

    },

    beforeAnnivListRender: function(){

        console.log('About to load CorpAnnivList');

        //var me = this,
        //    vm = me.getViewModel(),
        //    record = vm.get('selectedCorporate');
        //
        ////vm.set('idCorporateFilter',);
        //me.getCorpAnnivStore().loadByCorporate(record.id);
        //console.log(me.getCorpAnnivStore());
    },

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

    beforeCorpFormRender: function(mimi,eOpts){

        var me = this;

        if (mimi.isNewRecord) {
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
                                me.onCancel();
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
                targetCfg: {
                    header : false
                },
                windowCfg: {
                    viewModel: {
                        data: {
                            currentCorporate: record //this.lookupReference('corpDetails').getViewModel().get('currentCorporate')
                        }
                    },
                    bind: {
                        title: 'Edit: <b>{currentCorporate.name}</b> ({currentCorporate.abbreviation})'
                    },
                    maxHeight: 450,
                    maxWidth: 500
                }
            });

    },

    createDialog: function(record){
        var me = this,
            view = me.getView(),
            rec;
        console.info("Getting a peek at the session..");
        console.log(me.getView().getSession());
        me.dialog = view.add({
                    xtype: 'corp-form',
                    session: true,
                    viewModel: {
                        schema: 'uwSchema',
                        data: {
                            title: record ? 'Edit: ' + record//.get('name')
                                : 'Add Corporate Plan'
                        },
                        links: {
                            currentCorporate: record || {
                                type: 'Rhino.model.uw.Corporate',
                                create: true
                            }
                        }
                    },

                    bind: {
                        title: record ? 'Edit: ' + record.name : 'Add Corporate Plan'
                    }
        });

        if(record === null){
            //rec = Ext.create('Rhino.model.uw.Corporate'); //use session instead..
            rec = me.getView().getSession().createRecord('Rhino.model.uw.Corporate');
            me.dialog.down('form').loadRecord(rec);
        }else{
            me.dialog.down('form').loadRecord(record);
        }
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

        //store.getProxy().getExtraParams().idCorporate = record.id;
        //We're loading anniversaries only relevant to the selected corporate
        store.load({params: {'idCorporate' : record.id}});
        console.info('Pouring out proxy of anniversaries store..');
        console.log(store);
        this.setCurrentView('corpdetails');
        //vm.set('idCorporateFilter',record.id);

        //me.loadCorpAnnivs();

    },

    loadCorpAnnivs: function(){
        console.log('About to render corp anniv list..');
        this.setCurrentView('corp-anniv-list', {
            openWindow : true
        });
    },

    onMembersBtnClick: function(){
        this.setCurrentView('corpmembers');
    },

    onCorpListItemClick: function(view, td, cellIndex, record){

    },

    onSaveCorpClick: function(){
        var me = this,
            form = me.dialog.down('form'),
            record = form.getRecord(),
            msg;

        form.updateRecord(record);

        console.log('Showing form record...');
        console.log(form.getRecord().data);

        record.save({
            callback:  function(record, operation, success) {
                var result = Rhino.util.Util.decodeJSON(operation.responseText);
                if (success) {
                    Rhino.util.Util.showToast('Success! Corporate saved.');
                    me.onCancel();
                    me.refresh();
                } else {
                    Rhino.util.Util.showErrorMsg(result.msg);
                }
            }
        });

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