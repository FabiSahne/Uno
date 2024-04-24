package uno

class TUI {
  def startGame(): Unit = {
    println("Welcome to Uno, the card game that destroys friendships!")

    // Initialize the game
    val deck = Deck.generateDeck().shuffle()
    val players = List(Player(1, 0, deck.draw(7)._1), Player(2, 0, deck.draw(7)._1))
    gameLoop(players, deck, 0)
  }

  def gameLoop(players: List[Player], deck: Deck, currentPlayerIndex: Int): Unit = {
    if (players.exists(_.hand.nonEmpty)) {
      val currentPlayer = players(currentPlayerIndex)
      println(s"Current player: Player ${currentPlayerIndex + 1}")

      currentPlayer.hand.zipWithIndex.foreach { case (card, index) =>
        val colorCode = card.color match {
          case cardColors.RED => "\u001b[31m"
          case cardColors.GREEN => "\u001b[32m"
          case cardColors.YELLOW => "\u001b[33m"
          case cardColors.BLUE => "\u001b[34m"
          case _ => "\u001b[0m" // default for wild cards
        }
        println(s"${index + 1}: $colorCode${card.value}\u001b[0m")
      }

      println("Enter the number of the card you want to play:")
      val cardNumber = scala.io.StdIn.readInt()
      val card = currentPlayer.hand(cardNumber - 1) // Subtract 1 because list indices start at 0
      if (currentPlayer.canPlay(card)) {
        val newPlayer = currentPlayer.playCard(card)
        val newPlayers = players.updated(currentPlayerIndex, newPlayer)
        println(s"Player ${currentPlayerIndex + 1} played ${card}")
        gameLoop(newPlayers, deck, (currentPlayerIndex + 1) % players.size)
      } else {
        println("You can't play that card. You lose your turn.")
        gameLoop(players, deck, (currentPlayerIndex + 1) % players.size)
      }
    } else {
      // Game over
      println("Game over!")
      val winnerIndex = players.indexWhere(_.hand.isEmpty)
      println(s"Player ${winnerIndex + 1} wins!")
    }
  }
}