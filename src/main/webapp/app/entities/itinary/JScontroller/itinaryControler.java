@composant
Class SearchService{
@inject Locationrepository
Search(DateDepart) {
Location start = locationRepository.findbyNameLike(SearchLocation);
List<path> res = pathRepository.findByPath (start)
Return res;}
}
