object Main {
  def main(args: Array[String]): Unit = {

    case class Player(name: String)

    val player1 = Player("Selin")
    val player2 = Player("Fabian")

    println(s"Player 1: ${player1.name}")
    println(s"Player 2: ${player2.name}")
  }
}
