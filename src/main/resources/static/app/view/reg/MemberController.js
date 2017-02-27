/**
 * Created by akipkoech on 15/11/2016.
 */
Ext.define('Rhino.view.reg.MemberController',{
    extend: 'Ext.app.ViewController',
    alias: 'controller.member',

    onDeletePrincipal: function () {
        
    },

    onDeleteMember: function () {
        
    },

    afterPrincipalFormRender: function () {
        var me = this,
            vm = me.getViewModel(),
            rec = vm.get('current.principal');
        me.getView().getForm().loadRecord(rec);
    },

    onSavePrincipal: function () {
        var me = this,
            vm = me.getViewModel(),
            vw = me.getView(),
            store = vm.getStore('corpPrincipals'),
            form = vw.getForm(),
            rec = form.getRecord(),
            result;
        
        rec.save({
            /*failure: function (record, operation) {
                Rhino.util.Util.showErrorMsg('Save failed.');
            },
            success: function (record, operation) {
                Rhino.util.Util.showToast('Principal successfully saved.');
                // me.reloadPrincipals(record);
                if(typeof record.store === undefined){
                    vm.get('corpPrincipals').add(record);
                    console.log(store);
                }
                Ext.Msg.confirm('Add members','Do you want to add members to this family?',
                    function (key) {
                        if(key === 'yes'){
                            console.log('Adding members..');
                        }
                    }
                );
                
                me.closeWindow();
            },*/
            callback: function (record, operation, success) {
                result = Rhino.util.Util.decodeJSON(operation.responseText);
                console.info('Showing result..');
                console.log(result);
                console.log(vw);
                debugger;
                if(success){
                    Rhino.util.Util.showToast('Principal successfully saved.');
                    if(typeof record.store === undefined){
                        vm.get('corpPrincipals').add(record);
                        //console.log(store);
                    }
                    Ext.Msg.confirm('Add members','Do you want to add members to this family?',
                        function (key) {
                            if(key === 'yes'){

                                console.log(vw);
                            }
                        }
                    );

                    me.closeWindow();
                } else {
                    Rhino.util.Util.showErrorMsg(result.msg);
                }
                // do smth whether the save succeeded or failed
            }
        });
    },
    
    onAddMember: function () {
        
    },

    onSaveMember: function () {
        var me = this,
            vm = me.getViewModel(),
            rec = vm.get('current.member'),
            //vw = me.getView(),
            store = vm.getStore('members'),
            //form = vw.getForm(),
            //rec = form.getRecord(),
            result;
        
        rec.save({
            callback: function (record, operation, success) {
                result = Rhino.util.Util.decodeJSON(operation.responseText);
                /*console.info('Showing result..');
                console.log(result);
                console.log(vw);
                debugger;*/
                if (success) {
                    Rhino.util.Util.showToast('Member successfully saved.');
                    if (record.store === undefined) {
                        vm.getStore('members').add(record);
                    }
                    console.info('Looking at the record properties...');
                    console.log(record);
                    debugger;
                    //this.fireEvent('memberadded',record);

                    me.closeWindow();
                } else {
                    console.log('result object');
                    console.log(result);
                    Rhino.util.Util.showErrorMsg(result.msg);
                }
            }
        });
                
    },

    onDiscardMember: function () {
        var me = this,
            win = me.getView().up('window'),
            rec = me.getViewModel().get('current.member');
        if(rec.dirty){
            Ext.Msg.confirm('Revert changes?','The window has unsaved changes, do you want to close and lose them?',
                function (btn) {
                    if(btn==='yes'){
                        rec.reject();
                        if(win){
                            win.close();
                        }
                    }
                }
            );
        } else {
            if(win){
                win.close();
            }
        }

    },
    
    closeWindow: function () {
        var me = this,
            win = me.getView().up('window');
        if (win) {
            win.close();
        }
    },

    reloadPrincipals: function (rec) {
        var me = this,
            vm = me.getViewModel(),
            id = vm.get('current.scheme.id'),
            store = vm.get('corpPrincipals');
        if(rec.store===undefined){
            store.add(rec);
        }
        store.loadByCorporate(id);
        debugger;
    }
});