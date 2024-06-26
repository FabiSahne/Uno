package models

import uno.models.*
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*
import uno.models.cardComponent.cardImp._
import uno.models.gameComponent.gameImp.Hand

class HandTest extends AnyWordSpec {
  "Hand" when {
    "created" should {
      "have 7 cards" in {
        val hand = Hand()
        hand.cards.size should be(7)
      }
      "be 7 random cards" in {
        val hand1 = Hand()
        val hand2 = Hand()
        hand1.cards should not be hand2.cards
      }
    }
    "existing" should {
      "be able to get a card removed" in {
        val hand = Hand(randomCards(2))
        val card = hand.cards.head
        val newHand = hand.removeCard(card)
        newHand.cards should be(hand.cards.tail)
      }
      "be able to get a card added" in {
        val hand = Hand(randomCards(2))
        val card = Card(Some(cardColors.RED), cardValues.ZERO)
        val newHand = hand.addCard(card)
        newHand.cards should be(card +: hand.cards)
      }
    }
  }
}
