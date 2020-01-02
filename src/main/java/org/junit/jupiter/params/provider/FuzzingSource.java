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

import static org.apiguardian.api.API.Status.EXPERIMENTAL;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.apiguardian.api.API;

/**
 * {@code @FuzzingSource} is an {@link ArgumentsSource} which is used to load values from one
 * FuzzDB file.
 *
 * <p>The lines of these FuzzDB files will be provided as arguments to the
 * annotated {@code @ParameterizedTest} method. Any line beginning with a {@code #} symbol will be
 * interpreted as a comment and will be ignored.
 *
 * @see org.junit.jupiter.params.provider.ArgumentsSource
 * @see org.junit.jupiter.params.ParameterizedTest
 * @since 5.0
 */
@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@API(status = EXPERIMENTAL, since = "5.5")
@ArgumentsSource(FuzzingArgumentsProvider.class)
public @interface FuzzingSource {

  /**
   * The fuzzing files to use as the sources of arguments; must not be empty.
   */
  FuzzingFile[] files();

  /**
   * A list of strings that should be used to pad if the values provided by a {@code FuzzingDomain}
   * doesn't have the same length as the other one.
   *
   * <p>For example, if you specify a FUZZING_JSON (which has 89 lines) as the first source, and
   * FUZZING_SQL (132 lines) as the second one, then the arguments passed as the first parameter
   * will be the values defined in FUZZING_JSON up to 89, and the {@code paddingValue} from the 90th
   * line.
   *
   * <p>If {@code paddingValues} are left empty, then the last entry of the shortest {@code
   * FuzzingDomain} will be used for padding.
   *
   * @since 5.6
   */
  @API(status = EXPERIMENTAL, since = "5.5")
  String[] paddingValues() default {};

}
