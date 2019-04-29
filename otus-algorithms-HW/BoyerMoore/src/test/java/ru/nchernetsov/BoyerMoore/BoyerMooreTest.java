package ru.nchernetsov.BoyerMoore;

import org.junit.Ignore;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.nchernetsov.BoyerMoore.BoyerMoore.*;

public class BoyerMooreTest {

    @Test
    public void boyerMooreHorspoolAlgTest1() {
        List<BoyerMooreTestCase> boyerMooreTestCases = TestUtils.readTestTsvFileFromResources("string_matching_test_cases.tsv");
        for (BoyerMooreTestCase boyerMooreTestCase : boyerMooreTestCases) {
            String text = boyerMooreTestCase.getText();
            String pattern = boyerMooreTestCase.getPattern();
            List<Integer> rightOccurrencesIndices = boyerMooreTestCase.getOccurrencesIndices();
            List<Integer> calcOccurrencesIndices = boyerMooreHorspoolAlg(text, pattern);
            assertThat(calcOccurrencesIndices).isEqualTo(rightOccurrencesIndices);
        }
    }

    @Test
    public void badCharacterTableTest() {
        Map<Character, Integer> alphabetMap = new HashMap<>();
        alphabetMap.put('A', 0);
        alphabetMap.put('C', 1);
        alphabetMap.put('G', 2);
        alphabetMap.put('T', 3);
        String pattern = "GATTACA";
        int[][] badCharacterTable = badCharacterTable(pattern, alphabetMap);

        assertThat(badCharacterTable).hasSize(4);

        int[] row0 = badCharacterTable[0];
        int[] row1 = badCharacterTable[1];
        int[] row2 = badCharacterTable[2];
        int[] row3 = badCharacterTable[3];

        assertThat(row0).hasSize(pattern.length() + 1);

        assertThat(row0).containsExactly(-1, -1, 1, 1, 1, 4, 4, 6);
        assertThat(row1).containsExactly(-1, -1, -1, -1, -1, -1, 5, 5);
        assertThat(row2).containsExactly(-1, 0, 0, 0, 0, 0, 0, 0);
        assertThat(row3).containsExactly(-1, -1, -1, 2, 3, 3, 3, 3);
    }

    @Test
    public void findPrefixesTest1() {
        int[] prefixes = findPrefixes("тоторо");
        assertThat(prefixes).containsExactly(6, 0, 2, 0, 0, 0);
    }

    @Test
    public void findPrefixesTest2() {
        int[] prefixes = findPrefixes("ababab");
        assertThat(prefixes).containsExactly(6, 0, 4, 0, 2, 0);
    }

    @Test
    public void findPrefixesTest3() {
        int[] prefixes = findPrefixes("aaa");
        assertThat(prefixes).containsExactly(3, 2, 1);
    }

    @Test
    @Ignore(value = "тест проходит, но выполняется очень долго")
    public void findPrefixesTest4() {
        List<FindPrefixesTestCase> findPrefixesTestCases = TestUtils.readTestTxtFileFromResources("preprocess_test_cases.txt");
        int index = 1;
        for (FindPrefixesTestCase testCase : findPrefixesTestCases) {
            String string = testCase.getString();
            int[] rightPrefixesTable = testCase.getPrefixesTable();
            int[] calcPrefixesTable = findPrefixes(string);
            assertThat(calcPrefixesTable).isEqualTo(rightPrefixesTable);
            System.out.printf("Pass test %d\n", index);
            index++;
        }
    }

    @Test
    @Ignore
    public void findPrefixesTest5() {
        List<FindPrefixesTestCase> findPrefixesTestCases = TestUtils.readTestTxtFileFromResources("preprocess_test_cases.txt");
        FindPrefixesTestCase testCase17 = findPrefixesTestCases.get(17);
        String string = testCase17.getString();
        int[] rightPrefixesTable = testCase17.getPrefixesTable();
        int[] calcPrefixesTable = findPrefixes(string);
        assertThat(calcPrefixesTable).isEqualTo(rightPrefixesTable);
    }
}
