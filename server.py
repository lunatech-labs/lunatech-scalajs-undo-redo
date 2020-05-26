import json
from flask import Flask, request, jsonify

app = Flask(__name__)

recipes = None
with open('recipes.json') as f:
    recipes = json.load(f)
    
@app.route('/recipes', methods=['GET'])
def get_recipes():
    return jsonify(recipes)

@app.route('/recipes/<recipe>', methods=['POST'])
def post_recipe():
    recipes.append(request.get_json())
    return jsonify(recipes)

@app.route('/recipes/<recipe_id>', methods=['DELETE'])
def delete_recipe(recipe_id):
    updated_recipes = [r for r in recipes if r['name'] != recipe_id]
    return jsonify(updated_recipes)

if __name__ == "__main__":
    app.run()
