package uno.views

import scalafx.application.{JFXApp3, Platform}
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.input.{KeyCode, KeyEvent}
import scalafx.scene.layout.{Pane, StackPane}
import scalafx.scene.paint.Color
import scalafx.Includes.*
import uno.controller.GameController
import uno.patterns.state.{GameState, State, WelcomeState}
import uno.util.Event.*
import uno.util.{Event, Observer}

class GUI(controller: GameController) extends JFXApp3 with Observer:
  controller.add(this)

  private var state: State = new WelcomeState(this, controller)
  private val menuPane: Pane = new Pane
  private val gamePane: Pane = new Pane
  private val rootPane: StackPane = new StackPane {
    children = List(menuPane, gamePane)
  }

  def setState(newState: State): Unit = {
    state = newState
  }

  def display(): Unit = {
    state.display(gamePane)
    gamePane.toFront()
  }

  override def start(): Unit = {
    stage = new PrimaryStage {
      title = "Uno Game"
      scene = new Scene {
        fill = Color.Black
        content = rootPane
      }
    }
    stage.scene().onKeyPressed = handleKeyPress
    display()
  }

  private def handleKeyPress: KeyEvent => Unit = {
    case ke: KeyEvent if ke.code == KeyCode.Left =>
      state = new WelcomeState(this, controller)
      display()
    case _ =>
  }

  override def update(e: Event): Unit = {
    println(s"GUI Received event: $e")
    e match {
      case Start =>
        Platform.runLater(() => {
          state = new GameState(this, controller)
          display()
        })
      case Play =>
        Platform.runLater(() => {
          state = new GameState(this, controller)
          display()
        })
      case Quit =>
      // close application
    }
  }

object GUI {
  def launchApp(controller: GameController): Unit = {
    new GUI(controller).main(Array.empty)
  }
}