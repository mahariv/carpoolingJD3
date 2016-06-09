


Angular.controler(‘DateDepart’,function(){
Var self this ;
Self.startlocation :’DateDepart’ ;
Self.onSearch= function (){
$log.info(‘searchItinary’ + self.location);
}
Angular.service(‘searchService’, function() {
Search =$ressource.ressource(‘/search’,{get})
Search.get();
}
}) ;
