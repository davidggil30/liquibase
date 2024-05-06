Feature: endpoint de houses
	Vamos a probar todos los endpoints de houses
	
	Scenario: Dar de alta una casa
		Given Una casa de owner "David" y fullypaid igual a "true" con 1 item de name "item1"
		When Cuando llamo al endpoint "/houses" y verbo "get"
		Then Obtengo como respuesta el código 200
		
	Scenario: Dar de alta 2 casas
		Given Una casa de owner "David" y fullypaid igual a "true" con 1 item de name "item1"
		And Una casa de owner "Ricardo" y fullypaid igual a "true" con 1 item de name "item2"
		When Cuando llamo al endpoint "/houses" y verbo "get" y owner "David"
		Then Obtengo como respuesta la casa con 1 item de name "item1"
		And El número de casas es 2
	