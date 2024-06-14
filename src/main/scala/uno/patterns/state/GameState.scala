package uno.patterns.state

import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.Pane
import uno.controller.GControllerImp.GameController
import uno.models.playerComponent.playerImp.Player
import uno.views.GUI

class GameState(gui: GUI, controller: GameController) extends State {
  override def display(pane: Pane): Unit = {
    pane.children.clear()

    val image = new Image(getClass.getResourceAsStream("/field/field.png"))
    val fieldImageView = new ImageView(image)

    val logo = new Image(getClass.getResourceAsStream("/field/logo.png"))
    val logoImageView = new ImageView(logo) {
      layoutX = 0
      layoutY = 0
    }

    pane.children = List(fieldImageView, logoImageView)
  }
}