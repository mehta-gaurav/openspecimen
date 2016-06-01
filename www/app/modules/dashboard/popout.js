
angular.module('os.dashboard')
  .controller('DashboardPopoutCtrl', function($scope, dashboard, config) {

    function init() {
      $scope.popWindow = true;
      $scope.dashboard = dashboard;
      $scope.dashlet = {
        config : config
      }
    }

    init();
  });
