angular.module('os.administrative.form.entities', ['os.common.entities'])
  .factory('FormEntityReg', function(osEntity, $translate) {
    var entities = osEntity();

    entities.addEntity({name: 'Participant', key: 'entities.participant', caption: ''});
    entities.addEntity({name: 'SpecimenCollectionGroup', key: 'entities.visit', caption: ''});
    entities.addEntity({name: 'Specimen', key: 'entities.specimen', caption: ''});
    entities.addEntity({name: 'SpecimenEvent', caption: '', key: 'entities.specimen_event', allCps: true});

    return {
      getEntities: entities.getEntities,

      addEntity: entities.addEntity
    }
  });
