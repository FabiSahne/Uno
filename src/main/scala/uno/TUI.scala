package uno

import scala.io.StdIn

class TUI {
  private val menu = new Menu()

  def startGame(): Unit = {
    clearScreen()
    println("Welcome to Uno, the card game that destroys friendships!")
    menu.displayMainMenu()
    println("Please enter a number from the menu above:")

    val selection = StdIn.readLine().toIntOption

    selection match {
      case Some(1) => 
        val deck = Deck.generateDeck().shuffle()
        val (hand1, deck1) = deck.draw(7)
        val (hand2, deck2) = deck1.draw(7)
        val players = List(Player(1, 0, hand1), Player(2, 0, hand2))
        val (topCard, playDeck) = deck2.draw(1)
        gameLoop(players, deck2, topCard.head, 0)
      case Some(2) => 
        startGame() 
      case Some(3) => 
        println("Goodbye!")
      case _ => 
        println("Invalid input. Please enter a number between 1 and 3.")
        startGame()
    }
  }

    private def gameLoop(players: List[Player], deck: Deck, topCard: Card, currentPlayerIndex: Int): Unit = {
      if (players.exists(_.hand.nonEmpty)) {
        clearScreen()
        val currentPlayer = players(currentPlayerIndex)
        println(s"Current player: Player ${currentPlayerIndex + 1}")

        val topCardColorCode = getColorCode(topCard)
        println(s"Current top card: $topCardColorCode${topCard.value}\u001b[0m")

        currentPlayer.hand.zipWithIndex.foreach { case (card, index) =>
          val colorCode = getColorCode(card)
          println(s"${index + 1}: $colorCode${card.value}\u001b[0m")
        }

        println("Enter the number of the card you want to play:")
        val cardNumber = StdIn.readLine().toIntOption
        if (cardNumber.isEmpty) {
          println("Invalid input. Please enter a number.")
          gameLoop(players, deck, topCard, currentPlayerIndex)
        }

        if (cardNumber.get < 1 || cardNumber.get > currentPlayer.hand.length) {
          println("Invalid card number. Please try again.")
          gameLoop(players, deck, topCard, currentPlayerIndex)
        } else {
          val card = currentPlayer.hand(cardNumber.get - 1) // Subtract 1 because list indices start at 0
          if (currentPlayer.canPlay(card) && card.canBePlayedOn(topCard)) {
            val newPlayer = currentPlayer.playCard(card).get
            val newPlayers = players.updated(currentPlayerIndex, newPlayer)
            //println(s"Player ${currentPlayerIndex + 1} played ${card}")
            gameLoop(newPlayers, deck, card, (currentPlayerIndex + 1) % players.size)
          } else {
            println("You can't play that card. Do you want to draw, or try again?")
            println("1: Draw a card")
            println("2: Try again")
            
            val selection = StdIn.readLine().toIntOption.getOrElse(-1)
            if (selection == -1) {
              println("Invalid input. Please enter a number.")
              gameLoop(players, deck, topCard, currentPlayerIndex)
            }else if (selection == 1) {
              val (newCard, newDeck) = deck.draw(1)
              val newPlayer = Player(currentPlayer.id, currentPlayer.score, currentPlayer.hand :++ newCard)
              val newPlayers = players.updated(currentPlayerIndex, newPlayer)
              gameLoop(newPlayers, newDeck, topCard, currentPlayerIndex)
            } else if (selection == 2) {
              gameLoop(players, deck, topCard, currentPlayerIndex)
            } else {
              println("Invalid input. Please enter 1 or 2. Try again.")
              gameLoop(players, deck, topCard, currentPlayerIndex)
            }
          }
        }
      } else {
        // Game over
        clearScreen()
        println("Game over!")
        val winnerIndex = players.indexWhere(_.hand.isEmpty)
        println(s"Player ${winnerIndex + 1} wins!")
      }
    }

    private def getColorCode(card: Card): String = {
      card match {
        case Card(_, cardValues.WILD) => "\u001b[0m"
        case Card(_, cardValues.WILD_DRAW_FOUR) => "\u001b[0m"
        case Card(cardColors.RED, _) => "\u001b[31m"
        case Card(cardColors.GREEN, _) => "\u001b[32m"
        case Card(cardColors.YELLOW, _) => "\u001b[33m"
        case Card(cardColors.BLUE, _) => "\u001b[34m"
      }
    }

    private def clearScreen(): Unit = {
      print("\u001b[H\u001b[2J")
    }
  }