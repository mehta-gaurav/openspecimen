
angular.module('os.dashboard')
  .controller('DashboardPopoutCtrl', function($scope, config, DashletConfig) {
   
    function init() {
      $scope.popWindow = true;
      $scope.mode = "enlarge";
      $scope.dashlet = {
        config : config
      }

      getDataDetail($scope.dashlet);
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

    init();
  });
