package uno.views

import uno.controller.GControllerImp.GameController
import uno.models.*
import uno.util.{Event, Observer}
import uno.util.Event.*
import javafx.application.Platform
import uno.controller.GameControllerInterface
import uno.models.fileioComponent.IFileIO
import uno.models.fileioComponent.fileioImp.{FileIOJSON, FileIOXML}

import scala.annotation.tailrec
import scala.io.StdIn
import scala.io.AnsiColor.*

class TUI(val controller: GameControllerInterface) extends Observer {
  controller.add(this)
  private var gameStarted = false
  private var gameIsOver = false

  override def update(e: Event): Unit = {
    // println(s"Received event: $e") // Logging
    Platform.runLater(() => {
      e match {
        case Quit        => gameIsOver = true; gameOver()
        case ChooseColor => chooseColor()
        case Play        => gameLoop()
        case Start       => gameStarted = true
        case _           => ()
      }
    })
  }

  @tailrec
  private def chooseColor(): Unit = {
    println("Choose a color:")
    println("1: Red")
    println("2: Blue")
    println("3: Green")
    println("4: Yellow")
    val selection = StdIn.readLine().toIntOption.getOrElse(-1)
    if (selection < 1 || selection > 4) {
      println("Invalid input. Please enter a number between 1 and 4.")
      chooseColor()
    } else {
      controller.chooseColor(selection)
    }
  }

  def startGame(): Unit = {
    clearScreen()
    println("Welcome to Uno, the card game that destroys friendships!")
    displayMainMenu()
    println("Please enter a number from the menu above:")

    val selection = StdIn.readLine().toIntOption

    if (gameStarted) {
      println("Game already started. Please reenter your input.")
    } else {
      selection match {
        case Some(1) =>
          val playerCount = askPlayerCount()
          controller.initGame(playerCount)
        case Some(2) =>
          controller.loadGame()
        case Some(3) =>
          println("Goodbye!")
          controller.quitGame()
        case _ =>
          println(
            "Invalid input. Please enter a number between 1 and 2."
          ) // Adjusted the number range
          startGame()
      }
    }

    inputLoop()
  }

  @tailrec
  private def askPlayerCount(): Int = {
    println("How many players are there? (2-10)")
    val playerCount = StdIn.readLine().toIntOption.getOrElse(-1)
    if (playerCount < 2 || playerCount > 10) {
      println("Invalid input. Please enter a number between 2 and 10.")
      askPlayerCount()
    } else {
      playerCount
    }
  }

  private def displayMainMenu(): Unit = {
    val boxTopBottom = s"$BLUE" + "=" * 40 + s"$RESET"
    val menuItems =
      List(
        "1. Start a new game",
        "2. Load Game",
        "3. Exit"
      ) // Removed "2. View the rules"
    val menuString = menuItems.mkString("\n")

    println(boxTopBottom)
    println(s"$BLUE||" + " " * 36 + "||")
    menuString.split("\n").foreach { item =>
      val paddingSize = (36 - item.length) / 2
      val line =
        s"$BLUE||" + " " * paddingSize + s"$CYAN" + item + " " * (36 - paddingSize - item.length) + s"$BLUE||"
      println(line)
    }
    println(s"$BLUE||" + " " * 36 + s"||")
    println(boxTopBottom)
  }

  private def gameLoop(): Unit = {
    // println("Entering game loop") // Logging
    clearScreen()
    val currentPlayer =
      controller.getRound.players(controller.getRound.currentPlayer)
    println(s"Current player: Player ${controller.getRound.currentPlayer + 1}")
    println(
      s"Current top card: ${controller.getRound.topCard.getColorCode}${controller.getRound.topCard.getValue}$RESET"
    )
    currentPlayer.hand.getCards.zipWithIndex.foreach { case (card, index) =>
      println(s"${index + 1}: ${card.getColorCode}${card.getValue}$RESET")
    }
    println(
      "Enter the number of the card you want to play, or 'u' for undo, 'r' for redo, 's' to save game, 'l' to load game, 'q' to quit game:"
    )

  }

  @tailrec
  final def inputLoop(): Boolean =
    if gameIsOver then return false
    val input = StdIn.readLine()
    input match {
      case "u" => controller.undo()
      case "r" => controller.redo()
      case "s" => controller.saveGame()
      case "l" => controller.loadGame()
      case "q" => controller.quitGame()
      case _ =>
        val cardNumber = input.toIntOption
        if (cardNumber.isEmpty) {
          println(
            "Invalid input. Please enter a number, 'u' for undo, or 'r' for redo, 's' to save game, 'l' to load game, 'q' to quit game:"
          )
          return inputLoop()
        } else {
          val currentPlayer =
            controller.getRound.players(controller.getRound.currentPlayer)
          handleGameMenuInput(
            cardNumber.get,
            currentPlayer.hand.getCards.length
          )
        }
    }
    true

  private def handleGameMenuInput(input: Int, handSize: Int): Unit = {
    // println(s"Handling game menu input: $input") // Logging
    if (input < 1 || input > handSize) {
      println("Invalid card number. Please try again.")
      gameLoop()
    } else {
      val card = controller.getRound
        .players(controller.getRound.currentPlayer)
        .hand
        .getCards(input - 1)
      if (
        controller.getRound
          .players(controller.getRound.currentPlayer)
          .canPlay(card) && card.canBePlayedOn(controller.getRound.topCard)
      ) {
        controller.playCard(card)
      } else {
        println("You can't play that card. Do you want to draw, or try again?")
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
    println("Game over!") // Logging
    clearScreen()
    if controller.getRound.players.exists(_.hand.getCards.isEmpty) then
      val winnerIndex =
        controller.getRound.players.indexWhere(_.hand.getCards.isEmpty)
      println(s"Player ${winnerIndex + 1} wins!")
    else println("No winner found.")
  }
}
