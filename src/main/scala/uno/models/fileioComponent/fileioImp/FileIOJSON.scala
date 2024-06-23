package uno.models.fileioComponent.fileioImp

import com.google.inject.Inject
import play.api.libs.json
import play.api.libs.json.*
import play.api.libs.json.JsPath.\
import uno.models.fileioComponent.IFileIO
import uno.models.gameComponent.IRound
import uno.models.gameComponent.gameImp.Round.*

import java.io.PrintWriter

class FileIOJSON @Inject extends IFileIO:
  override def load: IRound =
    val source = scala.io.Source.fromFile("round.json")
    val json = Json.parse(source.getLines.mkString)
    source.close()
    val round = json.as[IRound]
    round

  override def save(round: IRound): Unit =
    val file = new PrintWriter("round.json")
    file.write(Json.toJson(round).toString())
    file.close()
