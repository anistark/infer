/*
* Copyright (c) 2013- Facebook.
* All rights reserved.
*/

package endtoend.java.eradicate;


import static org.hamcrest.MatcherAssert.assertThat;
import static utils.matchers.ErrorPattern.createPatterns;
import static utils.matchers.ResultContainsExactly.containsExactly;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import utils.InferException;
import utils.InferResults;
import utils.matchers.ErrorPattern;

public class InconsistentSubclassAnnotationTest {

  public static final String SOURCE_FILE =
      "infer/tests/codetoanalyze/java/eradicate/InconsistentSubclassAnnotation.java";

  public static final String ERADICATE_INCONSISTENT_SUBCLASS_RETURN_ANNOTATION =
      "ERADICATE_INCONSISTENT_SUBCLASS_RETURN_ANNOTATION";

  public static final String ERADICATE_INCONSISTENT_SUBCLASS_PARAMETER_ANNOTATION =
      "ERADICATE_INCONSISTENT_SUBCLASS_PARAMETER_ANNOTATION";

  private static InferResults inferResults;

  @BeforeClass
  public static void loadResults() throws InterruptedException, IOException {
    inferResults = InferResults.loadEradicateResults(
        InconsistentSubclassAnnotationTest.class,
        SOURCE_FILE);
  }

  @Test
  public void matchErrors()
      throws IOException, InterruptedException, InferException {


    String[] returnMethods = {"foo", "baz"};
    List<ErrorPattern> errorPatterns = createPatterns(
        ERADICATE_INCONSISTENT_SUBCLASS_RETURN_ANNOTATION,
        SOURCE_FILE,
        returnMethods);

    String[] parameterMethods = {"deref"};
    errorPatterns.addAll(
        createPatterns(
            ERADICATE_INCONSISTENT_SUBCLASS_PARAMETER_ANNOTATION,
            SOURCE_FILE,
            parameterMethods
        )
    );

    assertThat(
        "Results should contain ",
        inferResults,
        containsExactly(errorPatterns)
    );
  }

}
