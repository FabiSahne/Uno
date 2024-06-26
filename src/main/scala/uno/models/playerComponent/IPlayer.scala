package uno.models.playerComponent

import uno.models.cardComponent.ICard
import uno.models.gameComponent.IHand

import scala.util.Try

trait IPlayer {
  def id: Int
  def hand: IHand
  def canPlay(card: ICard): Boolean
  def playCard(card: ICard): Try[IPlayer]
  def toXml: scala.xml.Elem
}
