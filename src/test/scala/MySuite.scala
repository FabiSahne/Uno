import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
class MySuite extends AnyWordSpec {

  "Uno" should {
    "have cards" in {
      val c1 = uno.Card(uno.cardColors.RED, uno.cardValues.ONE)
      c1.color shouldEqual uno.cardColors.RED
      c1.value shouldEqual uno.cardValues.ONE
      val c2 = uno.Card(uno.cardColors.BLUE, uno.cardValues.ONE)
      val c3 = uno.Card(uno.cardColors.RED, uno.cardValues.TWO)
      val c4 = uno.Card(uno.cardColors.BLUE, uno.cardValues.TWO)
      assert(c2.canBePlayedOn(c1))
      assert(c3.canBePlayedOn(c3))
      assert(!c4.canBePlayedOn(c1))
      assert(c4.canBePlayedOn(c3))
    }
    "have players" in {
      val hand1: List[uno.Card] = List(uno.Card(uno.cardColors.RED, uno.cardValues.ONE), uno.Card(uno.cardColors.RED, uno.cardValues.TWO))
      val p1 = uno.Player(0, hand1)
      val topCard = uno.Card(uno.cardColors.RED, uno.cardValues.TWO)
      assert(p1.canPlay(topCard))
      val topCard2 = uno.Card(uno.cardColors.BLUE, uno.cardValues.THREE)
      assert(!p1.canPlay(topCard2))
    }
    // create multiple little tests


  }
}
