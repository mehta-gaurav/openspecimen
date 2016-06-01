
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
    function processData(scope, data) {
      if (data.data) {
        return;
      }

      data.series = [];
      data.metric = scope.metric = [];
      angular.forEach(data.seriesData, function(value, key) {
        data.series.push(key);
        data.metric.push(value);
      });
    }

    return {
      restrict: 'E',

      scope: {
        data: "=",
        options: "="
      },

      link: function(scope, element, attrs) {
        scope.options.type = scope.options.type || 'Line';

        scope.$watch('options.type', function(newVal, oldVal) {
          if (scope.data.series.length > 1) {
            return;
          }

          var type = scope.options.type;
          var data = scope.data.metric;
          if (type == 'Pie' || type == 'PolarArea' || type == 'Doughnut') {
            // simple chart need only array of number
            scope.data.metric = scope.metric[0];
          } else {
            scope.data.metric = scope.metric;
          }
        });

        processData(scope, scope.data);
      },

      template: function(tElem, tAttrs) {
        return '<canvas id="bar" class="chart-base" chart-type="options.type" chart-data="data.metric" ' +
               '  chart-labels="data.categories" chart-series="data.series" chart-options="options" ' +
               '  chart-legend="false">' +
               '</canvas>';
      }
    }
  });
