
angular.module('os.biospecimen.cp.label-settings-overview', [])
  .controller('CpLabelSettingsOverviewCtrl', function($scope, $translate, cp) {

    function init() {
      $scope.userInputLabels = '';
      $translate('cp.label_format.ppid').then(
        function() {
          var result = [];

          if (cp.manualPpidEnabled) {
            result.push($translate.instant('cp.label_format.ppid'));
          }

          if (cp.manualVisitNameEnabled) {
            result.push($translate.instant('cp.label_format.visit'));
          }

          if (cp.manualSpecLabelEnabled) {
            result.push($translate.instant('cp.label_format.specimen'));
          }

          $scope.userInputLabels = result.join(", ");
        }
      );
    };

    init();
  });
