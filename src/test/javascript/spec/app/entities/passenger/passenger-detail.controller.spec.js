'use strict';

describe('Controller Tests', function() {

    describe('Passenger Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPassenger;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPassenger = jasmine.createSpy('MockPassenger');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Passenger': MockPassenger
            };
            createController = function() {
                $injector.get('$controller')("PassengerDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'carpoolingCsid2016App:passengerUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
