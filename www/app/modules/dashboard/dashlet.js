
angular.module('os.dashboard')
  .controller('DashletCtrl', function($scope, Dashboard) {
    
    function loadChartTypes() {
      $scope.chartTypes = [
        {name: 'Bar', caption: 'Bar'},
        {name: 'PolarArea', caption: 'Polar Area'},
        {name: 'Line', caption: 'Line'},
        {name: 'Radar', caption: 'Radar'},
        {name: 'Pie', caption: 'Pie'},
        {name: 'Doughnut', caption: 'Doughnut'}
      ];
    }

    function init() {
      loadChartTypes();
      getDashletData($scope.dashboard, $scope.dashlet);
    }

    function getDashletData(dashboard, dashlet) {
      dashboard.getDashletData(dashlet.config.name).then(
        function(data) {
          //Showing first 10 character of category name
          var categories = data.categories;
          for (var i = 0; i < categories.length; i++) {
            categories[i] = categories[i] == null ? null : categories[i].substring(0, 10);
          }
          dashlet.data = data;
        }
      );
    }

    $scope.changeType = function (dashlet, chartType) {
      console.log(chartType);
      dashlet.config.chartOpts.type = chartType.name;
    }

    init();
  });
