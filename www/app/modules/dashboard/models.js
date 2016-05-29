angular.module('os.dashboard')
  .factory('DashletConfig', function(osModel, $http) {
    var DashletConfig = osModel('dashlet-configs');

    DashletConfig.getDataDetail = function (name) {
      return $http.get(DashletConfig.url() + name + "/data-detail").then(
        function(result) {
          return result.data;
        }
      );
    }

    return DashletConfig;
  })
  .factory('Dashboard', function(osModel, $http) {
    var Dashboard = osModel('dashboards');

    return Dashboard;
  })
  
