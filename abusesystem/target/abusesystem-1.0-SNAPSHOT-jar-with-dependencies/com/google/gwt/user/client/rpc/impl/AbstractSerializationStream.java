/*
 * Copyright 2008 Google Inc.
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
package com.google.gwt.user.client.rpc.impl;

/**
 * Base class for the client and server serialization streams. This class
 * handles the basic serialization and deserialization formatting for primitive
 * types since these are common between the client and the server.
 */
public abstract class AbstractSerializationStream {

  /**
   * The default flags to be used by serialization streams.
   */
  public static final int DEFAULT_FLAGS = 0;

  /**
   * The character used to separate fields in client->server RPC messages.
   * 
   * Note that this character is referenced in the following places not using
   * this constant, and they must be changed if this is:
   * <ul>
   * <li>{@link ServerSerializationStreamWriter}.deserializeStringTable
   * <li>{@link ClientSerializationStreamReader}.getQuotingRegex
   * </ul>
   */
  public static final char RPC_SEPARATOR_CHAR = '|';

  /**
   * The current RPC protocol version. This version differs from the previous
   * one in that primitive long values are represented as single-quoted base-64
   * strings with an alphabet of [A-Za-z0-9$_], rather than as pairs of doubles.
   */
  public static final int SERIALIZATION_STREAM_VERSION = 6;
  
  /**
   * The oldest supported RPC protocol version.
   */
  public static final int SERIALIZATION_STREAM_MIN_VERSION = 5;

  /**
   * Indicates that obfuscated type names should be used in the RPC payload.
   */
  public static final int FLAG_ELIDE_TYPE_NAMES = 0x1;

  private int flags = DEFAULT_FLAGS;
  private int version = SERIALIZATION_STREAM_VERSION;

  public final void addFlags(int flags) {
    this.flags |= flags;
  }

  public final int getFlags() {
    return flags;
  }

  public final int getVersion() {
    return version;
  }

  public final boolean hasFlags(int flags) {
    return (getFlags() & flags) == flags;
  }

  public final void setFlags(int flags) {
    this.flags = flags;
  }

  protected final void setVersion(int version) {
    this.version = version;
  }
}
