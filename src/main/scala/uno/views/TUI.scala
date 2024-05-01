package uno.views

import uno.models.*
import uno.util.{Event, Observer}
import uno.controller.GameController
import uno.util.Event.Quit

import scala.io.StdIn
import scala.io.AnsiColor._

class TUI(val controller: GameController) extends Observer {
  controller.add(this)
  private var continue = true

  override def update(e: Event): Unit =
    e match {
      case Quit => continue = false
      case _    => ()
    }

  private val menu = new Menu()

  def startGame(): Unit = {
    clearScreen()
    println("Welcome to Uno, the card game that destroys friendships!")
    menu.displayMainMenu()
    println("Please enter a number from the menu above:")

    val selection = StdIn.readLine().toIntOption

    selection match {
      case Some(1) =>
        val players = (0 until 2).map(i => Player(i, Hand())).toList
        gameLoop(players, randomCard, 0)
      case Some(2) =>
        startGame()
      case Some(3) =>
        println("Goodbye!")
      case _ =>
        println("Invalid input. Please enter a number between 1 and 3.")
        startGame()
    }
  }

  private def gameLoop(
      players: List[Player],
      topCard: Card,
      currentPlayerIndex: Int
  ): Unit = {
    if (players.exists(_.hand.cards.nonEmpty) && continue) {
      clearScreen()
      val currentPlayer = players(currentPlayerIndex)
      println(s"Current player: Player ${currentPlayerIndex + 1}")

      println(
        s"Current top card: ${topCard.getColorCode}${topCard.value}$RESET"
      )

      currentPlayer.hand.cards.zipWithIndex.foreach { case (card, index) =>
        println(s"${index + 1}: ${card.getColorCode}${card.value}$RESET")
      }

      println("Enter the number of the card you want to play:")
      val cardNumber = StdIn.readLine().toIntOption
      if (cardNumber.isEmpty) {
        println("Invalid input. Please enter a number.")
        gameLoop(players, topCard, currentPlayerIndex)
      }

      if (
        cardNumber.get < 1 || cardNumber.get > currentPlayer.hand.cards.length
      ) {
        println("Invalid card number. Please try again.")
        gameLoop(players, topCard, currentPlayerIndex)
      } else {
        val card = currentPlayer.hand.cards(
          cardNumber.get - 1
        ) // Subtract 1 because list indices start at 0
        if (currentPlayer.canPlay(card) && card.canBePlayedOn(topCard)) {
          val newPlayer = currentPlayer.playCard(card).get
          val newPlayers = players.updated(currentPlayerIndex, newPlayer)
          // println(s"Player ${currentPlayerIndex + 1} played ${card}")
          if (newPlayer.hand.cards.isEmpty) {
            continue = false
            controller.quitGame()
          }
          gameLoop(
            newPlayers,
            card,
            (currentPlayerIndex + 1) % players.size
          )
        } else {
          println(
            "You can't play that card. Do you want to draw, or try again?"
          )
          println("1: Draw a card")
          println("2: Try again")

          val selection = StdIn.readLine().toIntOption.getOrElse(-1)
          if (selection == -1) {
            println("Invalid input. Please enter a number.")
            gameLoop(players, topCard, currentPlayerIndex)
          } else if (selection == 1) {
            val newCard = randomCard
            val newPlayer = Player(
              currentPlayer.id,
              currentPlayer.hand.addCard(newCard)
            )
            val newPlayers = players.updated(currentPlayerIndex, newPlayer)
            gameLoop(newPlayers, topCard, currentPlayerIndex)
          } else if (selection == 2) {
            controller.drawCard()
            gameLoop(players, topCard, currentPlayerIndex)
          } else {
            println("Invalid input. Please enter 1 or 2. Try again.")
            gameLoop(players, topCard, currentPlayerIndex)
          }
        }
      }
    } else {
      // Game over
      clearScreen()
      println("Game over!")
      val winnerIndex = players.indexWhere(_.hand.cards.isEmpty)
      println(s"Player ${winnerIndex + 1} wins!")
    }
  }

  private def clearScreen(): Unit = {
    print("\u001b[H\u001b[2J")
  }
}
