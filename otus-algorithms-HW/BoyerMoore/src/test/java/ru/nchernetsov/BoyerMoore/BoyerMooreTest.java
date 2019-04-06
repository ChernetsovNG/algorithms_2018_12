package ru.nchernetsov.BoyerMoore;

import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.nchernetsov.BoyerMoore.BoyerMoore.*;

public class BoyerMooreTest {

    @Test
    public void boyerMooreHorspoolAlgTest1() {
        List<BoyerMooreTestCase> boyerMooreTestCases = readTestTsvFileFromResources("string_matching_test_cases.tsv");
        for (BoyerMooreTestCase boyerMooreTestCase : boyerMooreTestCases) {
            String text = boyerMooreTestCase.getText();
            String pattern = boyerMooreTestCase.getPattern();
            List<Integer> rightOccurrencesIndices = boyerMooreTestCase.getOccurrencesIndices();
            List<Integer> calcOccurrencesIndices = boyerMooreHorspoolAlg(text, pattern);
            assertThat(calcOccurrencesIndices).isEqualTo(rightOccurrencesIndices);
        }
    }

    @Test
    public void readTsvFileFromResourcesTest() {
        List<BoyerMooreTestCase> boyerMooreTestCases = readTestTsvFileFromResources("string_matching_test_cases.tsv");
        assertThat(boyerMooreTestCases).hasSize(4776);
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
    public void readTestTxtFileFromResourcesTest() {
        List<FindPrefixesTestCase> findPrefixesTestCases = readTestTxtFileFromResources("preprocess_test_cases.txt");
        assertThat(findPrefixesTestCases).hasSize(21);
    }
}
