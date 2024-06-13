package uno.patterns.memento

class Caretaker {
  private var mementos: List[Memento] = List()

  def addMemento(memento: Memento): Unit = {
    mementos = memento :: mementos
  }

  def getMemento: Option[Memento] = {
    val memento = mementos.headOption
    if (memento.isDefined) {
      mementos = mementos.tail
    }
    memento
  }
}
