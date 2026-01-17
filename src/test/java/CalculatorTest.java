
import com.example.bank.bank.service.CalcuatorService;
import com.example.bank.bank.service.CalcuatorServiceImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CalculatorTest {

    private CalcuatorService calcuatorService = new CalcuatorServiceImpl();

    @Test
    public void addition() {
        int result = calcuatorService.add(2, 3);
        assertEquals(5, result, "Verify basic addition, result should be 5");
    }

    @Test
    public void division() {
        int result = calcuatorService.divide(3, 3);
        assertNotEquals(0, result, "Verify basic division, result should not be 0");
    }

    @Test
    public void isEven() {
        boolean result = calcuatorService.isEven(2);
        assertTrue(result, "The number should be even");
    }

    @Test
    public void isEvenWithOddNum() {
        boolean result = calcuatorService.isEven(3);
        assertFalse(result, "The number should not be even");
    }

    @Test
    public void divideByZero() {
        ArithmeticException e = assertThrows(ArithmeticException.class, () -> {
            calcuatorService.divide(6, 0);
        });
        assertEquals("Cannot divide by zero", e.getMessage());
    }

    @Test
    public void divideNoneZero() {
        Integer result = null;
        result = calcuatorService.divide(10, 2);
        assertNotNull(result, "Verify basic division, result should be 5");
    }
}

