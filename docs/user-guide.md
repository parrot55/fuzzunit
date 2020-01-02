## User Guide

### Using the Fuzzing Source
FuzzingSource is a [value source]() as defined in JUnit 5. So please look at the JUnit documentation
to learn how to use value sources.

Fuzzing source defines the following parameters:
| Parameter | Description |
| --- | --- |
| files | 

Example:
````
@ParameterizedTest(name = "Fuzz testing")
@FuzzingSource(files = {BUSINESS_LOGIC_COMMONMETHODNAMES, BUSINESS_LOGIC_COMMONMETHODNAMES})
void testWithFuzzUnitException(String second, String expectedResult) {
	JsonParser parser = new JsonParser();
	assertEquals(expectedResult, parser.concatenateWithException("", second),
				() -> "''" + " + " + second + " should equal " + expectedResult);
}
````