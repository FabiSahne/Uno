package uno.models.fileioComponent.fileioImp

import com.google.inject.Inject
import play.api.libs.json
import play.api.libs.json.*
import uno.models.fileioComponent.IFileIO
import uno.models.gameComponent.IRound
import uno.models.gameComponent.gameImp.Round.*

import java.io._

class FileIOJSON @Inject extends IFileIO:
  override def load: IRound =
    val source = scala.io.Source.fromFile("round.json")
    val round = Json.parse(source.getLines.mkString).as[IRound]
    source.close()
    round

  override def save(round: IRound): Unit =
    val file = new File("round.json")
    if file.exists() then file.delete()
    val writer = new PrintWriter(new File("round.json"))
    writer.write(Json.toJson(round).toString())
    writer.close()
