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

package org.apache.celeborn.plugin.flink.protocol;

import java.util.Objects;

/**
 * Comparing {@link ReadData}, this class has an additional field of subpartitionId. This class is
 * added to keep the backward compatibility.
 */
public class SubPartitionReadData extends ReadData {
  private final int subPartitionId;

  public SubPartitionReadData(long streamId, int subPartitionId) {
    super(streamId);
    this.subPartitionId = subPartitionId;
  }

  @Override
  public int encodedLength() {
    return super.encodedLength() + 4;
  }

  // This method will not be called because ReadData won't be created at flink client.
  @Override
  public void encode(io.netty.buffer.ByteBuf buf) {
    super.encode(buf);
    buf.writeInt(subPartitionId);
  }

  public int getSubPartitionId() {
    return subPartitionId;
  }

  @Override
  public Type type() {
    return Type.SUBPARTITION_READ_DATA;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    SubPartitionReadData readData = (SubPartitionReadData) o;
    return streamId == readData.getStreamId()
        && subPartitionId == readData.getSubPartitionId()
        && Objects.equals(flinkBuffer, readData.flinkBuffer);
  }

  @Override
  public int hashCode() {
    return Objects.hash(streamId, subPartitionId, flinkBuffer);
  }

  @Override
  public String toString() {
    return "SubpartitionReadData{"
        + "streamId="
        + streamId
        + ", subPartitionId="
        + subPartitionId
        + ", flinkBuffer="
        + flinkBuffer
        + '}';
  }
}
