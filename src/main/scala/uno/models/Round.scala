package uno.models

case class Round(
    val players: List[Player] = (0 until 2).map(i => Player(i, Hand())).toList,
    val topCard: Card = randomCard,
    val currentPlayer: Int = 0
) {}
