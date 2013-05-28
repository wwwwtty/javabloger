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
package org.hsc.novelSpider.bundleplugin;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import org.apache.maven.archiver.ManifestSection;
import org.apache.maven.archiver.MavenArchiveConfiguration;
import org.apache.maven.archiver.MavenArchiver;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.handler.manager.ArtifactHandlerManager;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.model.License;
import org.apache.maven.model.Model;
import org.apache.maven.model.Resource;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.project.MavenProject;
import org.apache.maven.project.MavenProjectHelper;
import org.codehaus.plexus.archiver.UnArchiver;
import org.codehaus.plexus.archiver.manager.ArchiverManager;
import org.codehaus.plexus.util.DirectoryScanner;
import org.codehaus.plexus.util.FileUtils;
import org.codehaus.plexus.util.StringUtils;
import org.hsc.maven.shared.osgi.DefaultMaven2OsgiConverter;
import org.hsc.maven.shared.osgi.Maven2OsgiConverter;

import aQute.lib.osgi.Analyzer;
import aQute.lib.osgi.Builder;
import aQute.lib.osgi.Constants;
import aQute.lib.osgi.EmbeddedResource;
import aQute.lib.osgi.FileResource;
import aQute.lib.osgi.Jar;
import aQute.lib.osgi.Processor;
import aQute.lib.spring.SpringXMLType;


/**
 * Create an OSGi bundle from Maven project
 *
 * @goal bundle
 * @phase package
 * @requiresDependencyResolution test
 * @description build an OSGi bundle jar
 * @threadSafe
 */
public class BundlePlugin extends AbstractMojo
{
    /**
     * Directory where the manifest will be written
     * @parameter expression="${manifestLocation}" default-value="${project.build.outputDirectory}/META-INF"
     */
    protected File manifestLocation;

    /**
     * File where the BND instructions will be dumped
     * @parameter expression="${dumpInstructions}"
     */
    protected File dumpInstructions;

    /**
     * File where the BND class-path will be dumped
     * @parameter expression="${dumpClasspath}"
     */
    protected File dumpClasspath;

    /**
     * When true, unpack the bundle contents to the outputDirectory
     * @parameter expression="${unpackBundle}"
     */
    protected boolean unpackBundle;

    /**
     * Comma separated list of artifactIds to exclude from the dependency classpath passed to BND (use "true" to exclude everything)
     * @parameter expression="${excludeDependencies}"
     */
    protected String excludeDependencies;

    /**
     * Classifier type of the bundle to be installed.  For example, "jdk14".
     * Defaults to none which means this is the project's main bundle.
     *
     * @parameter
     */
    protected String classifier;

    /**
     * @component
     */
    private MavenProjectHelper m_projectHelper;

    /**
     * @component
     */
    private ArchiverManager m_archiverManager;

    /**
     * @component
     */
    private ArtifactHandlerManager m_artifactHandlerManager;

    /**
     * Project types which this plugin supports.
     * @parameter
     */
    protected List<String> supportedProjectTypes = Arrays.asList( new String[]
        { "jar", "bundle" } );

    /**
     * The directory for the generated bundles.
     * @parameter expression="${project.build.outputDirectory}"
     * @required
     */
    private File outputDirectory;

    /**
     * The directory for the generated JAR.
     * @parameter expression="${project.build.directory}"
     * @required
     */
    private String buildDirectory;

    /**
     * The Maven project.
     *
     * @parameter expression="${project}"
     * @required
     * @readonly
     */
    private MavenProject project;

    /**
     * The BND instructions for the bundle.
     *
     * @parameter
     */
    private Map instructions = new LinkedHashMap();

    /**
     * Use locally patched version for now.
     */
    private Maven2OsgiConverter m_maven2OsgiConverter = new DefaultMaven2OsgiConverter();

    /**
     * The archive configuration to use.
     *
     * @parameter
     */
    private MavenArchiveConfiguration archive; // accessed indirectly in JarPluginConfiguration

    /**
     * @parameter default-value="${session}"
     * @required
     * @readonly
     */
    private MavenSession m_mavenSession;

    private static final String MAVEN_SYMBOLICNAME = "maven-symbolicname";
    private static final String MAVEN_RESOURCES = "{maven-resources}";
    private static final String LOCAL_PACKAGES = "{local-packages}";
    private static final String MAVEN_SOURCES = "{maven-sources}";

    private static final String[] EMPTY_STRING_ARRAY =
        {};
    private static final String[] DEFAULT_INCLUDES =
        { "**/**" };

    private static final String NL = System.getProperty( "line.separator" );


    protected Maven2OsgiConverter getMaven2OsgiConverter()
    {
        return m_maven2OsgiConverter;
    }


    protected void setMaven2OsgiConverter( Maven2OsgiConverter maven2OsgiConverter )
    {
        m_maven2OsgiConverter = maven2OsgiConverter;
    }


    protected MavenProject getProject()
    {
        return project;
    }


    /**
     * @see org.apache.maven.plugin.AbstractMojo#execute()
     */
    public void execute() throws MojoExecutionException
    {
        Properties properties = new Properties();
        String projectType = getProject().getArtifact().getType();

        // ignore unsupported project types, useful when bundleplugin is configured in parent pom
        if ( !supportedProjectTypes.contains( projectType ) )
        {
            getLog().warn("Ignoring project type " + projectType + " - supportedProjectTypes = " + supportedProjectTypes );
            return;
        }
        
        MavenProject currentProject=getProject();
        try {
        	execute( currentProject, instructions, properties, getClasspath(currentProject) );
        }
        catch ( IOException e ){
            throw new MojoExecutionException( "Error calculating classpath for project " + currentProject, e );
        }
    }


    /* transform directives from their XML form to the expected BND syntax (eg. _include becomes -include) */
    protected static Map transformDirectives( Map originalInstructions )
    {
        Map transformedInstructions = new LinkedHashMap();
        for ( Iterator i = originalInstructions.entrySet().iterator(); i.hasNext(); )
        {
            Map.Entry e = ( Map.Entry ) i.next();

            String key = ( String ) e.getKey();
            if ( key.startsWith( "_" ) )
            {
                key = "-" + key.substring( 1 );
            }

            String value = ( String ) e.getValue();
            if ( null == value )
            {
                value = "";
            }
            else
            {
                value = value.replaceAll( "\\p{Blank}*[\r\n]\\p{Blank}*", "" );
            }

            if ( Analyzer.WAB.equals( key ) && value.length() == 0 )
            {
                // provide useful default
                value = "src/main/webapp/";
            }

            transformedInstructions.put( key, value );
        }
        return transformedInstructions;
    }


    protected boolean reportErrors( String prefix, Analyzer analyzer )
    {
        List<String> errors = analyzer.getErrors();
        List<String> warnings = analyzer.getWarnings();

        for ( Iterator<String> w = warnings.iterator(); w.hasNext(); )
        {
            String msg =  w.next();
            getLog().warn( prefix + " : " + msg );
        }

        boolean hasErrors = false;
        String fileNotFound = "Input file does not exist: ";
        for ( Iterator<String> e = errors.iterator(); e.hasNext(); )
        {
            String msg = e.next();
            if ( msg.startsWith( fileNotFound ) && msg.endsWith( "~" ) )
            {
                // treat as warning; this error happens when you have duplicate entries in Include-Resource
                String duplicate = Processor.removeDuplicateMarker( msg.substring( fileNotFound.length() ) );
                getLog().warn( prefix + " : Duplicate path '" + duplicate + "' in Include-Resource" );
            }
            else
            {
                getLog().error( prefix + " : " + msg );
                hasErrors = true;
            }
        }
        return hasErrors;
    }


    protected void execute( MavenProject currentProject, Map originalInstructions, Properties properties,
        Jar[] classpath ) throws MojoExecutionException
    {
        try
        {
            File jarFile = new File( getBuildDirectory(), getBundleName( currentProject ) );
            Builder builder = buildOSGiBundle( currentProject, originalInstructions, properties, classpath );
            boolean hasErrors = reportErrors( "Bundle " + currentProject.getArtifact(), builder );
            if ( hasErrors )
            {
                String failok = builder.getProperty( "-failok" );
                if ( null == failok || "false".equalsIgnoreCase( failok ) )
                {
                    jarFile.delete();

                    throw new MojoFailureException( "Error(s) found in bundle configuration" );
                }
            }

            // attach bundle to maven project
            jarFile.getParentFile().mkdirs();
            builder.getJar().write( jarFile );

            Artifact mainArtifact = currentProject.getArtifact();

            if ( "bundle".equals( mainArtifact.getType() ) )
            {
                // workaround for MNG-1682: force maven to install artifact using the "jar" handler
                mainArtifact.setArtifactHandler( m_artifactHandlerManager.getArtifactHandler( "jar" ) );
            }

            if ( null == classifier || classifier.trim().length() == 0 )
            {
                mainArtifact.setFile( jarFile );
            }
            else
            {
                m_projectHelper.attachArtifact( currentProject, jarFile, classifier );
            }

            if ( unpackBundle )
            {
                unpackBundle( jarFile );
            }

            if ( manifestLocation != null )
            {
                File outputFile = new File( manifestLocation, "MANIFEST.MF" );

                try
                {
                    Manifest manifest = builder.getJar().getManifest();
                    ManifestPlugin.writeManifest( manifest, outputFile );
                }
                catch ( IOException e )
                {
                    getLog().error( "Error trying to write Manifest to file " + outputFile, e );
                }
            }

            // cleanup...
            builder.close();
        }
        catch ( MojoFailureException e )
        {
            getLog().error( e.getLocalizedMessage() );
            throw new MojoExecutionException( "Error(s) found in bundle configuration", e );
        }
        catch ( Exception e )
        {
            getLog().error( "An internal error occurred", e );
            throw new MojoExecutionException( "Internal error in maven-bundle-plugin", e );
        }
    }


    protected Builder getOSGiBuilder( MavenProject currentProject, Map originalInstructions, Properties properties,
        Jar[] classpath ) throws Exception
    {
        properties.putAll( getDefaultProperties( currentProject ) );
        properties.putAll( transformDirectives( originalInstructions ) );

        Builder builder = new Builder();
        builder.setBase( getBase( currentProject ) );
        builder.setProperties( sanitize( properties ) );
        if ( classpath != null )
        {
            builder.setClasspath( classpath );
        }

        return builder;
    }


    protected static Properties sanitize( Properties properties )
    {
        // convert any non-String keys/values to Strings
        Properties sanitizedEntries = new Properties();
        for ( Iterator itr = properties.entrySet().iterator(); itr.hasNext(); )
        {
            Map.Entry entry = ( Map.Entry ) itr.next();
            if ( entry.getKey() instanceof String == false )
            {
                String key = sanitize( entry.getKey() );
                if ( !properties.containsKey( key ) )
                {
                    sanitizedEntries.setProperty( key, sanitize( entry.getValue() ) );
                }
                itr.remove();
            }
            else if ( entry.getValue() instanceof String == false )
            {
                entry.setValue( sanitize( entry.getValue() ) );
            }
        }
        properties.putAll( sanitizedEntries );
        return properties;
    }


    protected static String sanitize( Object value )
    {
        if ( value instanceof String )
        {
            return ( String ) value;
        }
        else if ( value instanceof Iterable )
        {
            String delim = "";
            StringBuilder buf = new StringBuilder();
            for ( Object i : ( Iterable<?> ) value )
            {
                buf.append( delim ).append( i );
                delim = ", ";
            }
            return buf.toString();
        }
        else if ( value.getClass().isArray() )
        {
            String delim = "";
            StringBuilder buf = new StringBuilder();
            for ( int i = 0, len = Array.getLength( value ); i < len; i++ )
            {
                buf.append( delim ).append( Array.get( value, i ) );
                delim = ", ";
            }
            return buf.toString();
        }
        else
        {
            return String.valueOf( value );
        }
    }


    protected void addMavenInstructions( MavenProject currentProject, Builder builder ) throws Exception
    {
        if ( currentProject.getBasedir() != null )
        {
            // update BND instructions to add included Maven resources
            includeMavenResources( currentProject, builder, getLog() );

            // calculate default export/private settings based on sources
            addLocalPackages( outputDirectory, builder );

            // tell BND where the current project source resides
            addMavenSourcePath( currentProject, builder, getLog() );
        }

        // update BND instructions to embed selected Maven dependencies
        Collection embeddableArtifacts = getEmbeddableArtifacts( currentProject, builder );
        new DependencyEmbedder( getLog(), embeddableArtifacts ).processHeaders( builder );

        if ( dumpInstructions != null || getLog().isDebugEnabled() )
        {
            StringBuilder buf = new StringBuilder();
            getLog().debug( "BND Instructions:" + NL + dumpInstructions( builder.getProperties(), buf ) );
            if ( dumpInstructions != null )
            {
                getLog().info( "Writing BND instructions to " + dumpInstructions );
                dumpInstructions.getParentFile().mkdirs();
                FileUtils.fileWrite( dumpInstructions, "# BND instructions" + NL + buf );
            }
        }

        if ( dumpClasspath != null || getLog().isDebugEnabled() )
        {
            StringBuilder buf = new StringBuilder();
            getLog().debug( "BND Classpath:" + NL + dumpClasspath( builder.getClasspath(), buf ) );
            if ( dumpClasspath != null )
            {
                getLog().info( "Writing BND classpath to " + dumpClasspath );
                dumpClasspath.getParentFile().mkdirs();
                FileUtils.fileWrite( dumpClasspath, "# BND classpath" + NL + buf );
            }
        }
    }


    protected Builder buildOSGiBundle( MavenProject currentProject, Map originalInstructions, Properties properties,
        Jar[] classpath ) throws Exception
    {
        Builder builder = getOSGiBuilder( currentProject, originalInstructions, properties, classpath );

        addMavenInstructions( currentProject, builder );

        builder.build();

        mergeMavenManifest( currentProject, builder );

        return builder;
    }


    protected static StringBuilder dumpInstructions( Properties properties, StringBuilder buf )
    {
        try
        {
            buf.append( "#-----------------------------------------------------------------------" + NL );
            Properties stringProperties = new Properties();
            for ( Enumeration e = properties.propertyNames(); e.hasMoreElements(); )
            {
                // we can only store String properties
                String key = ( String ) e.nextElement();
                String value = properties.getProperty( key );
                if ( value != null )
                {
                    stringProperties.setProperty( key, value );
                }
            }
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            stringProperties.store( out, null ); // properties encoding is 8859_1
            buf.append( out.toString( "8859_1" ) );
            buf.append( "#-----------------------------------------------------------------------" + NL );
        }
        catch ( Throwable e )
        {
            // ignore...
        }
        return buf;
    }


    protected static StringBuilder dumpClasspath( List classpath, StringBuilder buf )
    {
        try
        {
            buf.append( "#-----------------------------------------------------------------------" + NL );
            buf.append( "-classpath:\\" + NL );
            for ( Iterator i = classpath.iterator(); i.hasNext(); )
            {
                File path = ( ( Jar ) i.next() ).getSource();
                if ( path != null )
                {
                    buf.append( ' ' + path.toString() + ( i.hasNext() ? ",\\" : "" ) + NL );
                }
            }
            buf.append( "#-----------------------------------------------------------------------" + NL );
        }
        catch ( Throwable e )
        {
            // ignore...
        }
        return buf;
    }


    protected static StringBuilder dumpManifest( Manifest manifest, StringBuilder buf )
    {
        try
        {
            buf.append( "#-----------------------------------------------------------------------" + NL );
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            Jar.writeManifest( manifest, out ); // manifest encoding is UTF8
            buf.append( out.toString( "UTF8" ) );
            buf.append( "#-----------------------------------------------------------------------" + NL );
        }
        catch ( Throwable e )
        {
            // ignore...
        }
        return buf;
    }


    protected static void includeMavenResources( MavenProject currentProject, Analyzer analyzer, Log log )
    {
        // pass maven resource paths onto BND analyzer
        final String mavenResourcePaths = getMavenResourcePaths( currentProject );
        final String includeResource = ( String ) analyzer.getProperty( Analyzer.INCLUDE_RESOURCE );
        if ( includeResource != null )
        {
            if ( includeResource.indexOf( MAVEN_RESOURCES ) >= 0 )
            {
                // if there is no maven resource path, we do a special treatment and replace
                // every occurance of MAVEN_RESOURCES and a following comma with an empty string
                if ( mavenResourcePaths.length() == 0 )
                {
                    String cleanedResource = removeTagFromInstruction( includeResource, MAVEN_RESOURCES );
                    if ( cleanedResource.length() > 0 )
                    {
                        analyzer.setProperty( Analyzer.INCLUDE_RESOURCE, cleanedResource );
                    }
                    else
                    {
                        analyzer.unsetProperty( Analyzer.INCLUDE_RESOURCE );
                    }
                }
                else
                {
                    String combinedResource = StringUtils
                        .replace( includeResource, MAVEN_RESOURCES, mavenResourcePaths );
                    analyzer.setProperty( Analyzer.INCLUDE_RESOURCE, combinedResource );
                }
            }
            else if ( mavenResourcePaths.length() > 0 )
            {
                log.warn( Analyzer.INCLUDE_RESOURCE + ": overriding " + mavenResourcePaths + " with " + includeResource
                    + " (add " + MAVEN_RESOURCES + " if you want to include the maven resources)" );
            }
        }
        else if ( mavenResourcePaths.length() > 0 )
        {
            analyzer.setProperty( Analyzer.INCLUDE_RESOURCE, mavenResourcePaths );
        }
    }


    protected void mergeMavenManifest( MavenProject currentProject, Builder builder ) throws Exception
    {
        Jar jar = builder.getJar();

        if ( getLog().isDebugEnabled() )
        {
            getLog().debug( "BND Manifest:" + NL + dumpManifest( jar.getManifest(), new StringBuilder() ) );
        }

        boolean addMavenDescriptor = currentProject.getBasedir() != null;

        try
        {
            /*
             * Grab customized manifest entries from the maven-jar-plugin configuration
             */
            MavenArchiveConfiguration archiveConfig = JarPluginConfiguration.getArchiveConfiguration( currentProject );
            String mavenManifestText = new MavenArchiver().getManifest( currentProject, archiveConfig ).toString();
            addMavenDescriptor = addMavenDescriptor && archiveConfig.isAddMavenDescriptor();

            Manifest mavenManifest = new Manifest();

            // First grab the external manifest file (if specified and different to target location)
            File externalManifestFile = archiveConfig.getManifestFile();
            if ( null != externalManifestFile && externalManifestFile.exists()
                && !externalManifestFile.equals( new File( manifestLocation, "MANIFEST.MF" ) ) )
            {
                InputStream mis = new FileInputStream( externalManifestFile );
                mavenManifest.read( mis );
                mis.close();
            }

            // Then apply customized entries from the jar plugin; note: manifest encoding is UTF8
            mavenManifest.read( new ByteArrayInputStream( mavenManifestText.getBytes( "UTF8" ) ) );

            if ( !archiveConfig.isManifestSectionsEmpty() )
            {
                /*
                 * Add customized manifest sections (for some reason MavenArchiver doesn't do this for us)
                 */
                List sections = archiveConfig.getManifestSections();
                for ( Iterator i = sections.iterator(); i.hasNext(); )
                {
                    ManifestSection section = ( ManifestSection ) i.next();
                    Attributes attributes = new Attributes();

                    if ( !section.isManifestEntriesEmpty() )
                    {
                        Map entries = section.getManifestEntries();
                        for ( Iterator j = entries.entrySet().iterator(); j.hasNext(); )
                        {
                            Map.Entry entry = ( Map.Entry ) j.next();
                            attributes.putValue( ( String ) entry.getKey(), ( String ) entry.getValue() );
                        }
                    }

                    mavenManifest.getEntries().put( section.getName(), attributes );
                }
            }

            Attributes mainMavenAttributes = mavenManifest.getMainAttributes();
            mainMavenAttributes.putValue( "Created-By", "novelSpide Maven osgi Plugin" );

            String[] removeHeaders = builder.getProperty( Constants.REMOVEHEADERS, "" ).split( "," );

            // apply -removeheaders to the custom manifest
            for ( int i = 0; i < removeHeaders.length; i++ )
            {
                for ( Iterator j = mainMavenAttributes.keySet().iterator(); j.hasNext(); )
                {
                    if ( j.next().toString().matches( removeHeaders[i].trim() ) )
                    {
                        j.remove();
                    }
                }
            }

            /*
             * Overlay generated bundle manifest with customized entries
             */
            Manifest bundleManifest = jar.getManifest();
            bundleManifest.getMainAttributes().putAll( mainMavenAttributes );
            bundleManifest.getEntries().putAll( mavenManifest.getEntries() );

            getLog().info("hsc info Import-Package:"+bundleManifest.getMainAttributes().getValue( "Import-Package" ));
            // adjust the import package attributes so that optional dependencies use
            // optional resolution.
            String importPackages = bundleManifest.getMainAttributes().getValue( "Import-Package" );
            if ( importPackages != null ){
                Set optionalPackages = getOptionalPackages( currentProject );

                Map<String, Map<String, String>> values = new Analyzer().parseHeader( importPackages );
                for ( Map.Entry<String, Map<String, String>> entry : values.entrySet() )
                {
                    String pkg = entry.getKey();
                    Map<String, String> options = entry.getValue();
                    if ( !options.containsKey( "resolution:" ) && optionalPackages.contains( pkg ) )
                    {
                        options.put( "resolution:", "optional" );
                    }
                }
                String result = Processor.printClauses( values );
                bundleManifest.getMainAttributes().putValue( "Import-Package", result );
            }

            jar.setManifest( bundleManifest );
        }
        catch ( Exception e )
        {
            getLog().warn( "Unable to merge Maven manifest: " + e.getLocalizedMessage() );
        }

        if ( addMavenDescriptor )
        {
            doMavenMetadata( currentProject, jar );
        }

        if ( getLog().isDebugEnabled() )
        {
            getLog().debug( "Final Manifest:" + NL + dumpManifest( jar.getManifest(), new StringBuilder() ) );
        }

        builder.setJar( jar );
    }


    protected Set getOptionalPackages( MavenProject currentProject ) throws IOException, MojoExecutionException
    {
        ArrayList inscope = new ArrayList();
        final Collection artifacts = getSelectedDependencies( currentProject.getArtifacts() );
        for ( Iterator it = artifacts.iterator(); it.hasNext(); )
        {
            Artifact artifact = ( Artifact ) it.next();
            if ( artifact.getArtifactHandler().isAddedToClasspath() )
            {
                if ( !Artifact.SCOPE_TEST.equals( artifact.getScope() ) )
                {
                    inscope.add( artifact );
                }
            }
        }

        HashSet optionalArtifactIds = new HashSet();
        for ( Iterator it = inscope.iterator(); it.hasNext(); )
        {
            Artifact artifact = ( Artifact ) it.next();
            if ( artifact.isOptional() )
            {
                String id = artifact.toString();
                if ( artifact.getScope() != null )
                {
                    // strip the scope...
                    id = id.replaceFirst( ":[^:]*$", "" );
                }
                optionalArtifactIds.add( id );
            }

        }

        HashSet required = new HashSet();
        HashSet optional = new HashSet();
        for ( Iterator it = inscope.iterator(); it.hasNext(); )
        {
            Artifact artifact = ( Artifact ) it.next();
            File file = getFile( artifact );
            if ( file == null )
            {
                continue;
            }

            Jar jar = new Jar( artifact.getArtifactId(), file );
            if ( isTransitivelyOptional( optionalArtifactIds, artifact ) )
            {
                optional.addAll( jar.getPackages() );
            }
            else
            {
                required.addAll( jar.getPackages() );
            }
            jar.close();
        }

        optional.removeAll( required );
        return optional;
    }


    /**
     * Check to see if any dependency along the dependency trail of
     * the artifact is optional.
     *
     * @param artifact
     */
    protected boolean isTransitivelyOptional( HashSet optionalArtifactIds, Artifact artifact )
    {
        List trail = artifact.getDependencyTrail();
        for ( Iterator iterator = trail.iterator(); iterator.hasNext(); )
        {
            String next = ( String ) iterator.next();
            if ( optionalArtifactIds.contains( next ) )
            {
                return true;
            }
        }
        return false;
    }


    private void unpackBundle( File jarFile )
    {
        File outputDir = getOutputDirectory();
        if ( null == outputDir )
        {
            outputDir = new File( getBuildDirectory(), "classes" );
        }

        try
        {
            /*
             * this directory must exist before unpacking, otherwise the plexus
             * unarchiver decides to use the current working directory instead!
             */
            if ( !outputDir.exists() )
            {
                outputDir.mkdirs();
            }

            UnArchiver unArchiver = m_archiverManager.getUnArchiver( "jar" );
            unArchiver.setDestDirectory( outputDir );
            unArchiver.setSourceFile( jarFile );
            unArchiver.extract();
        }
        catch ( Exception e )
        {
            getLog().error( "Problem unpacking " + jarFile + " to " + outputDir, e );
        }
    }


    protected static String removeTagFromInstruction( String instruction, String tag )
    {
        StringBuffer buf = new StringBuffer();

        String[] clauses = instruction.split( "," );
        for ( int i = 0; i < clauses.length; i++ )
        {
            String clause = clauses[i].trim();
            if ( !tag.equals( clause ) )
            {
                if ( buf.length() > 0 )
                {
                    buf.append( ',' );
                }
                buf.append( clause );
            }
        }

        return buf.toString();
    }


    private static Map getProperties( Model projectModel, String prefix )
    {
        Map properties = new LinkedHashMap();
        Method methods[] = Model.class.getDeclaredMethods();
        for ( int i = 0; i < methods.length; i++ )
        {
            String name = methods[i].getName();
            if ( name.startsWith( "get" ) )
            {
                try
                {
                    Object v = methods[i].invoke( projectModel, null );
                    if ( v != null )
                    {
                        name = prefix + Character.toLowerCase( name.charAt( 3 ) ) + name.substring( 4 );
                        if ( v.getClass().isArray() )
                            properties.put( name, Arrays.asList( ( Object[] ) v ).toString() );
                        else
                            properties.put( name, v );

                    }
                }
                catch ( Exception e )
                {
                    // too bad
                }
            }
        }
        return properties;
    }


    private static StringBuffer printLicenses( List licenses )
    {
        if ( licenses == null || licenses.size() == 0 )
            return null;
        StringBuffer sb = new StringBuffer();
        String del = "";
        for ( Iterator i = licenses.iterator(); i.hasNext(); )
        {
            License l = ( License ) i.next();
            String url = l.getUrl();
            if ( url == null )
                continue;
            sb.append( del );
            sb.append( url );
            del = ", ";
        }
        if ( sb.length() == 0 )
            return null;
        return sb;
    }


    /**
     * @param jar
     * @throws IOException
     */
    private void doMavenMetadata( MavenProject currentProject, Jar jar ) throws IOException
    {
        String path = "META-INF/maven/" + currentProject.getGroupId() + "/" + currentProject.getArtifactId();
        File pomFile = new File( currentProject.getBasedir(), "pom.xml" );
        jar.putResource( path + "/pom.xml", new FileResource( pomFile ) );

        Properties p = new Properties();
        p.put( "version", currentProject.getVersion() );
        p.put( "groupId", currentProject.getGroupId() );
        p.put( "artifactId", currentProject.getArtifactId() );
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        p.store( out, "Generated by org.hsc.novelSpider.bundleplugin" );
        jar.putResource( path + "/pom.properties", new EmbeddedResource( out.toByteArray(), System.currentTimeMillis() ) );
    }


    protected Jar[] getClasspath( MavenProject currentProject ) throws IOException, MojoExecutionException
    {
        List list = new ArrayList();

        if ( getOutputDirectory() != null && getOutputDirectory().exists() )
        {
            list.add( new Jar( ".", getOutputDirectory() ) );
        }

        final Collection artifacts = getSelectedDependencies( currentProject.getArtifacts() );
        for ( Iterator it = artifacts.iterator(); it.hasNext(); )
        {
            Artifact artifact = ( Artifact ) it.next();
            if ( artifact.getArtifactHandler().isAddedToClasspath() )
            {
                if ( !Artifact.SCOPE_TEST.equals( artifact.getScope() ) )
                {
                    File file = getFile( artifact );
                    if ( file == null )
                    {
                        getLog().warn(
                            "File is not available for artifact " + artifact + " in project "
                                + currentProject.getArtifact() );
                        continue;
                    }
                    Jar jar = new Jar( artifact.getArtifactId(), file );
                    list.add( jar );
                }
            }
        }
        Jar[] cp = new Jar[list.size()];
        list.toArray( cp );
        return cp;
    }


    private Collection getSelectedDependencies( Collection artifacts ) throws MojoExecutionException
    {
        if ( null == excludeDependencies || excludeDependencies.length() == 0 )
        {
            return artifacts;
        }
        else if ( "true".equalsIgnoreCase( excludeDependencies ) )
        {
            return Collections.EMPTY_LIST;
        }

        Collection selectedDependencies = new LinkedHashSet( artifacts );
        DependencyExcluder excluder = new DependencyExcluder( artifacts );
        excluder.processHeaders( excludeDependencies );
        selectedDependencies.removeAll( excluder.getExcludedArtifacts() );

        return selectedDependencies;
    }


    /**
     * Get the file for an Artifact
     *
     * @param artifact
     */
    protected File getFile( Artifact artifact )
    {
        return artifact.getFile();
    }


    private static void header( Properties properties, String key, Object value )
    {
        if ( value == null )
            return;

        if ( value instanceof Collection && ( ( Collection ) value ).isEmpty() )
            return;

        properties.put( key, value.toString().replaceAll( "[\r\n]", "" ) );
    }


    /**
     * Convert a Maven version into an OSGi compliant version
     *
     * @param version Maven version
     * @return the OSGi version
     */
    protected String convertVersionToOsgi( String version )
    {
        return getMaven2OsgiConverter().getVersion( version );
    }


    /**
     * TODO this should return getMaven2Osgi().getBundleFileName( project.getArtifact() )
     */
    protected String getBundleName( MavenProject currentProject )
    {
        String extension;
        try
        {
            extension = currentProject.getArtifact().getArtifactHandler().getExtension();
        }
        catch ( Throwable e )
        {
            extension = currentProject.getArtifact().getType();
        }
        if ( StringUtils.isEmpty( extension ) || "bundle".equals( extension ) || "pom".equals( extension ) )
        {
            extension = "jar"; // just in case maven gets confused
        }
        String finalName = currentProject.getBuild().getFinalName();
        if ( null != classifier && classifier.trim().length() > 0 )
        {
            return finalName + '-' + classifier + '.' + extension;
        }
        return finalName + '.' + extension;
    }


    protected String getBuildDirectory()
    {
        return buildDirectory;
    }


    protected void setBuildDirectory( String _buildirectory )
    {
        buildDirectory = _buildirectory;
    }


    protected Properties getDefaultProperties( MavenProject currentProject )
    {
        Properties properties = new Properties();

        String bsn;
        try
        {
            bsn = getMaven2OsgiConverter().getBundleSymbolicName( currentProject.getArtifact() );
        }
        catch ( Exception e )
        {
            bsn = currentProject.getGroupId() + "." + currentProject.getArtifactId();
        }

        // Setup defaults
        properties.put( MAVEN_SYMBOLICNAME, bsn );
        properties.put( Analyzer.BUNDLE_SYMBOLICNAME, bsn );
        properties.put( Analyzer.IMPORT_PACKAGE, "*" );
        properties.put( Analyzer.BUNDLE_VERSION, getMaven2OsgiConverter().getVersion( currentProject.getVersion() ) );

        // remove the extraneous Include-Resource and Private-Package entries from generated manifest
        properties.put( Constants.REMOVEHEADERS, Analyzer.INCLUDE_RESOURCE + ',' + Analyzer.PRIVATE_PACKAGE );

        header( properties, Analyzer.BUNDLE_DESCRIPTION, currentProject.getDescription() );
        StringBuffer licenseText = printLicenses( currentProject.getLicenses() );
        if ( licenseText != null )
        {
            header( properties, Analyzer.BUNDLE_LICENSE, licenseText );
        }
        header( properties, Analyzer.BUNDLE_NAME, currentProject.getName() );

        if ( currentProject.getOrganization() != null )
        {
            if ( currentProject.getOrganization().getName() != null )
            {
                String organizationName = currentProject.getOrganization().getName();
                header( properties, Analyzer.BUNDLE_VENDOR, organizationName );
                properties.put( "project.organization.name", organizationName );
                properties.put( "pom.organization.name", organizationName );
            }
            if ( currentProject.getOrganization().getUrl() != null )
            {
                String organizationUrl = currentProject.getOrganization().getUrl();
                header( properties, Analyzer.BUNDLE_DOCURL, organizationUrl );
                properties.put( "project.organization.url", organizationUrl );
                properties.put( "pom.organization.url", organizationUrl );
            }
        }

        properties.putAll( currentProject.getProperties() );
        properties.putAll( currentProject.getModel().getProperties() );
        if ( m_mavenSession != null )
        {
            try
            {
                // don't pass upper-case session settings to bnd as they end up in the manifest
                Properties sessionProperties = m_mavenSession.getExecutionProperties();
                for ( Enumeration e = sessionProperties.propertyNames(); e.hasMoreElements(); )
                {
                    String key = ( String ) e.nextElement();
                    if ( key.length() > 0 && !Character.isUpperCase( key.charAt( 0 ) ) )
                    {
                        properties.put( key, sessionProperties.getProperty( key ) );
                    }
                }
            }
            catch ( Exception e )
            {
                getLog().warn( "Problem with Maven session properties: " + e.getLocalizedMessage() );
            }
        }

        properties.putAll( getProperties( currentProject.getModel(), "project.build." ) );
        properties.putAll( getProperties( currentProject.getModel(), "pom." ) );
        properties.putAll( getProperties( currentProject.getModel(), "project." ) );

        properties.put( "project.baseDir", getBase( currentProject ) );
        properties.put( "project.build.directory", getBuildDirectory() );
        properties.put( "project.build.outputdirectory", getOutputDirectory() );

        properties.put( "classifier", classifier == null ? "" : classifier );

        // Add default plugins
        header( properties, Analyzer.PLUGIN, BlueprintPlugin.class.getName() + "," + SpringXMLType.class.getName() );

        return properties;
    }


    protected static File getBase( MavenProject currentProject )
    {
        return currentProject.getBasedir() != null ? currentProject.getBasedir() : new File( "" );
    }


    protected File getOutputDirectory()
    {
        return outputDirectory;
    }


    protected void setOutputDirectory( File _outputDirectory )
    {
        outputDirectory = _outputDirectory;
    }


    private static void addLocalPackages( File outputDirectory, Analyzer analyzer )
    {
        Collection packages = new TreeSet();

        if ( outputDirectory != null && outputDirectory.isDirectory() )
        {
            // scan classes directory for potential packages
            DirectoryScanner scanner = new DirectoryScanner();
            scanner.setBasedir( outputDirectory );
            scanner.setIncludes( new String[]
                { "**/*.class" } );

            scanner.addDefaultExcludes();
            scanner.scan();

            String[] paths = scanner.getIncludedFiles();
            for ( int i = 0; i < paths.length; i++ )
            {
                packages.add( getPackageName( paths[i] ) );
            }
        }

        StringBuffer exportedPkgs = new StringBuffer();
        StringBuffer privatePkgs = new StringBuffer();

        boolean noprivatePackages = "!*".equals( analyzer.getProperty( Analyzer.PRIVATE_PACKAGE ) );

        for ( Iterator i = packages.iterator(); i.hasNext(); )
        {
            String pkg = ( String ) i.next();

            // mark all source packages as private by default (can be overridden by export list)
            if ( privatePkgs.length() > 0 )
            {
                privatePkgs.append( ';' );
            }
            privatePkgs.append( pkg );

            // we can't export the default package (".") and we shouldn't export internal packages 
            if ( noprivatePackages || !( ".".equals( pkg ) || pkg.contains( ".internal" ) || pkg.contains( ".impl" ) ) )
            {
                if ( exportedPkgs.length() > 0 )
                {
                    exportedPkgs.append( ';' );
                }
                exportedPkgs.append( pkg );
            }
        }

        if ( analyzer.getProperty( Analyzer.EXPORT_PACKAGE ) == null ){
        	
            if ( analyzer.getProperty( Analyzer.EXPORT_CONTENTS ) == null ){
                // no -exportcontents overriding the exports, so use our computed list
                analyzer.setProperty( Analyzer.EXPORT_PACKAGE, exportedPkgs + ";-split-package:=merge-first" );
            }
            else
            {
                // leave Export-Package empty (but non-null) as we have -exportcontents
                analyzer.setProperty( Analyzer.EXPORT_PACKAGE, "" );
            }
        }
        else
        {
            String exported = analyzer.getProperty( Analyzer.EXPORT_PACKAGE );
            if ( exported.indexOf( LOCAL_PACKAGES ) >= 0 )
            {
                String newExported = StringUtils.replace( exported, LOCAL_PACKAGES, exportedPkgs.toString() );
                analyzer.setProperty( Analyzer.EXPORT_PACKAGE, newExported );

            }
        }

        String internal = analyzer.getProperty( Analyzer.PRIVATE_PACKAGE );
        if ( internal == null )
        {
            if ( privatePkgs.length() > 0 )
            {
                analyzer.setProperty( Analyzer.PRIVATE_PACKAGE, privatePkgs + ";-split-package:=merge-first" );
            }
            else
            {
                // if there are really no private packages then use "!*" as this will keep the Bnd Tool happy
                analyzer.setProperty( Analyzer.PRIVATE_PACKAGE, "!*" );
            }
        }
        else if ( internal.indexOf( LOCAL_PACKAGES ) >= 0 )
        {
            String newInternal = StringUtils.replace( internal, LOCAL_PACKAGES, privatePkgs.toString() );
            analyzer.setProperty( Analyzer.PRIVATE_PACKAGE, newInternal );
        }
    }


    private static String getPackageName( String filename )
    {
        int n = filename.lastIndexOf( File.separatorChar );
        return n < 0 ? "." : filename.substring( 0, n ).replace( File.separatorChar, '.' );
    }


    private static List getMavenResources( MavenProject currentProject )
    {
        List resources = new ArrayList( currentProject.getResources() );

        if ( currentProject.getCompileSourceRoots() != null )
        {
            // also scan for any "packageinfo" files lurking in the source folders
            List packageInfoIncludes = Collections.singletonList( "**/packageinfo" );
            for ( Iterator i = currentProject.getCompileSourceRoots().iterator(); i.hasNext(); )
            {
                String sourceRoot = ( String ) i.next();
                Resource packageInfoResource = new Resource();
                packageInfoResource.setDirectory( sourceRoot );
                packageInfoResource.setIncludes( packageInfoIncludes );
                resources.add( packageInfoResource );
            }
        }

        return resources;
    }


    protected static String getMavenResourcePaths( MavenProject currentProject )
    {
        final String basePath = currentProject.getBasedir().getAbsolutePath();

        Set pathSet = new LinkedHashSet();
        for ( Iterator i = getMavenResources( currentProject ).iterator(); i.hasNext(); )
        {
            Resource resource = ( Resource ) i.next();

            final String sourcePath = resource.getDirectory();
            final String targetPath = resource.getTargetPath();

            // ignore empty or non-local resources
            if ( new File( sourcePath ).exists() && ( ( targetPath == null ) || ( targetPath.indexOf( ".." ) < 0 ) ) )
            {
                DirectoryScanner scanner = new DirectoryScanner();

                scanner.setBasedir( sourcePath );
                if ( resource.getIncludes() != null && !resource.getIncludes().isEmpty() )
                {
                    scanner.setIncludes( ( String[] ) resource.getIncludes().toArray( EMPTY_STRING_ARRAY ) );
                }
                else
                {
                    scanner.setIncludes( DEFAULT_INCLUDES );
                }

                if ( resource.getExcludes() != null && !resource.getExcludes().isEmpty() )
                {
                    scanner.setExcludes( ( String[] ) resource.getExcludes().toArray( EMPTY_STRING_ARRAY ) );
                }

                scanner.addDefaultExcludes();
                scanner.scan();

                List includedFiles = Arrays.asList( scanner.getIncludedFiles() );

                for ( Iterator j = includedFiles.iterator(); j.hasNext(); )
                {
                    String name = ( String ) j.next();
                    String path = sourcePath + '/' + name;

                    // make relative to project
                    if ( path.startsWith( basePath ) )
                    {
                        if ( path.length() == basePath.length() )
                        {
                            path = ".";
                        }
                        else
                        {
                            path = path.substring( basePath.length() + 1 );
                        }
                    }

                    // replace windows backslash with a slash
                    // this is a workaround for a problem with bnd 0.0.189
                    if ( File.separatorChar != '/' )
                    {
                        name = name.replace( File.separatorChar, '/' );
                        path = path.replace( File.separatorChar, '/' );
                    }

                    // copy to correct place
                    path = name + '=' + path;
                    if ( targetPath != null )
                    {
                        path = targetPath + '/' + path;
                    }

                    // use Bnd filtering?
                    if ( resource.isFiltering() )
                    {
                        path = '{' + path + '}';
                    }

                    pathSet.add( path );
                }
            }
        }

        StringBuffer resourcePaths = new StringBuffer();
        for ( Iterator i = pathSet.iterator(); i.hasNext(); )
        {
            resourcePaths.append( i.next() );
            if ( i.hasNext() )
            {
                resourcePaths.append( ',' );
            }
        }

        return resourcePaths.toString();
    }


    protected Collection getEmbeddableArtifacts( MavenProject currentProject, Analyzer analyzer )
        throws MojoExecutionException
    {
        final Collection artifacts;

        String embedTransitive = analyzer.getProperty( DependencyEmbedder.EMBED_TRANSITIVE );
        if ( Boolean.valueOf( embedTransitive ).booleanValue() )
        {
            // includes transitive dependencies
            artifacts = currentProject.getArtifacts();
        }
        else
        {
            // only includes direct dependencies
            artifacts = currentProject.getDependencyArtifacts();
        }

        return getSelectedDependencies( artifacts );
    }


    protected static void addMavenSourcePath( MavenProject currentProject, Analyzer analyzer, Log log )
    {
        // pass maven source paths onto BND analyzer
        StringBuilder mavenSourcePaths = new StringBuilder();
        if ( currentProject.getCompileSourceRoots() != null )
        {
            for ( Iterator i = currentProject.getCompileSourceRoots().iterator(); i.hasNext(); )
            {
                if ( mavenSourcePaths.length() > 0 )
                {
                    mavenSourcePaths.append( ',' );
                }
                mavenSourcePaths.append( ( String ) i.next() );
            }
        }
        final String sourcePath = ( String ) analyzer.getProperty( Analyzer.SOURCEPATH );
        if ( sourcePath != null )
        {
            if ( sourcePath.indexOf( MAVEN_SOURCES ) >= 0 )
            {
                // if there is no maven source path, we do a special treatment and replace
                // every occurance of MAVEN_SOURCES and a following comma with an empty string
                if ( mavenSourcePaths.length() == 0 )
                {
                    String cleanedSource = removeTagFromInstruction( sourcePath, MAVEN_SOURCES );
                    if ( cleanedSource.length() > 0 )
                    {
                        analyzer.setProperty( Analyzer.SOURCEPATH, cleanedSource );
                    }
                    else
                    {
                        analyzer.unsetProperty( Analyzer.SOURCEPATH );
                    }
                }
                else
                {
                    String combinedSource = StringUtils
                        .replace( sourcePath, MAVEN_SOURCES, mavenSourcePaths.toString() );
                    analyzer.setProperty( Analyzer.SOURCEPATH, combinedSource );
                }
            }
            else if ( mavenSourcePaths.length() > 0 )
            {
                log.warn( Analyzer.SOURCEPATH + ": overriding " + mavenSourcePaths + " with " + sourcePath + " (add "
                    + MAVEN_SOURCES + " if you want to include the maven sources)" );
            }
        }
        else if ( mavenSourcePaths.length() > 0 )
        {
            analyzer.setProperty( Analyzer.SOURCEPATH, mavenSourcePaths.toString() );
        }
    }
}
