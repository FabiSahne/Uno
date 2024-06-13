package uno.models

import scala.util.Random

private val WildChance = 0.10

class CardFacade {
  def randomCard: Card =
    if (Random.nextDouble() < WildChance) {
      WildCard(None, randomWildValue)
    } else {
      NormalCard(Some(randomColor), randomNormalValue)
    }
    
  def randomCards(n: Int): List[Card] = (0 until n).map(_ => randomCard).toList
}
