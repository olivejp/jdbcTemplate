package nc.oliweb.jdbctemplate.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for {@link UserService}.
 */
@RunWith(JUnit4.class)
public class BaoLocServiceTest {

    BaoLocService baoLocService;

    @BeforeEach
    public void setup() {
        baoLocService = new BaoLocService();
    }

    @Test
    public void isListCorrectTest() throws Exception {
        List<Double> doubleTrue = Arrays.asList(5d, 8d);
        String[] operatorTrue = {"+"};
        assertThat(baoLocService.isListCorrect(doubleTrue, operatorTrue, 13)).isTrue();

        List<Double> doubleTrueAgain = Arrays.asList(1d, 1d, 5d, 30d, 21d);
        String[] operatorTrueAgain = {"+", "-", "*", "+"};
        assertThat(baoLocService.isListCorrect(doubleTrueAgain, operatorTrueAgain, -69)).isTrue();

        List<Double> doubleFalse = Arrays.asList(2d, 1d);
        String[] operatorFalse = {"+"};
        assertThat(baoLocService.isListCorrect(doubleFalse, operatorFalse, 5)).isFalse();
    }

    @Test
    public void isListCorrectTestShouldThrow() {
        List<Double> doubleException = Arrays.asList(1d, 1d, 5d, 30d, 21d);
        String[] operatorTrue = {"+"};
        Assertions.assertThrows(Exception.class, () -> {
            baoLocService.isListCorrect(doubleException, operatorTrue, 2);
        });
    }

    @Test
    public void isListCorrectTestShouldThrowA() throws Exception {
        String[] operatorTrue = {"+", "*", "/", "+", "+", "*", "-", "-", "+", "*", "/", "-"};
        double[] possibilities = {1d, 2d, 3d, 4d, 5d, 6d, 7d, 8d, 9d};
        List<Double> list = Arrays.asList(0d, 13d, 0d, 0d, 0d, 12d, 0d, 0d, 11d, 0d, 0d, 0d, 10d);
        assertThat(baoLocService.isListCorrect(baoLocService.blabla(list, possibilities, operatorTrue, 8), operatorTrue, 66)).isTrue();
    }
}
