'use strict';

describe('Controller Tests', function() {

    describe('Car Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCar, MockDriver, MockCarBrand, MockCarType;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCar = jasmine.createSpy('MockCar');
            MockDriver = jasmine.createSpy('MockDriver');
            MockCarBrand = jasmine.createSpy('MockCarBrand');
            MockCarType = jasmine.createSpy('MockCarType');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Car': MockCar,
                'Driver': MockDriver,
                'CarBrand': MockCarBrand,
                'CarType': MockCarType
            };
            createController = function() {
                $injector.get('$controller')("CarDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'carpoolingCsid2016App:carUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
