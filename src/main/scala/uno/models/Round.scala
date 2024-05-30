package uno.models

case class Round(
    players: List[Player] = (0 until 2).map(i => Player(i, Hand())).toList,
    topCard: Card = CardFacade().randomCard,
    currentPlayer: Int = 0
) {}
