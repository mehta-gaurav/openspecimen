angular.module('os.biospecimen.specimen.bulkaddevent', ['os.biospecimen.models'])
  .controller('BulkAddEventCtrl', function($scope, $translate, SpecimensHolder, Specimen, SpecimenEvent, Alerts,
    Util) {
    function init() {
      $scope.selectedEvent = {};
      $scope.eventTableCtrl = {};
      
      $scope.specimens = SpecimensHolder.getSpecimens() || [];
      SpecimensHolder.setSpecimens(null);
      loadSpecimenEvents();
    }
    
    function loadSpecimenEvents() {
      SpecimenEvent.getEvents().then(
        function(events) {
          $scope.specimenEvents = events.filter(function(event) {
            return !event.sysForm;
          });
        }
      );
    }
    
    function getSpecimenOpts(specimens) {
      return specimens.map(
        function(spec) {
          return {
            key: {
              id: spec.id,
              objectId: spec.id,
              label: spec.label
            },
            appColumnsData: {},
            records: []
          };
        }
      );
    }

    function onValidationError() {
      Alerts.error('common.form_validation_error');
    }

    $scope.addSpecimens = function(labels) {
      return Specimen.listByLabels(labels).then(
        function (specimens) {
          if (specimens.length == labels.length) {
            Util.appendAll($scope.specimens, specimens);
            return true;
          } else {
            Alerts.error('specimens.bulk_events.specimens_not_found_or_no_access');
            return false;
          }
        }
      );
    }
    
    $scope.removeSpecimen = function(index) {
      $scope.specimens.splice(index, 1);
    }
    
    $scope.initEventOpts = function() {
      if (!$scope.selectedEvent.formId) {
        return;
      }
      
      var opts = {
        formId            : $scope.selectedEvent.formId,
        appColumns        : [],
        tableData         : getSpecimenOpts($scope.specimens),
        idColumnLabel     : $translate.instant('specimens.title'),
        mode              : 'add',
        allowRowSelection : false,
        onValidationError : onValidationError
      };

      $scope.eventOpts = opts;
    }

    $scope.saveEvent = function() {
      var tableCtrl = $scope.eventTableCtrl.ctrl;
      var data = tableCtrl.getData();
      if (!data) {
        return;
      }

      SpecimenEvent.save($scope.selectedEvent.formId, data).then(
        function(savedData) {
          $scope.back();
          Alerts.success("specimens.bulk_events.events_saved");
        }
      );
    }

    $scope.copyFirstToAll = function () {
      $scope.eventTableCtrl.ctrl.copyFirstToAll();
    }
    
    init();
  });
