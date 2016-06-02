angular.module('os.dashboard', ['os.common.models'])
  .config(function($stateProvider) {
    $stateProvider
      .state('dashlet-popout', {
        url: '/dashlet-popout?dashboardId&dashletName',
        templateUrl: 'modules/dashboard/popout.html',
        controller: 'DashletPopoutCtrl',
        resolve: {
          dashboard: function($stateParams, Dashboard) {
            return Dashboard.getById($stateParams.dashboardId);
          }
        },
        parent: 'signed-in'
      });
  });
