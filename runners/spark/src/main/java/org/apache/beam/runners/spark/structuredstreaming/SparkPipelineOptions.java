/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.beam.runners.spark.structuredstreaming;

import java.util.List;
import org.apache.beam.sdk.options.ApplicationNameOptions;
import org.apache.beam.sdk.options.Default;
import org.apache.beam.sdk.options.Description;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.StreamingOptions;

/**
 * Spark runner {@link PipelineOptions} handles Spark execution-related configurations, such as the
 * master address, and other user-related knobs.
 */
public interface SparkPipelineOptions
    extends PipelineOptions, StreamingOptions, ApplicationNameOptions {

  @Description("The url of the spark master to connect to, (e.g. spark://host:port, local[4]).")
  @Default.String("local[4]")
  String getSparkMaster();

  void setSparkMaster(String master);

  @Description("Batch default storage level")
  @Default.String("MEMORY_ONLY")
  String getStorageLevel();

  void setStorageLevel(String storageLevel);

  @Description(
      "A checkpoint directory for streaming resilience, ignored in batch. "
          + "For durability, a reliable filesystem such as HDFS/S3/GS is necessary.")
  String getCheckpointDir();

  void setCheckpointDir(String checkpointDir);

  @Description(
      "The period to checkpoint (in Millis). If not set, Spark will default "
          + "to Max(slideDuration, Seconds(10)). This PipelineOptions default (-1) will end-up "
          + "with the described Spark default.")
  @Default.Long(-1)
  Long getCheckpointDurationMillis();

  void setCheckpointDurationMillis(Long durationMillis);

  @Description("Enable/disable sending aggregator values to Spark's metric sinks")
  @Default.Boolean(true)
  Boolean getEnableSparkMetricSinks();

  void setEnableSparkMetricSinks(Boolean enableSparkMetricSinks);

  /**
   * List of local files to make available to workers.
   *
   * <p>Jars are placed on the worker's classpath.
   *
   * <p>The default value is the list of jars from the main program's classpath.
   */
  @Description(
      "Jar-Files to send to all workers and put on the classpath. "
          + "The default value is all files from the classpath.")
  List<String> getFilesToStage();

  void setFilesToStage(List<String> value);
}