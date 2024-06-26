package uno.models.fileioComponent.fileioImp

import com.google.inject.Inject
import play.api.libs.json
import play.api.libs.json.*
import play.api.libs.json.JsPath.\
import uno.models.fileioComponent.IFileIO
import uno.models.gameComponent.IRound
import uno.models.gameComponent.gameImp.Round.*

import java.io.PrintWriter
import scala.util.{Failure, Success, Try}

class FileIOJSON @Inject extends IFileIO:
  override def load: Try[IRound] =
    val source = scala.io.Source.fromFile("round.json")
    if source.isEmpty then
      source.close()
      return Failure(new Exception("File not found"))
    val round = Json.parse(source.getLines.mkString).as[IRound]
    source.close()
    Success(round)

  override def save(round: IRound): Unit =
    val file = new PrintWriter("round.json")
    file.write(Json.toJson(round).toString())
    file.close()
