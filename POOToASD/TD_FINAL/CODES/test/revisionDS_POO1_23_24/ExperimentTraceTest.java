package revisionDS_POO1_23_24;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExperimentTraceTest {

    ExperimentTrace experimentTrace = new ExperimentTrace();
    Result result1_11 = new WaterResult(11);
    Result result2_0 = new WaterResult(0);
    Result result3_101 = new WaterResult(101);
    Result result4_11 = new WaterResult(11);
    Result result5_0 = new WaterResult(0);
    Result result6_0 = new WaterResult(0);
    @BeforeEach
    void setUp() {
        experimentTrace = new ExperimentTrace();
    }

    void initExperimentTrace() throws ExperimentTraceException {
        experimentTrace = new ExperimentTrace();
        experimentTrace.addExperimentResult(result1_11);
        experimentTrace.addExperimentResult(result2_0);
        experimentTrace.addExperimentResult(result3_101);
        experimentTrace.addExperimentResult(result4_11);
        experimentTrace.addExperimentResult(result5_0);
        experimentTrace.addExperimentResult(result6_0);
    }

    @Test
    void testCopy() throws ExperimentTraceException {
        ExperimentTrace trace = new ExperimentTrace();
        trace.addExperimentResult(result1_11);
        trace.addExperimentResult(result3_101);
        trace.addExperimentResult(result2_0);
        List<Result> res = trace.getResultCopy();
        assertEquals(3, res.size());
        assertEquals(result2_0, res.get(0));
        assertEquals(result3_101, res.get(1));
        assertEquals(result1_11, res.get(2));
        assertEquals(result2_0, trace.peekLastResult());
    }

    @Test
    void addOneExperimentResult() throws ExperimentTraceException {
        experimentTrace.addExperimentResult(result1_11);
        assertEquals(result1_11, experimentTrace.peekLastResult());
        List<Result> res = experimentTrace.getExperimentResults();
        assertEquals(1, res.size());
        assertEquals(result1_11, res.get(0));
        assertTrue(experimentTrace.getExperimentResults().isEmpty());
    }

    @Test
    void addExperimentResults() throws ExperimentTraceException {
        experimentTrace.addExperimentResult(result1_11);
        experimentTrace.addExperimentResult(result3_101);
        experimentTrace.addExperimentResult(result2_0);
        assertEquals(result2_0, experimentTrace.peekLastResult());
        List<Result> res = experimentTrace.getExperimentResults();
        assertEquals(3, res.size());
        assertEquals(result2_0, res.get(0));
        assertEquals(result3_101, res.get(1));
        assertEquals(result1_11, res.get(2));
    }


    @Test
    void removeEqualResultsInTheMiddle() throws ExperimentTraceException {
        initExperimentTrace();
        List<Result> res = experimentTrace.getExperimentResults();
        assertEquals(6, res.size());

        initExperimentTrace();
        experimentTrace.removeMatchingResults(result1_11);
        assertEquals(result6_0, experimentTrace.peekLastResult());
        res = experimentTrace.getExperimentResults();
        assertEquals(4, res.size());
    }
    @Test
    void removeEqualResultsToTheTop() throws ExperimentTraceException {
        initExperimentTrace();
        experimentTrace.removeMatchingResults(result2_0);
        assertEquals(result4_11, experimentTrace.peekLastResult());

        List<Result> res = experimentTrace.getExperimentResults();
        assertEquals(3, res.size());
    }


    @Test
    void testRemoveDuplicateResults() throws ExperimentTraceException {
        initExperimentTrace();
        experimentTrace.removeDuplicatedResults();
        List<Result> res = experimentTrace.getResultCopy();
        assertEquals(3, res.size());
        assertEquals(result6_0, experimentTrace.peekLastResult());
        assertEquals(result6_0, res.get(0));
        assertEquals(result4_11, res.get(1));
        assertEquals(result3_101, res.get(2));
    }
}