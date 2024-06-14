package uno.models.playerComponent

import uno.models.cardComponent.cardImp.Card
import uno.models.gameComponent.gameImp.Hand

import scala.util.Try

trait IPlayer {
  def id: Int
  def hand: Hand
  def canPlay(card: Card): Boolean
  def playCard(card: Card): Try[IPlayer]
}