
angular.module('os.dashboard.list', ['os.dashboard.dashlet'])
  .controller('DashletListCtrl', function($scope, dashboardDetail, Dashlet) {
    
    function init() {
      $scope.dashboardDetail = dashboardDetail;
      loadViewDashlets(dashboardDetail.name);
    }

    function loadViewDashlets(name) {
      Dashlet.getViewDashlets(name).then(
        function(viewDashlets) {
          $scope.viewDashlets = viewDashlets;
        }
      );
    }

    $scope.getChartDetail = function(dashlet) {
      Dashlet.getChartDetail(dashlet.id).then(
        function(chartDetail) {
          var labels = chartDetail.labels;
          for (var i = 0; i < labels.length; i++) {
            labels[i] = labels[i] == null ? null : labels[i].substring(0, 10);
          }
          dashlet.chartDetail = chartDetail;
        }
      );
    }

    init();
  });
