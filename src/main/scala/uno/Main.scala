package uno

import uno.controller.GameController
import uno.models.Round
import uno.views.TUI

<<<<<<< HEAD
@main def main(): Unit = {
  val round = Round()
  val controller = new GameController(round)
  val tui = new TUI(controller)
  tui.startGame()
}
=======
  // Initialize the game
  val deck = Deck()
  val (remainingDeck1, initialHand1) = deck.draw(7)
  val (remainingDeck2, initialHand2) = remainingDeck1.draw(7)
  var players = List(Player(initialHand1), Player(initialHand2))
  var currentPlayerIndex = 0

  // Game loop
  while (players.exists(_.hand.nonEmpty)) {
    var currentPlayer = players(currentPlayerIndex)
    println(s"Current player: Player ${currentPlayerIndex + 1}")
    
    currentPlayer.hand.zipWithIndex.foreach { case (card, index) =>
      val colorCode = card match {
        case Card(_, cardValues.WILD) => "\u001b[0m"
        case Card(_, cardValues.WILD_DRAW_FOUR) => "\u001b[0m"
        case Card(cardColors.RED, _) => "\u001b[31m"
        case Card(cardColors.GREEN, _) => "\u001b[32m"
        case Card(cardColors.YELLOW, _) => "\u001b[33m"
        case Card(cardColors.BLUE, _) => "\u001b[34m"
      }
      println(s"${index + 1}: $colorCode${card.value}\u001b[0m")
    }

    println("Enter the number of the card you want to play:")
    val cardNumber = scala.io.StdIn.readInt()
    val card = currentPlayer.hand(cardNumber - 1) // Subtract 1 because list indices start at 0
    if (currentPlayer.canPlay(card)) {
      currentPlayer = currentPlayer.playCard(card).get
      players = players.updated(currentPlayerIndex, currentPlayer)
      println(s"Player ${currentPlayerIndex + 1} played ${card}")
    } else {
      println("You can't play that card. You lose your turn.")
    }

    // Move to next player
    currentPlayerIndex = (currentPlayerIndex + 1) % players.size
  }

  // Game over
  println("Game over!")
  val winnerIndex = players.indexWhere(_.hand.isEmpty)
  println(s"Player ${winnerIndex + 1} wins!")
>>>>>>> tests
