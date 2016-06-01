
angular.module('os.dashboard', ['os.common.models'])
  .config(function($stateProvider) {
    $stateProvider
      .state('dashboard-popout', {
        url: '/dashboard-popout?dashboardId&configId',
        templateUrl: 'modules/dashboard/popout.html',
        controller: 'DashboardPopoutCtrl',
        resolve: {
          dashboard: function($stateParams, Dashboard) {
            return new Dashboard({id: $stateParams.dashboardId});
          },
          config: function ($stateParams, DashletConfig) {
            return DashletConfig.getById($stateParams.configId);
          }
        },
        parent: 'signed-in'
      });
  });
