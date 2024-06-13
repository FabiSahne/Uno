package uno.patterns.state

import scalafx.scene.layout.Pane
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.paint.Color
import scalafx.scene.text.{Font, Text, TextFlow}
import scalafx.Includes.*
import uno.views.GUI
import uno.controller.GameController

trait State {
  def display(pane: Pane): Unit

  protected def createImageView(imagePath: String): ImageView = {
    val image = new Image(getClass.getResourceAsStream(imagePath))
    new ImageView(image)
  }
}

class WelcomeState(gui: GUI, controller: GameController) extends State {

  private def displayCredits(): Unit = {
    gui.setState(new CreditsState(gui, controller))
    gui.display()
  }

  override def display(pane: Pane): Unit = {
    pane.children.clear()

    val menuImageView = createImageView("/field/menu.png")

    val welcomeText: Text = new Text("Welcome to Uno!") {
      font = Font.loadFont(getClass.getResourceAsStream("/font/undefined-medium.ttf"), 24).delegate
      x = 220
      y = 250
    }

    val menuOptions: TextFlow = new TextFlow {
      layoutX = 170
      layoutY = 280
      
      children = List(
        new Text("> Start a new game\n") {
          font = Font(welcomeText.font.value.getName, 20)
          fill = Color.Pink
          onMouseEntered = _ => fill = Color.Bisque
          onMouseExited = _ => fill = Color.Pink
          onMouseClicked = _ => controller.initGame()
        },
        
        new Text("> Exit\n") {
          font = Font(welcomeText.font.value.getName, 20)
          fill = Color.Pink
          onMouseEntered = _ => fill = Color.Bisque
          onMouseExited = _ => fill = Color.Pink
          onMouseClicked = _ => controller.quitGame()
        },
        
        new Text("> Credits\n") {
          font = Font(welcomeText.font.value.getName, 20)
          fill = Color.Pink
          onMouseEntered = _ => fill = Color.Bisque
          onMouseExited = _ => fill = Color.Pink
          onMouseClicked = _ => displayCredits()
        }
      )
    }

    pane.children = List(menuImageView, welcomeText, menuOptions)
  }
}

class CreditsState(gui: GUI, controller: GameController) extends State {
  override def display(pane: Pane): Unit = {
    pane.children.clear()

    val menuImageView = createImageView("/field/menu.png")

    val creditsText: TextFlow = new TextFlow {
      children = List(
        new Text("Credits:\n") {
          font = Font.loadFont(getClass.getResourceAsStream("/font/undefined-medium.ttf"), 20)
          fill = Color.SandyBrown
        },
        new Text("Developed by Selin K and Fabian W\n") {
          font = Font.loadFont(getClass.getResourceAsStream("/font/undefined-medium.ttf"), 20)
          fill = Color.AntiqueWhite
        },
        new Text("\n") {
        },
        new Text("Special Thanks to:\n") {
          font = Font.loadFont(getClass.getResourceAsStream("/font/undefined-medium.ttf"), 20)
          fill = Color.SandyBrown
        },
        new Text("ChatGPT and Github Copilot\n") {
          font = Font.loadFont(getClass.getResourceAsStream("/font/undefined-medium.ttf"), 20)
          fill = Color.AntiqueWhite
        },
      )
      layoutX = 100
      layoutY = 220
    }

    pane.children = List(menuImageView, creditsText)
  }
}