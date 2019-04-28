package ru.nchernetsov.KnuthMorrisPratt;

import org.junit.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class KnuthMorrisPrattTest {

    @Test
    public void knuthMorrisPrattStateMachineTest1() {
        char[] alphabet = {'A', 'C', 'G', 'T'};
        String pattern = "GAGAGTT";

        StringStateMachine stringStateMachine = new StringStateMachine(alphabet, pattern);

        assertThat(stringStateMachine.getPattern()).isEqualTo("GAGAGTT");
        assertThat(stringStateMachine.getQ()).hasSize(8);
        assertThat(stringStateMachine.getQ()).containsExactly(0, 1, 2, 3, 4, 5, 6, 7);
        assertThat(stringStateMachine.getSigma()).hasSize(4);
        assertThat(stringStateMachine.getSigma()).containsExactly('A', 'C', 'G', 'T');
        assertThat(stringStateMachine.get_q()).isEqualTo(0);

        // Проверяем таблицу переходов
        Map<Integer, Map<Character, Integer>> delta = stringStateMachine.getDelta();
        Map<Character, Integer> delta0 = delta.get(0);
        Map<Character, Integer> delta1 = delta.get(1);
        Map<Character, Integer> delta2 = delta.get(2);
        Map<Character, Integer> delta3 = delta.get(3);
        Map<Character, Integer> delta4 = delta.get(4);
        Map<Character, Integer> delta5 = delta.get(5);
        Map<Character, Integer> delta6 = delta.get(6);
        Map<Character, Integer> delta7 = delta.get(7);

        assertThat(delta0.get('A')).isEqualTo(0);
        assertThat(delta0.get('C')).isEqualTo(0);
        assertThat(delta0.get('G')).isEqualTo(1);
        assertThat(delta0.get('T')).isEqualTo(0);

        assertThat(delta1.get('A')).isEqualTo(2);
        assertThat(delta1.get('C')).isEqualTo(0);
        assertThat(delta1.get('G')).isEqualTo(1);
        assertThat(delta1.get('T')).isEqualTo(0);

        assertThat(delta2.get('A')).isEqualTo(0);
        assertThat(delta2.get('C')).isEqualTo(0);
        assertThat(delta2.get('G')).isEqualTo(3);
        assertThat(delta2.get('T')).isEqualTo(0);

        assertThat(delta3.get('A')).isEqualTo(4);
        assertThat(delta3.get('C')).isEqualTo(0);
        assertThat(delta3.get('G')).isEqualTo(1);
        assertThat(delta3.get('T')).isEqualTo(0);

        assertThat(delta4.get('A')).isEqualTo(0);
        assertThat(delta4.get('C')).isEqualTo(0);
        assertThat(delta4.get('G')).isEqualTo(5);
        assertThat(delta4.get('T')).isEqualTo(0);

        assertThat(delta5.get('A')).isEqualTo(4);
        assertThat(delta5.get('C')).isEqualTo(0);
        assertThat(delta5.get('G')).isEqualTo(1);
        assertThat(delta5.get('T')).isEqualTo(6);

        assertThat(delta6.get('A')).isEqualTo(0);
        assertThat(delta6.get('C')).isEqualTo(0);
        assertThat(delta6.get('G')).isEqualTo(1);
        assertThat(delta6.get('T')).isEqualTo(7);

        assertThat(delta7.get('A')).isEqualTo(0);
        assertThat(delta7.get('C')).isEqualTo(0);
        assertThat(delta7.get('G')).isEqualTo(1);
        assertThat(delta7.get('T')).isEqualTo(0);
    }

    @Test
    public void suffixFunctionSigmaTest1() {
        StringStateMachine stateMachine = new StringStateMachine(new char[]{'a', 'b'}, "abaab");
        int sigma = stateMachine.suffixFunction("aba");
        assertThat(sigma).isEqualTo(3);
    }

    @Test
    public void suffixFunctionSigmaTest2() {
        StringStateMachine stateMachine = new StringStateMachine(new char[]{'a', 'b'}, "abaab");
        int sigma = stateMachine.suffixFunction("bb");
        assertThat(sigma).isEqualTo(0);
    }

    @Test
    public void suffixFunctionSigmaTest3() {
        StringStateMachine stateMachine = new StringStateMachine(new char[]{'a', 'b'}, "abaab");
        int sigma = stateMachine.suffixFunction("aaab");
        assertThat(sigma).isEqualTo(2);
    }

    @Test
    public void suffixFunctionSigmaTest4() {
        StringStateMachine stateMachine = new StringStateMachine(new char[]{'A', 'C', 'G', 'T'}, "GAGAGTT");
        int sigma = stateMachine.suffixFunction("G");
        assertThat(sigma).isEqualTo(1);
    }
}
