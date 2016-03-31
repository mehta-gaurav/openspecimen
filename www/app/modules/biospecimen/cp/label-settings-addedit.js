
angular.module('os.biospecimen.cp.labels-addedit', ['os.biospecimen.models'])
  .controller('LabelsAddEditCtrl', function(
    $scope, $state, cp, $stateParams, PvManager) {

    function init() {
      $scope.cp = angular.copy(cp);
      $scope.ppidFmt = cp.getUiPpidFmt();

      if (!cp.spmnLabelPrintSettings || cp.spmnLabelPrintSettings.length == 0) {
        $scope.cp.spmnLabelPrintSettings = [
          {"lineage": "New"},
          {"lineage": "Derived"},
          {"lineage": "Aliquot"}
        ];
      }

      loadPvs();

      $scope.$watch('ppidFmt', function(newVal) {
        var sample = newVal.prefix || '';

        if (newVal.digitsWidth && newVal.digitsWidth > 0) {
          for (var i = 0; i < newVal.digitsWidth - 1; ++i) {
            sample += '0';
          }

          sample += '7';  
        }

        sample += (newVal.suffix || '');
        $scope.ppidFmt.samplePpid = sample;
      }, true);
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

    function getPpidFmt() {
      var result = $scope.ppidFmt.prefix || '';
      if ($scope.ppidFmt.digitsWidth) {
        result += '%0' + $scope.ppidFmt.digitsWidth + 'd';
      }

      result += ($scope.ppidFmt.suffix || '');
      return result;
    };

    $scope.saveSettings = function() {
      delete $scope.cp.repositoryNames;
      delete $scope.cp.extensionDetail;
      $scope.cp.ppidFmt = getPpidFmt();
      
      $scope.cp.$saveOrUpdate().then(
        function(savedCp) {
          angular.extend(cp, savedCp);
          $scope.$parent.processLabelFmts();
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
