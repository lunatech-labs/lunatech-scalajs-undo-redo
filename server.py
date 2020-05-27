import json
from flask import Flask, request, jsonify, make_response

app = Flask(__name__)

recipes = None
with open('recipes.json') as f:
    recipes = json.load(f)

@app.route('/recipes', methods=['GET', 'OPTIONS'])
def get_recipes():
    response = jsonify(recipes)
    response.headers.add('Access-Control-Allow-Origin', '*')
    response.headers.add('Access-Control-Allow-Methods', '*')
    response.headers.add('Access-Control-Allow-Headers', '*')
    return response

@app.route('/recipes', methods=['POST'])
def post_recipe():
    recipes.append(request.get_json())
    response = jsonify(recipes)
    response.headers.add('Access-Control-Allow-Origin', '*')
    response.headers.add('Access-Control-Allow-Methods', '*')
    response.headers.add('Access-Control-Allow-Headers', '*')
    return response

@app.route('/recipes/<recipe_id>', methods=['DELETE', 'OPTIONS'])
def delete_recipe(recipe_id):
    updated_recipes = [r for r in recipes if r['id'] != recipe_id]
    for r in recipes:
        toKeep = False
        for ur in updated_recipes:
            if r['id'] == ur['id']:
                toKeep = True
        if not toKeep:
            recipes.remove(r)
    response = jsonify(updated_recipes)
    response.headers.add('Access-Control-Allow-Origin', '*')
    response.headers.add('Access-Control-Allow-Methods', '*')
    response.headers.add('Access-Control-Allow-Headers', '*')
    return response

if __name__ == "__main__":
    app.run()
