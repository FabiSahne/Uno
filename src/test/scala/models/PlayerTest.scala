package models

import uno.models.*
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*
import uno.models.cardComponent.cardImp._
import uno.models.cardComponent.*
import uno.models.gameComponent.gameImp.Hand
import uno.models.playerComponent.playerImp
import uno.models.playerComponent.playerImp.Player

class PlayerTest extends AnyWordSpec {
  "Player" should {
    "check if he can play a card" in {
      val player = Player(0, Hand(List(Card(Some(cardColors.RED), cardValues.ONE))))
      val card = Card(Some(cardColors.RED), cardValues.ONE)
      player.canPlay(card) shouldBe true
    }
    "be able to play a card" in {
      val player = playerImp.Player(0, Hand(List(Card(Some(cardColors.RED), cardValues.ONE))))
      val card = Card(Some(cardColors.RED), cardValues.ONE)
      player.playCard(card).isSuccess shouldBe true
    }
    "not be able to play a card" in {
      val player = playerImp.Player(0, Hand(List(Card(Some(cardColors.RED), cardValues.ONE))))
      val card = Card(Some(cardColors.BLUE), cardValues.TWO)
      player.playCard(card).isFailure shouldBe true
    }
  }
}
