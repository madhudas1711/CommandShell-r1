/*
 * Copyright © 2017 Cask Data, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package org.example.plugin;

import io.cdap.cdap.etl.mock.action.MockActionContext;
import io.cdap.cdap.etl.mock.common.MockPipelineConfigurer;
import org.junit.Test;

/**
 * Example unit tests for {@link ExampleAction}. You can test your configuration logic and run logic here
 */
public class ExampleActionTest {

  // This is an example of how to test that your validate function is throwing the right exception.
  @Test(expected = IllegalArgumentException.class)
  public void testExamplePluginValidate() throws Exception {
    ExampleAction.ExampleActionConfig config = new ExampleAction.ExampleActionConfig("test","test","test");
    MockPipelineConfigurer configurer = new MockPipelineConfigurer(null);
    new ExampleAction(config).configurePipeline(configurer);
    new ExampleAction(config).run(new MockActionContext());
  }
}
