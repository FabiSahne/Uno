package models

import uno.models.*
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*
import uno.models.cardComponent.cardImp.Card
import uno.models.gameComponent.gameImp.{Hand, Round}
import uno.models.playerComponent.playerImp.Player
import uno.models.cardComponent.cardImp.cardValues.*
import uno.models.cardComponent.cardImp.cardColors.*

class RoundTest extends AnyWordSpec {
  "A round" should {
    val player1 = Player(0, Hand(List(Card(Some(RED), ZERO))))
    val player2 = Player(1, Hand(List(Card(Some(BLUE), ONE))))
    val round = Round(players = List(player1, player2), topCard = Card(Some(GREEN), TWO), currentPlayer = 0)

    "have two players" in {
      round.players.size should be(2)
    }
  }
}
