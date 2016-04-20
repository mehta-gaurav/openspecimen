
angular.module('os.biospecimen.cp.settings', [])
  .controller('SettingsCtrl', function($scope, $state, SettingsEntityReg) {
    function init() {
      $scope.ctx = {};
      SettingsEntityReg.getEntities().then(function(entities) {
        $scope.settings = entities;
        $scope.setSelectedSetting();
      });
    }

    $scope.onSettingSelect = function(entity) {
      $state.go(entity.state);
    }

    $scope.setSelectedSetting = function() {
      angular.forEach($scope.settings, function(setting) {
        if (setting.state == $state.current.name) {
          $scope.ctx.setting = setting;
        }
      });
    }
    
    init();
  });
