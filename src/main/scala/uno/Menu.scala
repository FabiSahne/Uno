package uno

class Menu {
  def displayMainMenu(): Unit = {
    val boxTopBottom = "\u001b[34m" + "=" * 40 + "\u001b[0m"
    val menuItems = List("1. Start a new game", "2. View the rules", "3. Exit")
    val menuString = menuItems.mkString("\n")

    println(boxTopBottom)
    println("\u001b[34m||" + " " * 36 + "||\u001b[0m")
    menuString.split("\n").foreach { item =>
      val paddingSize = (36 - item.length) / 2
      val line = "\u001b[34m||\u001b[0m" + " " * paddingSize + "\u001b[36m" + item + "\u001b[0m" + " " * (36 - paddingSize - item.length) + "\u001b[34m||\u001b[0m"
      println(line)
    }
    println("\u001b[34m||" + " " * 36 + "||\u001b[0m")
    println(boxTopBottom)
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