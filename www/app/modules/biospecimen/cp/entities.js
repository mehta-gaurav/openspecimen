
angular.module('os.biospecimen.cp.entities', ['os.common.entities'])
  .factory('CpSettingsReg', function(osEntity, $translate) {
    var entities = osEntity();

    entities.addEntity({name: 'Label', state: 'cp-detail.settings.labels', key: 'cp.label_format.title', caption: ''});
    entities.addEntity({name: 'Catalog', state: 'cp-detail.settings.catalog', key: 'cp.catalog.title', caption: ''});
    
    return {
      getEntities: entities.getEntities,

      addEntity: entities.addEntity
    }
  });
