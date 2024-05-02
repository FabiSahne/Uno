package uno.views

import uno.models.*
import uno.util.{Event, Observer}
import uno.controller.GameController
import uno.util.Event._

import scala.io.StdIn
import scala.io.AnsiColor._

class TUI(val controller: GameController) extends Observer {
  controller.add(this)

  override def update(e: Event): Unit =
    e match {
      case Quit => gameOver()
      case _    => gameLoop()
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
        controller.initGame()
      case Some(2) =>
        startGame()
      case Some(3) =>
        controller.quitGame()
        println("Goodbye!")
      case _ =>
        println("Invalid input. Please enter a number between 1 and 3.")
        startGame()
    }
  }

  private def gameLoop(): Unit = {

    clearScreen()
    val currentPlayer =
      controller.round.players(controller.round.currentPlayer)
    println(s"Current player: Player ${controller.round.currentPlayer + 1}")

    println(
      s"Current top card: ${controller.round.topCard.getColorCode}${controller.round.topCard.value}$RESET"
    )

    currentPlayer.hand.cards.zipWithIndex.foreach { case (card, index) =>
      println(s"${index + 1}: ${card.getColorCode}${card.value}$RESET")
    }

    println("Enter the number of the card you want to play:")
    val cardNumber = StdIn.readLine().toIntOption
    if (cardNumber.isEmpty) {
      println("Invalid input. Please enter a number.")
      gameLoop()
    }

    if (
      cardNumber.get < 1 || cardNumber.get > currentPlayer.hand.cards.length
    ) {
      println("Invalid card number. Please try again.")
      gameLoop()
    } else {

      val card = currentPlayer.hand.cards(
        cardNumber.get - 1
      ) // Subtract 1 because list indices start at 0
      if (
        currentPlayer
          .canPlay(card) && card.canBePlayedOn(controller.round.topCard)
      ) {
        controller.playCard(card)
      } else {
        println(
          "You can't play that card. Do you want to draw, or try again?"
        )
        println("1: Draw a card")
        println("2: Try again")

        val selection = StdIn.readLine().toIntOption.getOrElse(-1)
        if (selection == -1) {
          println("Invalid input. Please enter a number.")
          gameLoop()
        } else if (selection == 1) {
          controller.drawCard()
        } else if (selection == 2) {
          gameLoop()
        } else {
          println("Invalid input. Please enter 1 or 2. Try again.")
          gameLoop()
        }
      }
    }
  }

  private def clearScreen(): Unit = {
    print("\u001b[H\u001b[2J")
  }

  private def gameOver(): Unit = {
    clearScreen()
    println("Game over!")
    val winnerIndex =
      controller.round.players.indexWhere(_.hand.cards.isEmpty)
    println(s"Player ${winnerIndex + 1} wins!")
  }

}
