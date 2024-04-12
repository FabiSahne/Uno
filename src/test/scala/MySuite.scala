// For more information on writing tests, see
// https://scalameta.org/munit/docs/getting-started.html
class MySuite extends munit.FunSuite {
  test("test card implementation") {
    val c1 = uno.Card(uno.cardColors.RED, uno.cardValues.ONE)
    assertEquals(c1.color, uno.cardColors.RED)
    assertEquals(c1.value, uno.cardValues.ONE)
    val c2 = uno.Card(uno.cardColors.BLUE, uno.cardValues.ONE)
    val c3 = uno.Card(uno.cardColors.RED, uno.cardValues.TWO)
    val c4 = uno.Card(uno.cardColors.BLUE, uno.cardValues.TWO)
    // test can play card
    assert(c2.canBePlayedOn(c1))
    assert(c3.canBePlayedOn(c3))
    assert(!c4.canBePlayedOn(c1))
    assert(c4.canBePlayedOn(c3))
  }
}
