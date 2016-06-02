angular.module('os.dashboard')
  .directive('osDashboard', function($state, $window) {
    return {
      restrict: 'E',

      templateUrl: 'modules/dashboard/dashboard.html',

      controller: function($scope) {
        this.zoomIn = function(dashlet) {
          $scope.ctx.dashlet = dashlet;
          $scope.ctx.view = 'zoomIn';
        }

        this.zoomOut = function() {
          $scope.ctx.dashlet = undefined;
          $scope.ctx.view = 'normal';
        }

        this.popOut = function(dashlet) {
          var qs = 'dashboardId=' + $scope.dashboard.id + '&dashletName=' + dashlet.config.name;
          $window.open('#/dashlet-popout?' + qs, 'OpenSpecimen', 'width=900,height=600,scrollbars=yes');
        }
      },

      scope: {
        dashboard: '='
      },

      link: function(scope, element, attrs, ctrl) {
        scope.ctx = {
          dashboard: scope.dashboard,
          view: 'normal'
        };
      }
    }
  });
