object Main {
  def main(args: Array[String]): Unit = {

    case class Player(name: String, score: Int)

    val player1 = Player("Alice", 10)
    val player2 = Player("Bob", 20)

    println(s"Player 1: ${player1.name}")
    println(s"Player 2: ${player2.name}")
  }
}
