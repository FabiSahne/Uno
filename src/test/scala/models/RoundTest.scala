package models

import uno.models.*
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*
import uno.models.gameComponent.gameImp.{Hand, Round}
import uno.models.playerComponent.playerImp.Player
import uno.models.cardComponent.cardImp.cardValues.*
import uno.models.cardComponent.cardImp.cardColors.*
import uno.models.cardComponent.cardImp.Card
import uno.models.fileioComponent.fileioImp.{FileIOJSON, FileIOXML}

class RoundTest extends AnyWordSpec {
  "A round" should {
    val player1 = Player(
      0,
      Hand(
        List(
          Card(Some(RED), ZERO),
          Card(Some(GREEN), FOUR),
          Card(Some(YELLOW), THREE),
          Card(Some(RED), FIVE),
          Card(Some(RED), SIX),
          Card(Some(RED), SEVEN),
          Card(Some(RED), EIGHT),
          Card(Some(RED), NINE),
          Card(Some(RED), SKIP),
          Card(Some(RED), REVERSE),
          Card(Some(RED), DRAW_TWO),
          Card(None, WILD),
          Card(None, WILD_DRAW_FOUR)
        )
      )
    )
    val player2 = Player(1, Hand(List(Card(Some(BLUE), ONE))))
    val round = Round(
      players = List(player1, player2),
      topCard = Card(Some(GREEN), TWO),
      currentPlayer = 0
    )

    "have two players" in {
      round.players.size should be(2)
    }
    "be saved as xml" in {
      val fileIOXML = new FileIOXML
      fileIOXML.save(round)
      val roundFromXml = fileIOXML.load
      roundFromXml.players.size should be(2)
      roundFromXml.topCard.getValue should be(TWO)
    }
    "be saved as json" in {
      val fileIOJSON = new FileIOJSON
      fileIOJSON.save(round)
      val roundFromJson = fileIOJSON.load
      roundFromJson.players.size should be(2)
      roundFromJson.topCard.getValue should be(TWO)
    }
    "be copyable" in {
      val roundCopy = round.copy()
      roundCopy.players.size should be(2)
    }
  }
}
