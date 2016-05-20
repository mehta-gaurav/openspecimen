
angular.module('openspecimen')
  .filter('osChartType', function() {
    return function(chartTypes, isSeriesData) {
      var simpleTypes = ['Pie', 'PolarArea', 'Doughnut'];

      var types = chartTypes.filter(function(type) {
        if (isSeriesData && simpleTypes.indexOf(type.name) > -1) {
          return false;
        }
        return true;
      });

      return types;
    }
  })
  .directive('osChart', function() {
    return {
      restrict: 'E',

      templateUrl: 'modules/common/chart.html',

      scope: {
        name:  "=",
        chartDetail: "="
      },

      link: function(scope, element, attrs) {
        /*scope.options = {
          scaleOverride : true,
          scaleSteps : 6,
          scaleStepWidth: 5,
          scaleStartValue : 0
        }*/

        scope.chartTypes = [
          {name: 'Bar', caption: 'Bar'}, 
          {name: 'PolarArea', caption: 'Polar Area'}, 
          {name: 'Line', caption: 'Line'}, 
          {name: 'Radar', caption: 'Radar'},
          {name: 'Pie', caption: 'Pie'},
          {name: 'Doughnut', caption: 'Doughnut'}
        ];

        scope.changeType = function (chartType) {
          scope.chartDetail.type = chartType.name;
          if (scope.series) {
            return;
          }

          var type = scope.chartDetail.type;
          var data = scope.chartDetail.data;
          if (type == 'Pie' || type == 'PolarArea' || type == 'Doughnut') {
            data = typeof data[0] == 'object' ? data[0] : data; 
          } else {
            data = typeof data[0] == 'object' ? data : [data]; 
          }

          scope.chartDetail.data = data;
        }
      }
    }
  });
