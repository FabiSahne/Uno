package models

import uno.models.*
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*

class PlayerTest extends AnyWordSpec {
  "Player" should {
    "check if he can play a card" in {
      val player = Player(0, Hand(List(NormalCard(Some(cardColors.RED), cardValues.ONE))))
      val card = NormalCard(Some(cardColors.RED), cardValues.ONE)
      player.canPlay(card) shouldBe true
    }
    "be able to play a card" in {
      val player = Player(0, Hand(List(NormalCard(Some(cardColors.RED), cardValues.ONE))))
      val card = NormalCard(Some(cardColors.RED), cardValues.ONE)
      player.playCard(card).isSuccess shouldBe true
    }
    "not be able to play a card" in {
      val player = Player(0, Hand(List(NormalCard(Some(cardColors.RED), cardValues.ONE))))
      val card = NormalCard(Some(cardColors.BLUE), cardValues.TWO)
      player.playCard(card).isFailure shouldBe true
    }
  }
}
