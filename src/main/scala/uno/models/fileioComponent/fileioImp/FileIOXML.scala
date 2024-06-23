package uno.models.fileioComponent.fileioImp

import uno.models.fileioComponent.IFileIO
import uno.models.gameComponent.IRound
import uno.models.gameComponent.gameImp.Round

class FileIOXML extends IFileIO:
  override def load: IRound =
    val elem = scala.xml.XML.loadFile("round.xml")
    Round.fromXml(elem)

  override def save(round: Round): Unit =
    scala.xml.XML.save("round.xml", round.toXml)
