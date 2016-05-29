
angular.module('os.common.charts', [])
  .filter('osChartType', function() {
    return function(chartTypes, seriesData) {
      var simpleTypes = ['Pie', 'PolarArea', 'Doughnut'];

      var types = chartTypes.filter(function(type) {
        if (Object.keys(seriesData).length > 1 && simpleTypes.indexOf(type.name) > -1) {
          return false;
        }
        return true;
      });

      return types;
    }
  })
  .directive('osChart', function() {
    function processData(dataDetail) {
      if (dataDetail.data) {
        return;
      }

      dataDetail.series = []; 
      dataDetail.data = [];
      angular.forEach(dataDetail.seriesData, function(value, key) {
        dataDetail.series.push(key);
        dataDetail.data.push(value);
      });
    }

    return {
      restrict: 'E',

      scope: {
        chartDetail: "=",
      },

      link: function(scope, element, attrs) {
        /*scope.options = {
          scaleOverride : true,
          scaleSteps : 6,
          scaleStepWidth: 5,
          scaleStartValue : 0
        }*/
        
        processData(scope.chartDetail);
      },

      template: function(tElem, tAttrs) {
        return '<canvas id="bar" class="chart-base" chart-type="chartDetail.type" chart-data="chartDetail.data" ' +
               '  chart-labels="chartDetail.categories" chart-series="chartDetail.series" chart-options="chartDetail.options" ' + 
               '  chart-legend="false">' +
               '</canvas>';
      }
    }
  });
