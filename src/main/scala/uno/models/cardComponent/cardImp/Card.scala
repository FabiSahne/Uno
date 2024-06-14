package uno.models.cardComponent.cardImp

import uno.models.cardComponent.ICard
import scala.io.AnsiColor

enum cardColors:
  case RED, GREEN, BLUE, YELLOW

enum cardValues:
  case ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, SKIP,
    REVERSE, DRAW_TWO, WILD, WILD_DRAW_FOUR

trait Card(color: Option[cardColors], value: cardValues) extends ICard {
  def getColor: Option[cardColors] = color
  def getValue: cardValues = value
  def canBePlayedOn(topCard: Card): Boolean

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
