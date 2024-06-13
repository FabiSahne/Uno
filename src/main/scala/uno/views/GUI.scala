package uno.views

import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.scene.Scene
import scalafx.Includes.*
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.Pane
import scalafx.scene.paint.Color
import scalafx.scene.text.{Font, Text, TextFlow}
import uno.controller.GameController
import uno.util.Event.*
import uno.util.{Event, Observer}


class GUI(controller: GameController) extends JFXApp3 with Observer:
  controller.add(this)

  val pane = new Pane {
    val menuImage = new Image(getClass.getResourceAsStream("/field/menu.png"))
    val menuImageView = new ImageView(menuImage)

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

    children = List(menuImageView, welcomeText, menuOptions)
  }

  override def start(): Unit =
    stage = new PrimaryStage {
      title = "Uno"
      scene = new Scene {
        content = pane
      }
    }

  private def displayCredits(): Unit = {
    pane.children.clear()
    val menuImage = new Image(getClass.getResourceAsStream("/field/menu.png"))
    val menuImageView = new ImageView(menuImage)

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

    val cardImage = new Image(getClass.getResourceAsStream("/path/to/your/card.png")) // replace with your card image path
    val returnCard: ImageView = new ImageView(cardImage) {
      x = 10 // adjust this value to position the card at the bottom left
      y = 550 // adjust this value to position the card at the bottom left
      onMouseEntered = _ => opacity = 0.7
      onMouseExited = _ => opacity = 1.0
      onMouseClicked = _ => {
        pane.children.clear()
        pane.children = List(menuImageView, welcomeText, menuOptions)
      }
    }

    pane.children = List(menuImageView, creditsText)
  }

  override def update(event: Event): Unit = event match {
    case Start => // handle start event
    case Quit => // handle quit event
    case Play => // handle play event
    case Draw => // handle draw event
    case _ => // handle other events
  }

object GUI {
  def launchApp(controller: GameController): Unit = {
    new GUI(controller).main(Array.empty)
  }
}