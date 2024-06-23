package uno.models.cardComponent

import uno.models.cardComponent.cardImp._

trait ICard:
  def getColor: Option[cardColors]
  def getValue: cardValues
  def getColorCode: String
  def canBePlayedOn(topCard: ICard): Boolean
  def toXml: scala.xml.Elem
  def fromXml(node: scala.xml.Node): Card
