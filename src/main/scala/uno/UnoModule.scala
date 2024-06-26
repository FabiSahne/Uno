package uno

import com.google.inject.{AbstractModule, TypeLiteral}
import net.codingwell.scalaguice.ScalaModule
import uno.controller.GControllerImp.GameController
import uno.controller.GameControllerInterface
import uno.models.cardComponent.ICard
import uno.models.gameComponent.{IHand, IRound}
import uno.models.playerComponent.IPlayer
import uno.models.playerComponent.playerImp.Player
import uno.models.cardComponent.cardImp.*
import uno.models.cardComponent.cardImp.cardValues.ONE
import uno.models.fileioComponent.IFileIO
import uno.models.fileioComponent.fileioImp.*
import uno.models.gameComponent.gameImp.*

class UnoModule extends AbstractModule with ScalaModule {

  override def configure(): Unit = {
    bind(classOf[GameControllerInterface]).to(classOf[GameController])
    bind(classOf[ICard]).to(classOf[Card])
    bind(classOf[IHand]).to(classOf[Hand])
    bind(classOf[IRound]).to(classOf[Round])
    bind(classOf[IPlayer]).to(classOf[Player])
    bind(classOf[IFileIO]).to(classOf[FileIOJSON])

    bind(new TypeLiteral[Option[cardColors]] {}).toInstance(None)
    bind(new TypeLiteral[cardValues] {}).toInstance(ONE)
    bind(new TypeLiteral[Hand] {}).toInstance(Hand())
    bind(new TypeLiteral[Round] {})
      .toInstance(Round(List.empty, Card(None, ONE), 0))
    bind(new TypeLiteral[Player] {}).toInstance(Player(0, Hand()))

  }
}
