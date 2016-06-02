
angular.module('os.dashboard')
  .controller('DashboardCtrl', function($scope, dashboard, viewDetail) {
    function init() {
      $scope.ctx = {
        dashboard: dashboard,
        viewDetail: viewDetail
      };

      dashboard.execute();
    }

    init();
  });
