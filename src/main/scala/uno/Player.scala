package uno
import scala.collection.immutable.List

case class Player(score: Int, hand: List[Card]) {
  def canPlay(card: Card): Boolean = {
    hand.exists(_.canBePlayedOn(card))
  }
  def playes(card: Card): Player = {
    if (canPlay(card)) {
      // wrong
      val newHand = hand.filterNot(_.canBePlayedOn(card))
      Player(score, newHand)
    } else {
      throw new IllegalArgumentException("Can't play that card")
    }
  }
}