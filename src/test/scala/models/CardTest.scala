package models

import uno.models.*
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*
import play.api.libs.json._
import uno.models.cardComponent.cardImp.*

import scala.io.AnsiColor
import scala.language.postfixOps

class CardTest extends AnyWordSpec {
  "A Card" should {
    "have a color" in {
      val card = Card(Some(cardColors.RED), cardValues.ZERO)
      card.getColor should be(Some(cardColors.RED))
    }
    "have a toString method" in {
      val card = Card(Some(cardColors.RED), cardValues.ZERO)
      val expected = s"${AnsiColor.RED}RED, ZERO${AnsiColor.RESET}"
      card.toString should be(expected)
    }
    "have a value" in {
      val card = Card(Some(cardColors.RED), cardValues.ZERO)
      card.getValue should be(cardValues.ZERO)
    }
    "have a color code" in {
      val redCard = Card(Some(cardColors.RED), cardValues.ZERO)
      redCard.getColorCode should be(AnsiColor.RED)
      val greenCard = Card(Some(cardColors.GREEN), cardValues.ZERO)
      greenCard.getColorCode should be(AnsiColor.GREEN)
      val blueCard = Card(Some(cardColors.BLUE), cardValues.ZERO)
      blueCard.getColorCode should be(AnsiColor.BLUE)
      val yellowCard = Card(Some(cardColors.YELLOW), cardValues.ZERO)
      yellowCard.getColorCode should be(AnsiColor.YELLOW)
      val wildCard = Card(None, cardValues.WILD)
      wildCard.getColorCode should be(AnsiColor.WHITE)
    }
    "be able to be played on a card of the same color" in {
      val card1 = Card(Some(cardColors.RED), cardValues.ZERO)
      val card2 = Card(Some(cardColors.RED), cardValues.ONE)
      card1.canBePlayedOn(card2) should be(true)
    }
    "be able to be played on a card of the same value" in {
      val card1 = Card(Some(cardColors.RED), cardValues.ZERO)
      val card2 = Card(Some(cardColors.BLUE), cardValues.ZERO)
      card1.canBePlayedOn(card2) should be(true)
    }
    "not be able to be played on a card of a different color and value" in {
      val card1 = Card(Some(cardColors.RED), cardValues.ZERO)
      val card2 = Card(Some(cardColors.BLUE), cardValues.ONE)
      card1.canBePlayedOn(card2) should be(false)
    }
    "be able to be played on any card if it is a wild card" in {
      val card1 = Card(None, cardValues.WILD)
      val card2 = Card(Some(cardColors.BLUE), cardValues.ONE)
      card1.canBePlayedOn(card2) should be(true)
    }
    "give random color" in {
      val color = randomColor
      List(
        cardColors.RED,
        cardColors.BLUE,
        cardColors.GREEN,
        cardColors.YELLOW
      ) should contain(color)
    }
    "give random value" in {
      val normalValue = randomNormalValue
      List(
        cardValues.ZERO,
        cardValues.ONE,
        cardValues.TWO,
        cardValues.THREE,
        cardValues.FOUR,
        cardValues.FIVE,
        cardValues.SIX,
        cardValues.SEVEN,
        cardValues.EIGHT,
        cardValues.NINE,
        cardValues.SKIP,
        cardValues.REVERSE,
        cardValues.DRAW_TWO
      ) should contain(normalValue)
      val wildValue = randomWildValue
      List(
        cardValues.WILD,
        cardValues.WILD_DRAW_FOUR
      ) should contain(wildValue)
    }
    "be able to be played on any card if it has no color" in {
      val card1 = Card(None, cardValues.ZERO)
      val card2 = Card(Some(cardColors.BLUE), cardValues.ONE)
      card1.canBePlayedOn(card2) should be(true)
    }
    "not be equal to a non-Card object" in {
      val card = Card(Some(cardColors.RED), cardValues.ZERO)
      val nonCardObject = "Not a Card"
      card.equals(nonCardObject) should be(false)
    }
    "throw an error when trying to create a card with an invalid color from JSON" in {
      val invalidColorJson = Json.obj(
        "color" -> "INVALID_COLOR",
        "value" -> "ZERO"
      )
      Card.cardFormat.reads(invalidColorJson) match {
        case JsError(_) => succeed
        case _ => fail("Expected a JsError")
      }
    }
    "throw an error when trying to create a card with an invalid value from JSON" in {
      val invalidValueJson = Json.obj(
        "color" -> "RED",
        "value" -> "INVALID_VALUE"
      )
      Card.cardFormat.reads(invalidValueJson) match {
        case JsError(_) => succeed
        case _ => fail("Expected a JsError")
      }
    }
  }
}
