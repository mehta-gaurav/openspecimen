angular.module('os.dashboard')
  .controller('DashletPopoutCtrl', function($scope, $stateParams, dashboard) {

    function init() {
      var dashlet = dashboard.getDashletByName($stateParams.dashletName);
      dashlet.poppedOut = true;

      $scope.ctx = {
        dashboard: dashboard,
        dashlet: dashlet
      };

      dashboard.executeDashlet(dashlet);
    }

    init();
  });
