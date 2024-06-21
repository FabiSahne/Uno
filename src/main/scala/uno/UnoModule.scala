package uno

import com.google.inject.AbstractModule
import net.codingwell.scalaguice.ScalaModule
import controller.controllerComponent._
import model.fileIOComponent._

class UnoModule extends AbstractModule {

  override def configure(): Any = {
    bind(classOf[controllerInterface]).to(classOf[controllerBaseImp.Controller])
    bind(classOf[FileIOTrait]).to(classOf[fileIOXmlImp.FileIO])