
angular.module('os.dashboard')
  .controller('DashletCtrl', function($scope) {
    
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
    }

    $scope.changeType = function (dashlet, chartType) {
      var dataDetail = dashlet.dataDetail;
      dataDetail.type = chartType.name;
      if (Object.keys(dataDetail.seriesData).length > 1) {
        return;
      }

      var type = dataDetail.type;
      var data = dataDetail.data;
      if (type == 'Pie' || type == 'PolarArea' || type == 'Doughnut') {
        dataDetail.data = typeof(data[0]) == 'object' ? data[0] : data;  
      } else {
        dataDetail.data = typeof(data[0]) == 'object' ? data : [data];
      }
    }

    init();
  });
