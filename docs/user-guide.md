## User Guide

### Using FuzzSource
FuzzSource is an [argument source](https://junit.org/junit5/docs/5.3.0/api/org/junit/jupiter/params/provider/ArgumentsSource.html)
as defined in JUnit 5. So please look at the [JUnit 5 documentation ](https://junit.org/junit5/docs/current/user-guide/)
to learn how to use argument sources.

Fuzz source defines the following parameters:

| Parameter | Description |
| --- | --- |
| files | A list of files to be used to provide values to the {@code ParameterizedTest} method. A java Enum is provided for representing the available files. |

Example:
````
@ParameterizedTest(name = "Fuzz testing")
@FuzzSource(files = {BUSINESS_LOGIC_COMMONMETHODNAMES, BUSINESS_LOGIC_DEBUGPARAMS_JSON_FUZZ})
void testWithFuzzUnit(String first, String second) {
	JsonApi api = new JsonApi();
	assertNotNull( api.call(first, second) );
}
````