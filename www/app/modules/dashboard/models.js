angular.module('os.dashboard')
  .factory('DashletConfig', function(osModel, $http) {
    var DashletConfig = osModel('dashlet-configs');

    return DashletConfig;
  })
  .factory('Dashboard', function(osModel, $http) {
    var Dashboard = osModel('dashboards');

    Dashboard.prototype.getDashletData = function(dashletName) {
      return $http.get(Dashboard.url() + this.$id() + "/data?dashlet=" + dashletName).then(
        function (result) {
          return result.data;
        }
      );
    }

    return Dashboard;
  })
