'use strict';

describe('Controller Tests', function() {

    describe('CarBrand Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCarBrand;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCarBrand = jasmine.createSpy('MockCarBrand');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'CarBrand': MockCarBrand
            };
            createController = function() {
                $injector.get('$controller')("CarBrandDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'carpoolingCsid2016App:carBrandUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
