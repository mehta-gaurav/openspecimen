
angular.module('os.dashboard', ['os.common.models'])
  .config(function($stateProvider) {
    $stateProvider
      .state('dashboard-popout', {
        url: '/dashboard-popout?dashletId',
        templateUrl: 'modules/dashboard/popout.html',
        controller: 'DashboardPopoutCtrl',
        resolve: {
          config: function ($stateParams, DashletConfig) {
            console.log("id=" + $stateParams.dashletId);
            return DashletConfig.getById($stateParams.dashletId);
          }
        },
        parent: 'signed-in'
      });
  });
