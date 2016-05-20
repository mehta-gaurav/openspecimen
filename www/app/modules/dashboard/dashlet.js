angular.module('os.dashboard.dashlet', ['os.common.models'])
  .factory('Dashlet', function(osModel, $http) {
    var Dashlet = osModel('dashboards');

    Dashlet.getViewDashlets = function(name) {
      return $http.get(Dashlet.url() + "view-dashlets/byname?name=" + name ).then(
        function (result) {
          return result.data; 
        } 
      );
    }

    Dashlet.getChartDetail = function (id) {
      return $http.get(Dashlet.url() + id + "/chartDetail").then(
        function(result) {
          return result.data;
        }
      );
    }

    return Dashlet;
  });
