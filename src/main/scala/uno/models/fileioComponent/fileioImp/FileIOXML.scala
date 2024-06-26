package uno.models.fileioComponent.fileioImp

import com.google.inject.Inject
import uno.models.fileioComponent.IFileIO
import uno.models.gameComponent.IRound
import uno.models.gameComponent.gameImp.Round

import scala.util.{Success, Try}

class FileIOXML @Inject extends IFileIO:
  override def load: IRound =
    val elem = scala.xml.XML.loadFile("round.xml")
    Round.fromXml(elem)

  override def save(round: IRound): Unit =
    scala.xml.XML.save("round.xml", round.toXml)
