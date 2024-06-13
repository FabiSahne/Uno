package uno.models

case class Round(
    players: List[Player] = (0 until 4).map(i => Player(i, Hand())).toList,
    topCard: Card = CardFacade().randomCard,
    currentPlayer: Int = 0,
    direction: Int = 1
) {}
