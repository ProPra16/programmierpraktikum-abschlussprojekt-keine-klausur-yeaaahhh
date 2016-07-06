package de.hhu.propra16.tddt.trainer;

import de.hhu.propra16.tddt.sourcecode.SourceCode;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.Theories;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import vk.core.api.CompilerResult;
import vk.core.api.TestResult;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by felix on 7/1/16.
 */
@RunWith(Parameterized.class)
public class ConditionCheckerTest {

    private ConditionChecker checker;
    private SourceCode source;


    @Before
    public void setUp() throws Exception {
        checker = new ConditionChecker();
        source = mock(SourceCode.class);
        when(source.hasCompileErrors()).thenReturn(hasCompileErrors);
        when(source.numberOfFailedTests()).thenReturn(numberOfTestFailed);
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Object[][] data = new Object[][]{
                // Phase, hasCompileErrors, numberOfTestsFailed, expected
                {Phase.RED, true, 0, true},
                {Phase.RED, true, 7, true},
                {Phase.RED, false, 1, true},
                {Phase.RED, false, 0, false},
                {Phase.RED, false, 2, false},
                {Phase.RED, false, 3, false},
                {Phase.GREEN, true, 0, false},
                {Phase.GREEN, true, 2, false},
                {Phase.GREEN, false, 1, false},
                {Phase.GREEN, false, 3, false},
                {Phase.GREEN, false, 0, true},
                {Phase.BLACK, true, 0, false},
                {Phase.BLACK, true, 2, false},
                {Phase.BLACK, false, 1, false},
                {Phase.BLACK, false, 3, false},
                {Phase.BLACK, false, 0, true}
        };
        return Arrays.asList(data);
    }

    private Phase phase;
    private boolean hasCompileErrors;
    private int numberOfTestFailed;
    private boolean expected;

    public ConditionCheckerTest(Phase phase, boolean hasCompileErrors, int numberOfTestFailed, boolean expected) {
        this.phase = phase;
        this.hasCompileErrors = hasCompileErrors;
        this.numberOfTestFailed = numberOfTestFailed;
        this.expected = expected;
    }

    @Test
    public void test() {
        assertEquals(expected, checker.check(source, phase));
    }

}