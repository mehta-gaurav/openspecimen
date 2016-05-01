
angular.module('os.common.entities', [])
  .factory('osEntity', function($translate) {
    function EntityFactory() {
      var entity = {};
      entity.items = [];
      entity.inited = false;
      
      entity.getEntities = function() {
        return $translate('common.none').then(
          function() {
            if (entity.inited) {
              return entity.items;
            }

            entity.items = entity.items.map(
              function(item) {
                item.caption = $translate.instant(item.key);
                return item;
              }
            );

            entity.inited = true;
            return entity.items;
          }
        );
      }

      entity.addEntity = function(item) {
        var existing = undefined;
        for (var i = 0; i < entity.items.length; ++i) {
          if (entity.items[i].name == item.name) {
            existing = entity.items[i];
            break;
          }
        }

        if (!existing) {
          entity.items.push(item);
        }
      }
    
      return entity;
    };

    return EntityFactory;
  });
