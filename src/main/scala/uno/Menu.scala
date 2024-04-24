package uno

class Menu {
  def displayMainMenu(): Unit = {
    println("1. Start a new game")
    println("2. View the rules")
    println("3. Exit")
  }

  def displayGameMenu(player: Player, topCard: Card): Unit = {
    println(s"Current player: Player ${player.id}")
    println(s"Top card: ${topCard}")
    println("Your hand:")
    player.hand.zipWithIndex.foreach { case (card, index) =>
      println(s"${index + 1}. ${card}")
    }
    println("Enter the number of the card you want to play, or 0 to draw a card:")
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
      println("Invalid input. Please enter a number between 0 and the size of your hand.")
      -1 // Invalid input
    }
  }
}