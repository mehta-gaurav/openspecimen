
angular.module('os.biospecimen.cp.settings.entities', [])
  .factory('SettingsEntityReg', function($translate) {
    var entities = [];
    var inited = false;

    function getEntities() {
      return $translate('common.none').then(
        function() {
          if (inited) {
            return entities;
          }

          entities = entities.map(
            function(entity) {
              entity.caption = $translate.instant(entity.key);
              return entity;
            }
          );

          inited = true;
          return entities;
        }
      );
    }

    function addEntity(entity) {
      var exiting = undefined;
      for (var i = 0; i < entities.length; i++) {
        if (entities[i].name == entity.name) {
          exiting = entities[i];
          break;
        }
      }

      if (!exiting) {
        entities.push(entity);
      }
    }

    function init() {
      addEntity({name: 'Label', state: 'cp-detail.settings.labels', key: 'cp.label_format.title', caption: ''});
      addEntity({name: 'Catalog', state: 'cp-detail.settings.catalog', key: 'cp.catalog.title', caption: ''});
    }

    init();

    return {
      getEntities: getEntities,

      addEntity: addEntity
    }
  });
