package com.lunatech.undoredo.recipes.models

import io.circe._
import io.circe.generic.semiauto._

case class Recipe(id: String, name: String, ingredients: Seq[String], instructions: Seq[String])

object Recipe {
  implicit val recipeCodec: Codec[Recipe] = deriveCodec[Recipe]
}
