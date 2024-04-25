Feature: endpoint de houses
	Vamos a probar todos los endpoints de houses
	
	Scenario: Dar de alta una casa
		Given Una casa de owner "David" y fullypaid igual a "true" con 1 item de nombre "item1"
		When Cuando llamo al endpoint "houses" y verbo "post"
		Then Obtengo como respuesta el código "201"