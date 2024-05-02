package models

import uno.models.*
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*

class RoundTest extends AnyWordSpec {
  "A round" should {
    val round = Round()
    "have two players" in {
      round.players.size should be(2)
    }
  }
}
