# ScalaJS undo/redo

This repository is to experiment with undo/redo approaches in ScalaJS, in particular using [Diode](https://github.com/suzaku-io/diode).
Diode is used as an app state container. To mutate this state, we send actions to thios state container.

We have several ways of implementing the undo/redo behaviour :
- Save the whole app state at each new action in a queue and look in the queue when we want to undo an action.
- Use "undoable" diode actions, that implement 2 functions : 
    - the `update` function that updates the current state and gets the next state
    - the `undo` function that is the `update` opposite behaviour.
    Then we keep a list of past actions, and when we want to undo an action, we pick the first action in the pas actions queue and run the `undo` method.
## To run locally

    sbt run
	
Visit http://localhost:8080

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
- [x] Add akka-http server
- [ ] Move the python server to akka-http
