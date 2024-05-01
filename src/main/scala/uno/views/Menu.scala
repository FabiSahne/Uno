package uno.views

import uno.models._
import scala.io.AnsiColor._

class Menu {
  def displayMainMenu(): Unit = {
    val boxTopBottom = s"$BLUE" + "=" * 40 + s"$RESET"
    val menuItems = List("1. Start a new game", "2. View the rules", "3. Exit")
    val menuString = menuItems.mkString("\n")

    println(boxTopBottom)
    println(s"$BLUE||" + " " * 36 + "||")
    menuString.split("\n").foreach { item =>
      val paddingSize = (36 - item.length) / 2
      val line =
        s"$BLUE||" + " " * paddingSize + s"$CYAN" + item + " " * (36 - paddingSize - item.length) + s"$BLUE||"
      println(line)
    }
    println(s"$BLUE||" + " " * 36 + s"||")
    println(boxTopBottom)
  }

  def displayGameMenu(player: Player, topCard: Card): Unit = {
    println(s"Current player: Player ${player.id}")
    println(s"Top card: $topCard")
    println("Your hand:")
    player.hand.cards.zipWithIndex.foreach { case (card, index) =>
      println(s"${index + 1}. $card")
    }
    println(
      "Enter the number of the card you want to play, or 0 to draw a card:"
    )
  }

  def handleMainMenuInput(input: Int): Int = {
    input match {
      case 1 => 1 // Start a new game
      case 2 => 2 // View the rules
      case 3 => 3 // Exit
      case _ =>
        println("Invalid input. Please enter a number between 1 and 3.")
        0 // Invalid input
    }
  }

  def handleGameMenuInput(input: Int, handSize: Int): Int = {
    if (input >= 0 && input <= handSize) {
      input
    } else {
      println(
        "Invalid input. Please enter a number between 0 and the size of your hand."
      )
      -1 // Invalid input
    }
  }
}
