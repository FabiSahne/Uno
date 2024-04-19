package uno

@main def hello(): Unit =
  println("Welcome to Uno, the card game that destroys friendships!")

  // Initialize the game
  val deck = Deck()
  val shuffeledDeck = deck.shuffle()
  val (remainingDeck1, initialHand1) = shuffeledDeck.draw(7)
  val (remainingDeck2, initialHand2) = remainingDeck1.draw(7)
  var players = List(Player(0, initialHand1), Player(0, initialHand2))
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
      currentPlayer = currentPlayer.playCard(card)
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