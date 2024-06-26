package uno.models.fileioComponent

import uno.models.gameComponent.IRound
import scala.util.Try

trait IFileIO:
  def load: Try[IRound]
  def save(round: IRound): Unit
