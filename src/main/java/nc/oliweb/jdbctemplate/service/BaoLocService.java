package nc.oliweb.jdbctemplate.service;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class BaoLocService {

    List<String> listTest = new ArrayList<>();

    /**
     * Return list of integer
     *
     * @param arrayNumber [0,13,0,0,0,12,0,0,11,0,0,0,10]
     * @param operators   ["+","*","/","+","+","*","-","-","+","*","/","-"]
     * @param expected    66
     * @return
     */
    List<Integer> getBaoLocResult(List<Double> arrayNumber, String[] operators, double expected) throws Exception {
        if (operators.length - arrayNumber.size() != 1) {
            throw new Exception("Incohérence entre le nombre ne correspond pas");
        }
//        addNewDigit(new ArrayList<>(), arrayNumber, operators, expected);
        return new ArrayList<>();
    }

    /**
     * @param listNumber [1,2,1]
     * @param operators  [+,-]
     * @param expected   2
     * @return
     * @throws Exception
     */
    boolean isListCorrect(List<Double> listNumber, String[] operators, double expected) throws Exception {
        if (listNumber.size() == 0 || operators.length == 0) {
            throw new Exception("Incohérence entre le nombre ne correspond pas");
        }
        if (listNumber.size() - operators.length != 1) {
            throw new Exception("Incohérence entre le nombre ne correspond pas");
        }
        double result = listNumber.get(0);
        for (int i = 0; i < operators.length; i++) {
            result = makeOperation(result, listNumber.get(i + 1), operators[i]);
        }
        return result == expected;
    }

    Double grabNextAvailableNumber(List<MutablePair<Boolean, Double>> listPossibles) {
        for (MutablePair<Boolean, Double> pair : listPossibles) {
            if (pair.left) {
                pair.left = false;
                return pair.right;
            }
        }
        return -1d;
    }

    void releaseNumber(List<MutablePair<Boolean, Double>> listPossibles, Double numberToRelease) {
        for (MutablePair<Boolean, Double> pair : listPossibles) {
            if (pair.right.equals(numberToRelease)) {
                pair.left = true;
                return;
            }
        }
    }

    boolean allNumberSelected(List<MutablePair<Boolean, Double>> listPossibles) {
        for (MutablePair<Boolean, Double> pair : listPossibles) {
            if (pair.left) {
                return false;
            }
        }
        return true;
    }

    List<Double> replaceInList(List<Double> listStart, double[] numberLeft) {
        List<Double> list = new ArrayList<>(listStart);
        for (double element : numberLeft) {
            int index = list.indexOf(0d);
            list.set(index, element);
        }
        return list;
    }

    List<Double> blabla(List<Double> listStart, double[] numberLeft, String[] operators, double expected) throws Exception {
        Pair<Boolean, List<Double>> pair;
        int indexElement = 0;
        int test = 1;
        do {
            if (indexElement < numberLeft.length - 1) {
                indexElement++;
            } else {
                indexElement = 1;
                test++;
            }
            pair = addNewDigit(indexElement, test, listStart, numberLeft, operators, expected);
        } while (!pair.getFirst());
        return pair.getSecond();
    }

    Pair<Boolean, List<Double>> addNewDigit(int indexElement, int test, List<Double> listStart, double[] numberLeft, String[] operators, double expected) throws Exception {
        List<Double> list = replaceInList(listStart, numberLeft);
        if (indexElement - test > 0) {
            double numberA = numberLeft[indexElement - test];
            double numberB = numberLeft[indexElement];
            numberLeft[indexElement - test] = numberB;
            numberLeft[indexElement] = numberA;
            return Pair.of(isListCorrect(list, operators, expected), list);
        }
        return Pair.of(false, list);
    }

//    Pair<Boolean, List<Double>> addNewDigit(List<Double> list, List<MutablePair<Boolean, Double>> numberLeft, String[] operators, double expected) throws Exception {
//        while (!listTest.contains(list.toString())) {
//            Double removedNumber = grabNextAvailableNumber(numberLeft);
//            if (removedNumber != -1) {
//                list.add(removedNumber);
//            }
//            if (allNumberSelected(numberLeft)) {
//                listTest.add(list.toString());
//                if (isListCorrect(list, operators, expected)) {
//                    return Pair.of(true, list);
//                }
//                releaseNumber(numberLeft, removedNumber);
//                return Pair.of(false, list);
//            } else {
//                Pair<Boolean, List<Double>> pair = addNewDigit(list, numberLeft, operators, expected);
//                if (pair.getFirst()) {
//                    return pair;
//                } else {
//                    list.remove(list.size() - 1);
//                    releaseNumber(numberLeft, removedNumber);
//                }
//            }
//        }
//        return Pair.of(false, list);
//    }

    private double makeOperation(double start, double item, String operator) throws Exception {
        switch (operator) {
            case "+":
                return start + item;
            case "-":
                return start - item;
            case "*":
                return start * item;
            case "/":
                return start / item;
            default:
                throw new Exception("Opérateur inconnu");
        }
    }

    private Pair<Integer, Integer> getRandomValueFromArray(int[] arrayNumber) {
        Random r = new Random();
        int index = r.nextInt(arrayNumber.length);
        return Pair.of(index, arrayNumber[index]);
    }
}
