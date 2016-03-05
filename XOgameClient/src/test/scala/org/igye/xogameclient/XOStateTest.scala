package org.igye.xogameclient

import org.junit.{Assert, Test}

class XOStateTest {
  @Test
  def testToString1: Unit = {
    Assert.assertEquals(
      " | |X\n" +
      "-----\n" +
      " |O| \n" +
      "-----\n" +
      " | | ",
      XOState().withField(2, Fields.X).withField(1, 1, Fields.O).toString
    )
  }

  @Test
  def testApply1: Unit = {
    Assert.assertEquals(
      Fields.EMPTY,
      XOState()(5)
    )
  }

  @Test
  def testApply2: Unit = {
    Assert.assertEquals(
      Fields.O,
      XOState().withField(7, Fields.O).withField(1, 1, Fields.X)(7)
    )
  }

  @Test
  def testApply3: Unit = {
    Assert.assertEquals(
      Fields.X,
      XOState().withField(1, 0, Fields.X).withField(1, 1, Fields.O)(1, 0)
    )
  }
}
