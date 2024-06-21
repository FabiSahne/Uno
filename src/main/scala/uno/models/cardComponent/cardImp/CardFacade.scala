package uno.models.cardComponent.cardImp

import uno.models.cardComponent.ICardFacade
import uno.models.cardComponent.cardTypeImp.{NormalCard, WildCard}

import scala.util.Random

private val WILD_CHANCE = 0.10

class CardFacade extends ICardFacade {
  def randomCard: Card =
    if (Random.nextDouble() < WILD_CHANCE) {
      WildCard(None, randomWildValue)
    } else {
      NormalCard(Some(randomColor), randomNormalValue)
    }
    
  def randomCards(n: Int): List[Card] = (0 until n).map(_ => randomCard).toList
}
