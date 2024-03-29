package org.hsc.novelSpider.bundleplugin;


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

import org.apache.maven.plugin.testing.stubs.ArtifactStub;
import org.apache.maven.plugin.testing.stubs.MavenProjectStub;
import org.codehaus.plexus.PlexusTestCase;


/**
 * Common methods for bundle plugin testing
 * 
 * @author <a href="mailto:carlos@apache.org">Carlos Sanchez</a>
 * @version $Id: AbstractBundlePluginTest.java 1189376 2011-10-26 18:50:20Z mcculls $
 */
public abstract class AbstractBundlePluginTest extends PlexusTestCase
{

    protected MavenProjectStub getMavenProjectStub()
    {
        MavenProjectStub project = new MavenProjectStub();
        project.setGroupId( "group" );
        project.setArtifactId( "project" );
        project.setVersion( "1.2.3.4" );
        return project;
    }


    protected ArtifactStub getArtifactStub()
    {
        ArtifactStub artifact = new ArtifactStub();
        artifact.setGroupId( "group" );
        artifact.setArtifactId( "artifact" );
        artifact.setVersion( "1.0" );
        return artifact;
    }


    protected File getTestBundle()
    {
        String osgiBundleFileName = "org.apache.maven.maven-model_2.1.0.SNAPSHOT.jar";
        return getTestFile( getBasedir(), "src/test/resources/" + osgiBundleFileName );
    }

}