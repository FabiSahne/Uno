package uno

@main def hello(): Unit =
  println("Welcome to Uno, the card game that destroys friendships!")

  // Initialize the game
  val deck = new Deck()
  deck.shuffle()
  var players = List(Player(deck.draw(7)), Player(deck.draw(7)))
  var currentPlayerIndex = 0

  // Game loop
  while (players.exists(_.hand.nonEmpty)) {
    var currentPlayer = players(currentPlayerIndex)
    println(s"Current player: Player ${currentPlayerIndex + 1}")
    
    currentPlayer.hand.zipWithIndex.foreach { case (card, index) =>
      val colorCode = card.color match {
        case cardColors.RED => "\u001b[31m"
        case cardColors.GREEN => "\u001b[32m"
        case cardColors.YELLOW => "\u001b[33m"
        case cardColors.BLUE => "\u001b[34m"
        //case _ => "\u001b[0m" // default for wild cards
      }
      println(s"${index + 1}: $colorCode${card.value}\u001b[0m")
    }

    println("Enter the number of the card you want to play:")
    val cardNumber = scala.io.StdIn.readInt()
    val card = currentPlayer.hand(cardNumber - 1) // Subtract 1 because list indices start at 0
    if (currentPlayer.canPlay(card)) {
      currentPlayer = currentPlayer.playCard(card).get // `get` unwraps the Player from the Option[Player]
      players = players.updated(currentPlayerIndex, currentPlayer)
      println(s"Player ${currentPlayerIndex + 1} played $card")
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