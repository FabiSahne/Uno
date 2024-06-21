package uno.models.cardComponent.cardImp

import com.google.inject.Inject
import uno.models.cardComponent.ICard

import scala.io.AnsiColor

enum cardColors:
  case RED, GREEN, BLUE, YELLOW

enum cardValues:
  case ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, SKIP,
    REVERSE, DRAW_TWO, WILD, WILD_DRAW_FOUR

class Card @Inject (color: Option[cardColors], value: cardValues) extends ICard {
  def getColor: Option[cardColors] = color
  def getValue: cardValues = value
  def canBePlayedOn(topCard: ICard): Boolean =
    this.getValue == cardValues.WILD ||
    (this.getColor match {
      case None => true
      case Some(c) =>
        topCard.getColor match {
          case None => true
          case Some(tc) => c == tc
        }
    })

  def getColorCode: String = {
    this.getColor match {
      case None                    => AnsiColor.WHITE
      case Some(cardColors.RED)    => AnsiColor.RED
      case Some(cardColors.GREEN)  => AnsiColor.GREEN
      case Some(cardColors.YELLOW) => AnsiColor.YELLOW
      case Some(cardColors.BLUE)   => AnsiColor.BLUE
    }
  }


}

def randomColor: cardColors =
  cardColors.values.toList(scala.util.Random.nextInt(cardColors.values.length))

def randomNormalValue: cardValues =
  val value = cardValues.values.toList(
    scala.util.Random.nextInt(cardValues.values.length - 2)
  )
  value

def randomWildValue: cardValues =
  List(cardValues.WILD, cardValues.WILD_DRAW_FOUR)(scala.util.Random.nextInt(2))


def randomCard: Card = {
  val wildchance = 0.1
  if (scala.util.Random.nextDouble() < wildchance) {
    Card(None, randomWildValue)
  } else {
    Card(Some(randomColor), randomNormalValue)
  }
}

def randomCards(n: Int): List[Card] = List.fill(n)(randomCard)