angular.module('os.dashboard')
  .directive('osDashlet', function() {

    var chartTypes = [
      {type: 'Bar',       multipleSeries: true},
      {type: 'PolarArea', multipleSeries: false},
      {type: 'Line',      multipleSeries: true},
      {type: 'Radar',     multipleSeries: true},
      {type: 'Pie',       multipleSeries: false},
      {type: 'Doughnut',  multipleSeries: false}
    ];

    function transformToChartData(data) {
      var result = {categories: [], series: [], values: []};
      if (!data) {
        return result;
      }

      result.categories = data.categories;
      angular.forEach(data.seriesData,
        function(seriesValues, seriesName) {
          result.series.push(seriesName);
          result.values.push(seriesValues);
        }
      );

      return result;
    }

    return {
      restrict: 'E',

      require: '?^osDashboard',

      templateUrl : 'modules/dashboard/dashlet.html',

      scope: {
        dashlet: '='
      },

      link: function(scope, element, attrs, dashboardCtrl) {
        scope.ctx = {
          data: {categories: [], series: [], values: []},
          options: scope.dashlet.config.chartOpts,
          chartTypes: chartTypes,
          chartTypesFilter: {}
        };

        scope.zoomIn = function() {
          dashboardCtrl.zoomIn(scope.dashlet);
          scope.dashlet.zoomedIn = true;
        }

        scope.zoomOut = function() {
          dashboardCtrl.zoomOut(scope.dashlet);
          scope.dashlet.zoomedIn = false;
        }

        scope.popOut = function() {
          dashboardCtrl.popOut(scope.dashlet);
        }

        scope.selectChartType = function(chartType) {
          if (!chartType.multipleSeries) {
            var dataCopy = scope.ctx.dataCopy;
            scope.ctx.data = {
              categories: dataCopy.categories,
              series: dataCopy.series[0],
              values: dataCopy.values[0]
            }
          } else {
            scope.ctx.data = scope.ctx.dataCopy;
          }

          scope.ctx.options.type = chartType.type;
        }

        scope.$watch('dashlet.data',
          function(data) {
            var data = scope.ctx.dataCopy = scope.ctx.data = transformToChartData(data);
            if (data.series.length > 1) {
              scope.ctx.chartTypesFilter = {multipleSeries: true};
            }
          }
        );
      }
    };
  })
