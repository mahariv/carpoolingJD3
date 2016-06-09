'use strict';

describe('Controller Tests', function() {

    describe('Driver Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockDriver;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockDriver = jasmine.createSpy('MockDriver');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Driver': MockDriver
            };
            createController = function() {
                $injector.get('$controller')("DriverDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'carpoolingCsid2016App:driverUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
