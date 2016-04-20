
angular.module('os.biospecimen.cp.label-settings', ['os.biospecimen.models'])
  .controller('LabelSettingsCtrl', function($scope, $translate, cp, PvManager) {

    function init() {
      $scope.settingCtx = {};
      $scope.setSelectedSetting();
      
      if (!cp.spmnLabelPrintSettings || cp.spmnLabelPrintSettings.length == 0) {
        $scope.settingCtx.cp.spmnLabelPrintSettings = [
          {"lineage": "New"},
          {"lineage": "Derived"},
          {"lineage": "Aliquot"}
        ];
      }

      loadPvs();
      setViewCtx();
    };
    
    function setViewCtx() {
      var ctx = {view: 'view_setting', cp: cp};
      angular.extend($scope.settingCtx, ctx);
      processManualUserInputs();
      $scope.onPrePrintModeChange();
    }
    
    function setEditCtx() {
      var ctx = {view: 'edit_setting', cp: angular.copy(cp)};
      angular.extend($scope.settingCtx, ctx);
      $scope.onPrePrintModeChange();
    }
    
    $scope.editLabelSettings = function() {
      setEditCtx();
    }
    
    $scope.revertEdit = function() {
      setViewCtx();
    }
    
    $scope.saveSettings = function() {
      delete $scope.settingCtx.cp.repositoryNames;
      delete $scope.settingCtx.cp.extensionDetail;
      delete $scope.settingCtx.cp.catalogSetting;
      
      $scope.settingCtx.cp.$saveOrUpdate().then(
        function(savedcp) {
          angular.extend(cp, savedcp);
          setViewCtx();
        }
      );
    };
    
    var processManualUserInputs = function() {
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
    }
    
    function loadPvs() {
      $scope.spmnLabelPrePrintModes = PvManager.getPvs('specimen-label-pre-print-modes');
      loadLabelAutoPrintModes();
    }

    function loadLabelAutoPrintModes() {
      $scope.spmnLabelAutoPrintModes = [];

      PvManager.loadPvs('specimen-label-auto-print-modes').then(
        function(pvs) {
          if ($scope.settingCtx.cp.spmnLabelPrePrintMode != 'NONE') {
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

    $scope.onPrePrintModeChange = function() {
      $scope.prePrintDisabled = !!$scope.settingCtx.cp.id && $scope.settingCtx.cp.spmnLabelPrePrintMode == 'NONE';

      loadLabelAutoPrintModes();
      if ($scope.prePrintDisabled) {
        angular.forEach($scope.settingCtx.cp.spmnLabelPrintSettings, function(setting) {
          if (setting.printMode == 'PRE_PRINT') {
            setting.printMode = '';
          }
        });
      }
    }

    init();
  });
