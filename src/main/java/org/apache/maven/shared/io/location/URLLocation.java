package org.apache.maven.shared.io.location;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.maven.shared.utils.io.FileUtils;

/**
 * The URL Location.
 *
 */
public class URLLocation
    extends FileLocation
{

    private final URL url;

    private final String tempFilePrefix;

    private final String tempFileSuffix;

    private final boolean tempFileDeleteOnExit;

    /**
     * @param url The URL.
     * @param specification The spec.
     * @param tempFilePrefix the prefix.
     * @param tempFileSuffix The suffix.
     * @param tempFileDeleteOnExit delete on exit.
     */
    public URLLocation( URL url, String specification, String tempFilePrefix, String tempFileSuffix,
                        boolean tempFileDeleteOnExit )
    {
        super( specification );

        this.url = url;
        this.tempFilePrefix = tempFilePrefix;
        this.tempFileSuffix = tempFileSuffix;
        this.tempFileDeleteOnExit = tempFileDeleteOnExit;
    }

    /** {@inheritDoc} */
    protected void initFile()
        throws IOException
    {
        // TODO: Log this in the debug log-level...
        if ( unsafeGetFile() == null )
        {
            File tempFile = File.createTempFile( tempFilePrefix, tempFileSuffix );

            if ( tempFileDeleteOnExit )
            {
                tempFile.deleteOnExit();
            }

            FileUtils.copyURLToFile( url, tempFile );

            setFile( tempFile );
        }
    }

}
