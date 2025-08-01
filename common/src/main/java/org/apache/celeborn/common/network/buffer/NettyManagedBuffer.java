/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.celeborn.common.network.buffer;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.Unpooled;

/** A {@link ManagedBuffer} backed by a Netty {@link ByteBuf}. */
public class NettyManagedBuffer extends ManagedBuffer {
  public static NettyManagedBuffer EmptyBuffer = new NettyManagedBuffer(Unpooled.buffer(0, 0));
  private final ByteBuf buf;

  public NettyManagedBuffer(ByteBuf buf) {
    this.buf = buf;
  }

  public NettyManagedBuffer(ByteBuffer buffer) {
    this.buf = Unpooled.wrappedBuffer(buffer);
  }

  public ByteBuf getBuf() {
    return buf.duplicate();
  }

  @Override
  public long size() {
    return buf.readableBytes();
  }

  @Override
  public ByteBuffer nioByteBuffer() throws IOException {
    return buf.nioBuffer();
  }

  @Override
  public InputStream createInputStream() throws IOException {
    return new ByteBufInputStream(buf);
  }

  @Override
  public ManagedBuffer retain() {
    buf.retain();
    return this;
  }

  @Override
  public ManagedBuffer release() {
    buf.release();
    return this;
  }

  @Override
  public Object convertToNetty() throws IOException {
    return buf.duplicate().retain();
  }

  @Override
  public Object convertToNettyForSsl() throws IOException {
    return buf.duplicate().retain();
  }

  @Override
  public String toString() {
    return "NettyManagedBuffer[buf=" + buf + "]";
  }
}
