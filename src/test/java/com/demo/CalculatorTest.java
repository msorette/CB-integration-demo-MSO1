package com.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires pour la classe Calculator.
 * Valide les trois méthodes principales et le cas d'erreur de division par zéro.
 */
@DisplayName("Tests de la classe Calculator")
class CalculatorTest {

    private Calculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new Calculator();
    }

    @Test
    @DisplayName("Test de la méthode sayHello - doit retourner 'Hello world'")
    void testSayHello() {
        // When
        String result = calculator.sayHello();

        // Then
        assertEquals("Hello world", result, "La méthode sayHello devrait retourner 'Hello world'");
    }

    @Test
    @DisplayName("Test de la méthode add - doit additionner correctement deux nombres")
    void testAdd() {
        // When
        int result1 = calculator.add(5, 3);
        int result2 = calculator.add(-2, 7);
        int result3 = calculator.add(0, 0);

        // Then
        assertEquals(8, result1, "5 + 3 devrait égaler 8");
        assertEquals(5, result2, "-2 + 7 devrait égaler 5");
        assertEquals(0, result3, "0 + 0 devrait égaler 0");
    }

    @Test
    @DisplayName("Test de la méthode divide - doit diviser correctement deux nombres")
    void testDivide() {
        // When
        double result1 = calculator.divide(10.0, 2.0);
        double result2 = calculator.divide(9.0, 3.0);
        double result3 = calculator.divide(7.0, 2.0);

        // Then
        assertEquals(5.0, result1, 0.001, "10 / 2 devrait égaler 5");
        assertEquals(3.0, result2, 0.001, "9 / 3 devrait égaler 3");
        assertEquals(3.5, result3, 0.001, "7 / 2 devrait égaler 3.5");
    }

    @Test
    @DisplayName("Test de la méthode divide avec diviseur zéro - doit lever une ArithmeticException")
    void testDivideByZero() {
        // When & Then
        ArithmeticException exception = assertThrows(
            ArithmeticException.class,
            () -> calculator.divide(10.0, 0.0),
            "La division par zéro devrait lever une ArithmeticException"
        );

        // Vérifier le message de l'exception
        assertEquals("Division par zéro impossible", exception.getMessage(),
            "Le message d'erreur devrait être 'Division par zéro impossible'");
    }
}
