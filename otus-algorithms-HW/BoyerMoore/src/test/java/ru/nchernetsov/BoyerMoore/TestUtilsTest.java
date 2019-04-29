package ru.nchernetsov.BoyerMoore;

import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TestUtilsTest {

    @Test
    public void readTsvFileFromResourcesTest() {
        List<BoyerMooreTestCase> boyerMooreTestCases = TestUtils.readTestTsvFileFromResources("string_matching_test_cases.tsv");
        assertThat(boyerMooreTestCases).hasSize(4776);
    }

    @Test
    public void readTestTxtFileFromResourcesTest() {
        List<FindPrefixesTestCase> findPrefixesTestCases = TestUtils.readTestTxtFileFromResources("preprocess_test_cases.txt");
        assertThat(findPrefixesTestCases).hasSize(21);
    }
}
