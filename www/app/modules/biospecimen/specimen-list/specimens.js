angular.module('os.biospecimen.specimenlist')
  .controller('SpecimenListSpecimensCtrl', function(
    $scope, $state, $timeout, currentUser, reqBasedDistOrShip, list,
    SpecimensHolder, SpecimenList, DeleteUtil, Alerts) {

    function init() { 
      $scope.orderCreateOpts =    {resource: 'Order', operations: ['Create']};
      $scope.shipmentCreateOpts = {resource: 'ShippingAndTracking', operations: ['Create']};
      $scope.specimenUpdateOpts = {resource: 'VisitAndSpecimen', operations: ['Update']};

      $scope.ctx = {
        list: list,
        filterOpts: {},
        filterPvs: {init: false},
        selection: {},
        reqBasedDistOrShip: (reqBasedDistOrShip.value == 'true'),
        url: SpecimenList.url()
      }

      $scope.$on('osRightDrawerOpen', initFilterPvs);
      resetSelection();
    }

    function initFilterPvs() {
      if ($scope.ctx.filterPvs.init) {
        return;
      }

      var types = [], sites = [], cps = [], lineages = [];
      angular.forEach(list.specimens,
        function(specimen) {
          if (types.indexOf(specimen.type) == -1) {
            types.push(specimen.type);
          }

          if (sites.indexOf(specimen.anatomicSite) == -1) {
            sites.push(specimen.anatomicSite);
          }

          if (cps.indexOf(specimen.cpShortTitle) == -1) {
            cps.push(specimen.cpShortTitle);
          }

          if (lineages.indexOf(specimen.lineage) == -1) {
            lineages.push(specimen.lineage);
          }
        }
      );

      var filterPvs = {
        init: true,
        types: types.sort(),
        sites: sites.sort(),
        cps: cps.sort(),
        lineages: lineages.sort()
      }

      $timeout(function() {$scope.ctx.filterPvs = filterPvs});
    }

    function resetSelection() {
      return $scope.ctx.selection = {all: false, any: false, specimens: []};
    }

    function removeSpecimensFromList() {
      var list = $scope.ctx.list;
      list.removeSpecimens($scope.ctx.selection.specimens).then(
        function(listSpecimens) {
          list.specimens = listSpecimens.specimens;
          $scope.ctx.selection = resetSelection();

          var type = list.getListType(currentUser);
          Alerts.success('specimen_list.specimens_removed_from_' + type, list);
        }
      );
    }

    function showSelectSpecimensErrMsg(msgCode) {
      Alerts.error(msgCode);
    };

    $scope.viewSpecimen = function(specimen) {
      $state.go('specimen', {specimenId: specimen.id});
    }

    $scope.toggleAllSpecimenSelect = function() {
      $scope.ctx.selection.any = $scope.ctx.selection.all;
      if (!$scope.ctx.selection.all) {
        $scope.ctx.selection.specimens = [];
      } else {
        $scope.ctx.selection.specimens = [].concat($scope.ctx.list.specimens);
      }

      angular.forEach($scope.ctx.list.specimens, function(specimen) {
        specimen.selected = $scope.ctx.selection.all;
      });
    }

    $scope.toggleSpecimenSelect = function (specimen) {
      var specimens = $scope.ctx.selection.specimens;

      if (specimen.selected) {
        specimens.push(specimen);
      } else {
        var idx = specimens.indexOf(specimen);
        if (idx != -1) {
          specimens.splice(idx, 1);
        }
      }

      $scope.ctx.selection.all = (specimens.length == list.specimens.length);
      $scope.ctx.selection.any = (specimens.length > 0);
    };

    $scope.confirmRemoveSpecimens = function () {
      if (!$scope.ctx.selection.any) {
        showSelectSpecimensErrMsg("specimen_list.no_specimens_for_deletion");
        return;
      }

      var listType = list.getListType(currentUser);
      DeleteUtil.confirmDelete({
        entity: list,
        props: {messageKey: 'specimen_list.confirm_remove_specimens_from_' + listType},
        templateUrl: 'modules/biospecimen/specimen-list/confirm-remove-specimens.html',
        delete: removeSpecimensFromList
      });
    }

    $scope.distributeSpecimens = function() {
      if (!$scope.ctx.selection.any) {
        showSelectSpecimensErrMsg("specimen_list.no_specimens_for_distribution");
        return;
      }

      SpecimensHolder.setSpecimens($scope.ctx.selection.specimens);
      $state.go('order-addedit', {orderId: ''});
    }

    $scope.shipSpecimens = function() {
      if (!$scope.ctx.selection.any) {
        showSelectSpecimensErrMsg("specimen_list.no_specimens_for_shipment");
        return;
      }

      SpecimensHolder.setSpecimens($scope.ctx.selection.specimens);
      $state.go('shipment-addedit', {orderId: ''});
    }
    
    $scope.addEvent = function() {
      if (!$scope.ctx.selection.any) {
        showSelectSpecimensErrMsg('specimen_list.no_specimens_to_add_event');
        return;
      }
      
      SpecimensHolder.setSpecimens($scope.ctx.selection.specimens);
      $state.go('bulk-add-event');
    }

    $scope.clearFilters = function() {
      $scope.ctx.filterOpts = {};
    }

    init();
  });
