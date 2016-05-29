
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
          angular.forEach($scope.dashlets, function(dashlet) {
            getDataDetail(dashlet);
          });
        }
      );
    }

    function getDataDetail(dashlet) {
      DashletConfig.getDataDetail(dashlet.config.id).then(
        function(dataDetail) {
          var categories = dataDetail.categories;
          for (var i = 0; i < categories.length; i++) {
            categories[i] = categories[i] == null ? null : categories[i].substring(0, 10);
          }
          dataDetail.type = 'Line';
          dashlet.dataDetail = dataDetail;
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

      var href = "/#/dashboard-popout?dashletId=" + dashlet.config.id;
      window.open(href, 'OpenSpecimen', 'width=900,height=600,scrollbars=yes');
    }

    init();
  });
