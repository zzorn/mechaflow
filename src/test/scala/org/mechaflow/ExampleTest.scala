package org.mechaflow

import org.scalatest.FunSuite
import primitives._
import primitives.ParsedEquation
import primitives.Real

/**
 *
 */
class ExampleTest extends FunSuite {

  test("Designing API") {

    // Resistor example

    val pin = new Model('Pin)
    pin.addModifier('connector)
    pin.addParameter('voltage, Real(0))
    pin.addParameter('current, Real(0), List('flow))

    val twoPinComponent = new Model('twoPinComponent)
    twoPinComponent.addParameter('p, ModelValue(pin))
    twoPinComponent.addParameter('n, ModelValue(pin))
    twoPinComponent.addVariable('voltage, Real(0))
    twoPinComponent.addEquation(ParsedEquation("voltage = p.voltage - n.voltage"))
    twoPinComponent.addEquation(ParsedEquation("p.current + n.current = 0"))
    twoPinComponent.addEquation(ParsedEquation("current = p.current"))

    val resistor = new Model('Resistor)
    resistor.addParent(twoPinComponent)
    twoPinComponent.addParameter('resistance, Real(200))
    resistor.addEquation(ParsedEquation("current = voltage / resistance"))




  }

}
