/**
 * Created by akipkoech on 26/10/2016.
 */
Ext.define('Rhino.view.reg.RegModel',{
    extend: 'Ext.app.ViewModel',
    alias: 'viewmodel.reg',
    requires: ['Rhino.model.uw.Corporate','Rhino.model.uw.Intermediary','Rhino.store.CorpAnniv','Rhino.store.BenefitRefs',
        /*'Rhino.model.uw.Category','Rhino.model.uw.CorpBenefit',*/
        'Rhino.store.Category','Rhino.store.CategoryBenefit','Rhino.store.PolicyMembers','Rhino.store.Members'],
    /*data:{
        current: {
            scheme: null,
            anniv: null,
            intermediary: null,
            category: null
        }
    },*/
    formulas: {
        currentScheme: {
            bind: {
                bindTo: '{schemeList.selection}',
                deep: true
            },
            get: function (scheme) {
                this.set('current.scheme', scheme);
                return scheme;
            }
        },
        currentAnniv: {
            bind: {
                bindTo: '{schemeAnnivList.selection}',
                deep: true
            },
            get: function (anniv) {
                this.set('current.anniv', anniv);
                return anniv;
            }
        },
        currentCategory: {
            bind: {
                bindTo: '{categoryList.selection}',
                deep: true
            },
            get: function (category) {
                this.set('current.category',category);
                return category;
            }
        },
        currentCategoryBenefit: {
            bind: {
                bindTo: '{corpBenefitList.selection}',
                deep: true
            },
            get: function (categoryBenefit) {
                this.set('current.categoryBenefit',categoryBenefit);
                return categoryBenefit;
            }
        },
        currentMemberPolicy: {
            bind: {
                bindTo: '{memberPolicyList.selection}',
                deep: true
            },
            get: function (memberPolicy) {
                this.set('current.member-policy',memberPolicy);
                return memberPolicy;
            }
        }
    },
    stores: {
        planTypes: {
            model: 'Rhino.model.TextCombo',
            data: [
                ['CORPORATE'],['INDIVIDUAL'],['SME']
            ]
        },
        benefitRefs: {
            type: 'benefit-refs'
        },
        benefitTypes: {
            model: 'Rhino.model.TextCombo',
            data: [
                ['INSURED'],['FUNDED']
            ]
        },
        memberTypes: {
            model: 'Rhino.model.TextCombo',
            data: [
                ['ALL'],['PRINCIPAL'],['SPOUSE'],['CHILD'],['PARENT'],['GRANDPARENT'],['OTHER']
            ]
        },
        schemes: {
            model: 'Rhino.model.uw.Corporate',
            autoLoad: true,
            session: true
        },
        terms: {
            type: 'corp-anniv',
            session: true
        },
        members: {
            type: 'members',
            autoLoad: false
        },
        uncoveredMembers: {
            type: 'members',
            autoLoad: false
        },
        policyMembers: {
            type: 'policy-members',
            autoLoad: false
        },
        intermediaries: {
            model: 'Rhino.model.uw.Intermediary',
            autoLoad: true,
            session: true
        },
        categories: {
            type: 'category'
        },
        categoryBenefits: {
            type: 'category-benefit'
        }
    }
});