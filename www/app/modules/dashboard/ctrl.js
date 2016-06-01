
angular.module('os.dashboard')
  .controller('DashboardCtrl', function($scope, dashboardDetail, Dashboard, DashletConfig) {

    function init() {
      $scope.mode = 'normal';
      $scope.dashboardDetail = dashboardDetail;
      loadDashboard();
    }

    function loadDashboard() {
      Dashboard.getById(1).then(
        function(dashboard) {
          $scope.dashboard = dashboard;
          $scope.dashlets = dashboard.dashlets;
        }
      );
    }

    $scope.enlarge = function(dashlet) {
      $scope.dashlet = dashlet;
      $scope.mode = 'enlarge';
    }

    $scope.normal = function() {
      $scope.mode = 'normal';
      $scope.dashlet = undefined;
    }

    $scope.popout = function (dashlet) {
      if (!window.focus) {
        return true;
      }

      var href = "#/dashboard-popout?dashboardId=1&configId=" + dashlet.config.id;
      window.open(href, 'OpenSpecimen', 'width=900,height=600,scrollbars=yes');
    }

    init();
  });
