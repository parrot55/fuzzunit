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

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.support.AnnotationConsumer;

/**
 * @since 5.0
 */
class FuzzingArgumentsProvider implements ArgumentsProvider, AnnotationConsumer<FuzzingSource> {

  private FuzzingSource annotation;
  private FuzzingFile[] files;
  private List<List<String>> lineList;
  private String[] paddingValues;

  FuzzingArgumentsProvider() {
  }

  @Override
  public void accept(FuzzingSource annotation) {
    this.annotation = annotation;
    this.files = annotation.files();
    this.paddingValues = getPaddingValues(annotation.paddingValues());
  }

  @Override
  public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
    // @formatter:off
    FuzzingFile longerFile = Arrays.stream(files)
        .max(Comparator.comparing(FuzzingFile::size)).get();
    int maxSize = longerFile.size();

    lineList = Arrays.stream(files).map(FuzzingFile::getData)
        .collect(Collectors.toList());

    return IntStream.range(0, maxSize).mapToObj(i -> provideObjectArray(i))
        .map(Arguments::of);
    // @formatter:on
  }

  private String[] provideObjectArray(int i) {
    String[] stringArgs = new String[lineList.size()];
    int j = 0;
    for (List<String> positions : lineList) {
      if (i < positions.size()) {
        stringArgs[j++] = positions.get(i);
      } else {
        stringArgs[j] = paddingValues[j];
        j++;
      }
    }
    return stringArgs;
  }

  private String[] getPaddingValues(String[] pv) {
    if (pv != null) {
      return pv;
    }
    String[] defaultPV = new String[this.files.length];
    Arrays.fill(defaultPV, "");
    return defaultPV;
  }

}
