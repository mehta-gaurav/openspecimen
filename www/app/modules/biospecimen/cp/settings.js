
angular.module('os.biospecimen.cp.settings', [])
  .controller('CpSettingsCtrl', function($scope, $state, CpSettingsReg) {
    function init() {
      $scope.ctx = {};
      CpSettingsReg.getEntities().then(function(entities) {
        $scope.settings = entities;
        $scope.setDefaultSettingInDropdown();
      });
    }

    $scope.onSettingSelect = function(entity) {
      $state.go(entity.state);
    }

    $scope.setDefaultSettingInDropdown = function() {
      angular.forEach($scope.settings, function(setting) {
        if (setting.state == $state.current.name) {
          $scope.ctx.setting = setting;
        }
      });
    }
    
    init();
  });
