angular.module('os.dashboard')
  .factory('DashletConfig', function(osModel) {
    var DashletConfig = osModel('dashlet-configs');
    return DashletConfig;
  })
  .factory('Dashboard', function(osModel, $http) {
    var Dashboard = osModel('dashboards');

    Dashboard.prototype.execute = function() {
      var that = this;
      angular.forEach(this.dashlets,
        function(dashlet) {
          that.executeDashlet(dashlet);
        }
      );
    }

    Dashboard.prototype.executeDashlet = function(dashlet) {
      var params = {params: {dashlet: dashlet.config.name}};
      return $http.get(Dashboard.url() + this.$id() + '/data', params).then(
        function (resp) {
          return (dashlet.data = resp.data);
        }
      );
    }

    Dashboard.prototype.getDashletByName = function(name) {
      for (var i = 0; i < this.dashlets.length; ++i) {
        if (this.dashlets[i].config.name == name) {
          return this.dashlets[i];
        }
      }

      return undefined;
    }

    return Dashboard;
  })
