package uno

@main def hello(): Unit =
  // Create a deck of cards
  val deck = new Deck()
  deck.shuffle()

  // Create players with initial hands
  val player1 = Player(0, deck.draw(7))
  val player2 = Player(0, deck.draw(7))

  // Create a list of players
  val players = List(player1, player2)

  // Print the initial state of the game
  println(s"Player 1's hand: ${player1.hand}")
  println(s"Player 2's hand: ${player2.hand}")
  println(s"Remaining cards in deck: ${deck.allCards.size}")