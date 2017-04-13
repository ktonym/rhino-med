Ext.define('Rhino.store.CorpAnniv',{
    extend: 'Ext.data.Store',
    alias: 'store.corp-anniv',
    requires: ['Rhino.model.uw.CorpAnniv'],
    model: 'Rhino.model.uw.CorpAnniv',
    autoLoad: false,
    loadByCorporate: function(idCorp){
        this.load({
            params: {
                idCorporate: idCorp
            }
        });
    }
});