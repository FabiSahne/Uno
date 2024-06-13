package uno.patterns.state

import scalafx.scene.image.{Image, ImageView}
import uno.controller.GameController
import scalafx.scene.layout.Pane
import uno.views.GUI

class GameState(gui: GUI, controller: GameController) extends State {
  override def display(pane: Pane): Unit = {
    pane.children.clear()

    val image = new Image(getClass.getResourceAsStream("/field/field.png"))
    val fieldImageView = new ImageView(image)

    pane.children = List(fieldImageView)
  }
}