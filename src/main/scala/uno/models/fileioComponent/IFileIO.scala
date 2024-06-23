package uno.models.fileioComponent

import uno.models.gameComponent.IRound

trait IFileIO:
  def load: IRound
  def save(round: IRound): Unit
