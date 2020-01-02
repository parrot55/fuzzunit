/**
 * Copyright (C)2020 - Patrick M.J. Roth <red.parrot@bluewin.ch>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.junit.jupiter.params.provider;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.params.provider.MockFuzzingAnnotationBuilder.fuzzingSource;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;

class FuzzingArgumentsProviderTest {

  @Test
  void providesArgumentsSingleFile() {
    FuzzingSource annotation = fuzzingSource().build();

    Stream<Object[]> arguments = provideArguments(new FuzzingArgumentsProvider(), annotation);

    assertThat(arguments).containsExactly(array("foo1"), array("foo2"), array("foo3"));
  }

  @Test
  void providesArgumentsFileTwice() {
    FuzzingSource annotation = fuzzingSource()
        .files(FuzzingFile.JSON_JSON_FUZZING,
            FuzzingFile.JSON_JSON_FUZZING).build();

    Stream<Object[]> arguments = provideArguments(new FuzzingArgumentsProvider(), annotation);

    assertThat(arguments)
        .containsExactly(array("foo1", "foo1"), array("foo2", "foo2"), array("foo3", "foo3"));
  }

  @Test
  void providesArgumentsTwoFiles() {
    FuzzingSource annotation = fuzzingSource()
        .files(FuzzingFile.JSON_JSON_FUZZING,
            FuzzingFile.BUSINESS_LOGIC_COMMONMETHODNAMES).build();

    Stream<Object[]> arguments = provideArguments(new FuzzingArgumentsProvider(), annotation);

    assertThat(arguments)
        .containsExactly(array("foo1", "bar1"), array("foo2", "bar2"),
            array("foo3", ""));
  }

  @Test
  void providesArgumentsTwoFilesWithPadding() {
    FuzzingSource annotation = fuzzingSource()
        .files(FuzzingFile.JSON_JSON_FUZZING, FuzzingFile.BUSINESS_LOGIC_COMMONMETHODNAMES)
        .paddingValues(null, null).build();

    Stream<Object[]> arguments = provideArguments(new FuzzingArgumentsProvider(), annotation);

    assertThat(arguments)
        .containsExactly(array("foo1", "bar1"), array("foo2", "bar2"),
            array("foo3", null));
  }

  @Test
  void providesArgumentsWithNormalFileWithPadding() {
    FuzzingSource annotation = fuzzingSource()
        .files(FuzzingFile.JSON_JSON_FUZZING, FuzzingFile.BUSINESS_LOGIC_COMMONDEBUGPARAMNAMES)
        .paddingValues(null, null).build();

    Stream<Object[]> arguments = provideArguments(new FuzzingArgumentsProvider(), annotation);

    assertThat(arguments)
        .contains(array("foo1", "7357=1"), array("foo2", "7357=true"),
            array("foo3", "7357=y"), array(null, "7357=yes"));
  }

  @ParameterizedTest
  @MethodSource("fuzzingFileStream")
  public void testFuzzingFiles(String fuzzingEnumName) throws IOException {
    FuzzingFile ff = FuzzingFile.valueOf(fuzzingEnumName);
    System.out.println(ff.getFilePath());
    assertThat(ff).isNotNull();
    assertThat(ff.getData().size()).isNotEqualTo(0);
  }

  static Stream<String> fuzzingFileStream() throws IOException {
    System.out.println(Paths.get("build/fuzzDb/attack"));
    return Files.walk(Paths.get("build/fuzzDb/attack"))
        .filter(f -> f.toString().endsWith("txt"))
        .map(p -> extractEnumName(p));
  }

  static String extractEnumName(Path p) {
    String filename = p.toString();
    int beginPos = filename.indexOf("attack" + File.separator) + 7;
    int endPos = filename.length() - 4;
    return filename.substring(beginPos, endPos)
        .replace(File.separator, "_")
        .replace('.', '_')
        .replace('-', '_')
        .toUpperCase();
  }


  private Stream<Object[]> provideArguments(FuzzingFile file, FuzzingSource annotation) {
    FuzzingFile expectedFile = annotation.files()[0];

    FuzzingArgumentsProvider provider = new FuzzingArgumentsProvider();
    return provideArguments(provider, annotation);
  }

  private Stream<Object[]> provideArguments(FuzzingArgumentsProvider provider,
      FuzzingSource annotation) {
    provider.accept(annotation);
    ExtensionContext context = mock(ExtensionContext.class);
    when(context.getTestClass()).thenReturn(Optional.of(FuzzingArgumentsProviderTest.class));
    doCallRealMethod().when(context).getRequiredTestClass();
    return provider.provideArguments(context).map(Arguments::get);
  }

  @SuppressWarnings("unchecked")
  private static <T> T[] array(T... elements) {
    return elements;
  }

}
