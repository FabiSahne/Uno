package uno


class UnoModule extends AbstractModule {

  override def configure(): Any = {
    bind(classOf[controllerInterface]).to(classOf[controllerBaseImp.Controller])
    bind(classOf[FileIOTrait]).to(classOf[fileIOXmlImp.FileIO])
  }
}