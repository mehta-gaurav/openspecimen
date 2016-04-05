
angular.module('os.biospecimen.cp.label-settings-addedit', ['os.biospecimen.models'])
  .controller('CpLabelSettingsAddEditCtrl', function(
    $scope, $state, cp, PvManager) {

    function init() {
      $scope.cp = angular.copy(cp);

      if (!cp.spmnLabelPrintSettings || cp.spmnLabelPrintSettings.length == 0) {
        $scope.cp.spmnLabelPrintSettings = [
          {"lineage": "New"},
          {"lineage": "Derived"},
          {"lineage": "Aliquot"}
        ];
      }

      loadPvs();
    };

    function loadPvs() {
      $scope.spmnLabelPrePrintModes = PvManager.getPvs('specimen-label-pre-print-modes');
      loadLabelAutoPrintModes();
    }

    function loadLabelAutoPrintModes() {
      $scope.spmnLabelAutoPrintModes = [];

      PvManager.loadPvs('specimen-label-auto-print-modes').then(
        function(pvs) {
          if ($scope.cp.spmnLabelPrePrintMode != 'NONE') {
            $scope.spmnLabelAutoPrintModes = pvs;
          } else {
            $scope.spmnLabelAutoPrintModes = pvs.filter(
      	      function(pv) {
      	        return pv.name != 'PRE_PRINT';
      	      }
      	    );
          }
        }
      );
    }

    $scope.saveSettings = function() {
      delete $scope.cp.repositoryNames;
      delete $scope.cp.extensionDetail;
      
      $scope.cp.$saveOrUpdate().then(
        function(savedCp) {
          angular.extend(cp, savedCp);                     // updating resolved cp to use updated values on overview page.
          $state.go('cp-detail.label-settings.overview');
        }
      );
    };

    $scope.onPrePrintModeChange = function() {
      $scope.prePrintDisabled = !!$scope.cp.id && $scope.cp.spmnLabelPrePrintMode == 'NONE';

      loadLabelAutoPrintModes();
      if ($scope.prePrintDisabled) {
        angular.forEach($scope.cp.spmnLabelPrintSettings, function(setting) {
          if (setting.printMode == 'PRE_PRINT') {
            setting.printMode = '';
          }
        });
      }
    }

    init();
  });
