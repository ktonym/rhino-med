/**
 * Created by akipkoech on 14/11/2016.
 */
Ext.define('Rhino.view.reg.AnnivMemberModel',{
    extend: 'Ext.app.ViewModel',
    alias: 'viewmodel.anniv-member',
    stores: {
        annivMembers: {
            model: 'Rhino.model.uw.AnnivMember',
            autoLoad: false,
            loadByCorpAnniv: function(idAnniv){
                this.load({
                    params: {
                        idCorpAnniv: idAnniv
                    }
                });
            }
        },
        schemePrincipals: {
            model: 'Rhino.model.uw.Principal',
            autoLoad: false
        },
        schemeMembers: {
            model: 'Rhino.model.uw.Member',
            autoLoad: false
        }
    }
});