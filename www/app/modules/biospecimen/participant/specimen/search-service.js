angular.module('os.biospecimen.specimen.search', ['os.biospecimen.models'])
  .factory('SpecimenSearchSvc', function($state, Specimen, Alerts) {
    var matchingSpecimen = [];
    var searchKey = undefined;

    function search(searchData) {
      Specimen.listByLabels(searchData.label).then(
        function(specimens) {
          if (specimens == undefined || specimens.length == 0) {
            Alerts.error('search.error', {entity: 'Specimen', key: searchData.label});
            return;
          } else if (specimens.length > 1) {
            matchingSpecimen = specimens;
            searchKey = searchData.label;
            $state.go('specimen-search', {}, {reload: true});
          } else {
            var specimen = specimens[0];
            var params = {
              cpId: specimen.cpId,
              visitId: specimen.visitId,
              cprId: specimen.cprId,
              specimenId: specimen.id
            };
            $state.go('specimen-detail.overview', params);
          }
        }
      );
    }

    function getSpecimens() {
      return matchingSpecimen;
    }

    function getSearchKey() {
      return searchKey;
    }

    return {
      getSpecimens: getSpecimens,

      getSearchKey: getSearchKey,

      search: search
    };
  })
  .controller('SpecimenResultsView', function($scope, $state, specimens, searchKey) {
    function init() {
      $scope.specimens = specimens;
      $scope.searchKey = searchKey;
    }

    $scope.showSpecimenDetail = function(specimen) {
      var params = {
        cpId: specimen.cpId,
        visitId: specimen.visitId,
        cprId: specimen.cprId,
        specimenId: specimen.id
      };
      $state.go('specimen-detail.overview', params);
    }

    init();
  });
