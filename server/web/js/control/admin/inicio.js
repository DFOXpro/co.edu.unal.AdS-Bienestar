/* global app */

app.controller('inicio', function ($rootScope, $scope, $sesion, $tabla) {
	console.log("inicio", $rootScope.nav([
		{url:"/inicio",nombre:"Inicio"}
	]));

	$scope.pagina = {
		titulo: "Administrador: ",
		subtitulo: window.atob(localStorage.getItem("6")),
		convocatoriasRecientes:[],
		talleresRecientes:[],
		usuariosRecientes:[]
	};
	$tabla.get("admin", "Convocatorias recientes", 0, 4, "/evento/convocatorias",function (r){$scope.pagina.convocatoriasRecientes = r;});
	$tabla.get("admin", "Talleres recientes", 0, 4, "/evento/talleres",function (r){$scope.pagina.talleresRecientes = r;});
	$tabla.get("admin", "Usuarios recientes", 0, 4, "usuarios",function (r){$scope.pagina.usuariosRecientes = r;});
});
console.log("Admin inicio cargado");