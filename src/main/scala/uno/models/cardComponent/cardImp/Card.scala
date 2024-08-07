package uno.models.cardComponent.cardImp

import com.google.inject.Inject
import play.api.libs.json._
import play.api.libs.json.Format.GenericFormat
import uno.models.cardComponent.ICard

import scala.io.AnsiColor

enum cardColors:
  case RED, GREEN, BLUE, YELLOW

enum cardValues:
  case ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, SKIP,
    DRAW_TWO, WILD, WILD_DRAW_FOUR

class Card @Inject (color: Option[cardColors], value: cardValues) extends ICard:

  def getColor: Option[cardColors] = color

  def getValue: cardValues = value

  def canBePlayedOn(topCard: ICard): Boolean =
    this.getValue == cardValues.WILD ||
      this.getValue == topCard.getValue ||
      (this.getColor match {
        case None => true
        case Some(c) =>
          topCard.getColor match {
            case None     => true
            case Some(tc) => c == tc
          }
      })

  def getColorCode: String =
    this.getColor match {
      case None                    => AnsiColor.WHITE
      case Some(cardColors.RED)    => AnsiColor.RED
      case Some(cardColors.GREEN)  => AnsiColor.GREEN
      case Some(cardColors.YELLOW) => AnsiColor.YELLOW
      case Some(cardColors.BLUE)   => AnsiColor.BLUE
    }

  def toXml: scala.xml.Elem =
    <card>
      <color>
        {color}
      </color>
      <value>
        {value}
      </value>
    </card>

  override def equals(obj: Any): Boolean =
    obj match
      case c: Card => c.getColor == this.getColor && c.getValue == this.getValue
      case _       => false

  override def toString: String =
    s"$getColorCode${this.getColor.getOrElse("None")}, ${this.getValue}${AnsiColor.RESET}"

object Card:
  def fromXml(node: scala.xml.Node): ICard =
    val color: Option[cardColors] = (node \ "color").text.trim match {
      case "None"         => None
      case "Some(RED)"    => Some(cardColors.RED)
      case "Some(GREEN)"  => Some(cardColors.GREEN)
      case "Some(BLUE)"   => Some(cardColors.BLUE)
      case "Some(YELLOW)" => Some(cardColors.YELLOW)
    }
    val value = (node \ "value").text.trim match {
      case "ZERO"           => cardValues.ZERO
      case "ONE"            => cardValues.ONE
      case "TWO"            => cardValues.TWO
      case "THREE"          => cardValues.THREE
      case "FOUR"           => cardValues.FOUR
      case "FIVE"           => cardValues.FIVE
      case "SIX"            => cardValues.SIX
      case "SEVEN"          => cardValues.SEVEN
      case "EIGHT"          => cardValues.EIGHT
      case "NINE"           => cardValues.NINE
      case "SKIP"           => cardValues.SKIP
      case "DRAW_TWO"       => cardValues.DRAW_TWO
      case "WILD"           => cardValues.WILD
      case "WILD_DRAW_FOUR" => cardValues.WILD_DRAW_FOUR
    }
    Card(color, value)

  implicit val cardFormat: Format[ICard] = new Format[ICard]:
    def writes(card: ICard): JsValue =
      val color = card.getColor.getOrElse("").toString
      val value = card.getValue.toString
      Json.obj(
        "color" -> Json.toJson(color),
        "value" -> Json.toJson(value)
      )

    def reads(json: JsValue): JsResult[ICard] =
      for
        color <- (json \ "color").validate[String] match {
          case JsSuccess("", _) => JsSuccess(None)
          case JsSuccess(s, _) =>
            s match {
              case "RED"    => JsSuccess(Some(cardColors.RED))
              case "GREEN"  => JsSuccess(Some(cardColors.GREEN))
              case "BLUE"   => JsSuccess(Some(cardColors.BLUE))
              case "YELLOW" => JsSuccess(Some(cardColors.YELLOW))
              case _        => JsError("Invalid color")
            }
          case JsError(e) => JsError(e)
        }
        value <- (json \ "value").validate[String] match {
          case JsSuccess("ZERO", _)     => JsSuccess(cardValues.ZERO)
          case JsSuccess("ONE", _)      => JsSuccess(cardValues.ONE)
          case JsSuccess("TWO", _)      => JsSuccess(cardValues.TWO)
          case JsSuccess("THREE", _)    => JsSuccess(cardValues.THREE)
          case JsSuccess("FOUR", _)     => JsSuccess(cardValues.FOUR)
          case JsSuccess("FIVE", _)     => JsSuccess(cardValues.FIVE)
          case JsSuccess("SIX", _)      => JsSuccess(cardValues.SIX)
          case JsSuccess("SEVEN", _)    => JsSuccess(cardValues.SEVEN)
          case JsSuccess("EIGHT", _)    => JsSuccess(cardValues.EIGHT)
          case JsSuccess("NINE", _)     => JsSuccess(cardValues.NINE)
          case JsSuccess("SKIP", _)     => JsSuccess(cardValues.SKIP)
          case JsSuccess("DRAW_TWO", _) => JsSuccess(cardValues.DRAW_TWO)
          case JsSuccess("WILD", _)     => JsSuccess(cardValues.WILD)
          case JsSuccess("WILD_DRAW_FOUR", _) =>
            JsSuccess(cardValues.WILD_DRAW_FOUR)
          case _ => JsError("Invalid value")
        }
      yield Card(color, value)

def randomColor: cardColors =
  cardColors.values.toList(scala.util.Random.nextInt(cardColors.values.length))

def randomNormalValue: cardValues =
  val value = cardValues.values.toList(
    scala.util.Random.nextInt(cardValues.values.length - 2)
  )
  value

def randomWildValue: cardValues =
  List(cardValues.WILD, cardValues.WILD_DRAW_FOUR)(scala.util.Random.nextInt(2))

def randomCard: Card =
  val wildchance = 0.1
  if scala.util.Random.nextDouble() < wildchance then
    Card(None, randomWildValue)
  else Card(Some(randomColor), randomNormalValue)

def randomCards(n: Int): List[Card] = List.fill(n)(randomCard)
