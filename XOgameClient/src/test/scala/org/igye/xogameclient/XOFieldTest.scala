package org.igye.xogameclient

import org.igye.xogamecommons.{Cells, XOField}
import org.junit.{Assert, Test}

class XOFieldTest {
  @Test
  def testToString1: Unit = {
    Assert.assertEquals(
      " | |X\n" +
      "-----\n" +
      " |O| \n" +
      "-----\n" +
      " | | ",
      XOField().withField(2, Cells.X).withField(1, 1, Cells.O).toString
    )
  }

  @Test
  def testApply1: Unit = {
    Assert.assertEquals(
      Cells.EMPTY,
      XOField()(5)
    )
  }

  @Test
  def testApply2: Unit = {
    Assert.assertEquals(
      Cells.O,
      XOField().withField(7, Cells.O).withField(1, 1, Cells.X)(7)
    )
  }

  @Test
  def testApply3: Unit = {
    Assert.assertEquals(
      Cells.X,
      XOField().withField(1, 0, Cells.X).withField(1, 1, Cells.O)(1, 0)
    )
  }
}
