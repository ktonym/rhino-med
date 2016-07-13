Ext.define('Rhino.view.uw.corp.CorpAnnivController',{
    extend: 'Rhino.view.uw.UwController',
    alias: 'controller.corpanniv',
    requires: ['Rhino.model.uw.CorpAnniv','Rhino.util.Util'],

    onAddAnnivBtnClick: function(btn,e,eOpts){
        this.createDialog(null);
    },

    onEditAnnivBtnClick: function (btn,e,eOpts) {
        var me = this,
            vm = me.getViewModel(),
            record = vm.get('selectedAnniversary');
        this.createDialog(record);
    },

    createDialog: function(record){
        var me = this,
            view = me.getView(),
            rec;

        me.dialog = view.add({
            xtype: 'corp-anniv-form',

            viewModel: {
                schema: 'uwSchema',
                data: {
                    title: record ? 'Edit: ' + record//.get('name')
                        : 'Add Cover'
                },
                links: {
                    currentCorpAnniv: record || {
                        type: 'Rhino.model.uw.CorpAnniv',
                        create: true
                    }
                }
            },

            bind: {
                title: record ? 'Edit: ' + record.name : 'Add Cover'
            }
        });

        if(record === null){
            rec = Ext.create('Rhino.model.uw.CorpAnniv');
            me.dialog.down('form').loadRecord(rec);
        }else{
            me.dialog.down('form').loadRecord(record);
        }
        me.dialog.show();
    },

    onBack2CorpDtlsBtnClick: function(){
        this.setCurrentView('corplist');
    }

});
