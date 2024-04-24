package uno

import scala.io.StdIn

class TUI {
  val menu = new Menu()

  def startGame(): Unit = {
    clearScreen()
    println("Welcome to Uno, the card game that destroys friendships!")
    menu.displayMainMenu()
    println("Please enter a number from the menu above:")
    
    val input = try {
      StdIn.readInt()
    } catch {
      case _: NumberFormatException =>
        println("Invalid input. Please enter a number.")
        startGame()
        return
    }

    menu.handleMainMenuInput(input) match {
      case 1 => 
        val deck = Deck.generateDeck().shuffle()
        val players = List(Player(1, 0, deck.draw(7)._1), Player(2, 0, deck.draw(7)._1))
        gameLoop(players, deck, 0)
      case 2 => 
        startGame() 
      case 3 => 
        println("Goodbye!")
      case _ => 
        println("Invalid input. Please enter a number between 1 and 3.")
        startGame()
    }
  }

    def gameLoop(players: List[Player], deck: Deck, currentPlayerIndex: Int): Unit = {
      if (players.exists(_.hand.nonEmpty)) {
        clearScreen()
        val currentPlayer = players(currentPlayerIndex)
        println(s"Current player: Player ${currentPlayerIndex + 1}")

        val topCard = deck.allCards.head
        val topCardColorCode = getColorCode(topCard)
        println(s"Current top card: $topCardColorCode${topCard.value}\u001b[0m")

        currentPlayer.hand.zipWithIndex.foreach { case (card, index) =>
          val colorCode = getColorCode(card)
          println(s"${index + 1}: $colorCode${card.value}\u001b[0m")
        }

        println("Enter the number of the card you want to play:")
        val cardNumber = try {
          StdIn.readInt()
        } catch {
          case _: NumberFormatException =>
            println("Invalid input. Please enter a number.")
            gameLoop(players, deck, currentPlayerIndex)
            return
        }

        if (cardNumber < 1 || cardNumber > currentPlayer.hand.length) {
          println("Invalid card number. Please try again.")
          gameLoop(players, deck, currentPlayerIndex)
        } else {
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
        }
      } else {
        // Game over
        clearScreen()
        println("Game over!")
        val winnerIndex = players.indexWhere(_.hand.isEmpty)
        println(s"Player ${winnerIndex + 1} wins!")
      }
    }

    def getColorCode(card: Card): String = {
      card.color match {
        case cardColors.RED => "\u001b[31m"
        case cardColors.GREEN => "\u001b[32m"
        case cardColors.YELLOW => "\u001b[33m"
        case cardColors.BLUE => "\u001b[34m"
        case _ => "\u001b[0m" // default for wild cards
      }
    }

    def clearScreen(): Unit = {
      print("\u001b[H\u001b[2J")
    }
  }