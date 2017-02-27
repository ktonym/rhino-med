/**
 * Created by akipkoech on 15/11/2016.
 */
Ext.define('Rhino.view.reg.MemberModel',{
    extend: 'Ext.app.ViewModel',
    alias: 'viewmodel.member',
    requires: ['Rhino.store.Members'],
    data: {},
    stores: {
        corpPrincipals: {
            model: 'Rhino.model.uw.Member',
            proxy: {
                type: 'ajax',
                url: '/uw/member/principals',
                /*extraParams: {
                    idCorporate: '{current.scheme.id}'
                },*/
                reader: {
                    type: 'json',
                    rootProperty: 'data',
                    totalProperty: 'results'
                }
            },
            autoLoad: false,
            loadByCorporate: function (corpId) {
                this.load({
                    params: {
                        idCorporate: corpId
                    }
                });
            }
        },
        principals: {
            source: {
                type: 'members'
            },
            autoLoad: true,
            filterBy: function (record) {
                return record.get('memberType')==='PRINCIPAL';
            }
        },
        members: {
            type: 'members'
        },
        sexes: {
            model: 'Rhino.model.TextCombo',
            data: [
                ['MALE'],['FEMALE']
            ]
        },
        memberTypes: {
            model: 'Rhino.model.TextCombo',
            data: [
                ['PRINCIPAL'],['SPOUSE'],['CHILD'],['PARENT'],['GRANDPARENT'],['OTHER']
            ]
        }
    }

});