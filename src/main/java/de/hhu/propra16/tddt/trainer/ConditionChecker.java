package de.hhu.propra16.tddt.trainer;

import de.hhu.propra16.tddt.sourcecode.SourceCode;

public class ConditionChecker implements CheckCompile {

    @Override
    public boolean check(SourceCode sc, Phase phase) {
        boolean hasCompileErrors = sc.compileCode().hasCompileErrors();
        int numberOfFailedTests = sc.compileTest().getNumberOfFailedTests();
        if (phase == Phase.RED) {
            return (hasCompileErrors || numberOfFailedTests == 1);
        } else {
            return (!hasCompileErrors && numberOfFailedTests == 0);
        }
    }
}
