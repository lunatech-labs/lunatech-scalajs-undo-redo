# ScalaJS undo/redo

This repository is to experiment with undo/redo approaches in ScalaJS, in particular using [Diode](https://github.com/suzaku-io/diode).

## To run locally

    sbt "~fastOptJS"
	
Visit http://localhost:12345

## Using the server

    python3 -m venv venv
	. venv/bin/activate
	pip install -r requirements.txt
	FLASK_ENV=development python server.py

    $ curl http://localhost:5000/recipes
	
    $ curl -X POST -H 'Content-Type: application/json' http://localhost:5000/recipes -d '{"id":"tiramisu","name":"tiramisu","ingredients":["coffee","eggs","mascarpone","savoiardi"],"instructions":["instruction 1","instruction 2"]}'

    $ curl -X DELETE http://localhost:5000/recipes/pancakes
	

## Action Items

- [ ] Add form for adding a recipe.
- [ ] Allow updating one recipe.
- [ ] Limit actions history to 5 items.
- [ ] On the backend, merge the POST and DELETE endpoints.
- [ ] Disable/enable undo redo buttons when the corresponding list is empty
