package uno.views

import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.layout.Pane
import scalafx.scene.paint.Color
import uno.controller.GameController
import uno.patterns.state.{State, WelcomeState}
import uno.util.Event.*
import uno.util.{Event, Observer}


class GUI(controller: GameController) extends JFXApp3 with Observer:
  controller.add(this)

  private var state: State = new WelcomeState(this, controller)
  private val pane: Pane = new Pane

  def setState(newState: State): Unit = {
    state = newState
  }

  def display(): Unit = {
    state.display(pane)
  }

  override def start(): Unit = {
    stage = new PrimaryStage {
      title = "Uno Game"
      scene = new Scene {
        fill = Color.Black
        content = pane
      }
    }
    display()
  }

  override def update(e: Event): Unit = {
    e match {
      case Start =>
      case Quit =>
    }
  }

object GUI {
  def launchApp(controller: GameController): Unit = {
    new GUI(controller).main(Array.empty)
  }
}