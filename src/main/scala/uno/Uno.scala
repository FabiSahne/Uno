package uno

import uno.controller.GameController
import uno.models.Round
import uno.views.{GUI, TUI}
import java.io.{FileOutputStream, PrintStream}
import javafx.application.Platform

object Uno {
  def main(args: Array[String]): Unit = {
    val errStream = new PrintStream(new FileOutputStream("error.log"))
    System.setErr(errStream)
    val controller = GameController(Round())
    new GUIThread(controller).start()
    new TUIThread(controller).start()
  }

  private class GUIThread(gameController: GameController) extends Thread {
    override def run(): Unit = {
      GUI(gameController).main(Array())
    }
  }

  private class TUIThread(gameController: GameController) extends Thread {
    override def run(): Unit = {
      val tui = TUI(gameController)
      tui.startGame()
    }
  }
}
