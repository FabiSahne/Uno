object Main {
  def main(args: Array[String]): Unit = {

    case class Player(name: String, age: Int)

    val player1 = Player("Selin", 25)
    val player2 = Player("Fabian", 30)

    println(s"Player 1: ${player1.name}")
    println(s"Player 2: ${player2.name}")
  }
}
