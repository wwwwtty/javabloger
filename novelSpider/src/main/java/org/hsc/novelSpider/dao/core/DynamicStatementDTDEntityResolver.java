package org.hsc.novelSpider.dao.core;

import java.io.InputStream;
import java.io.Serializable;

import org.hibernate.internal.util.ConfigHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;

public class DynamicStatementDTDEntityResolver implements EntityResolver, Serializable{  
    private static final long serialVersionUID = 8123799007554762965L;  
    private static final Logger LOGGER = LoggerFactory.getLogger( DynamicStatementDTDEntityResolver.class );  
    private static final String HOP_DYNAMIC_STATEMENT = "http://www.haier.com/dtd/";  
  
    public InputSource resolveEntity(String publicId, String systemId) {  
        InputSource source = null; // returning null triggers default behavior  
        if ( systemId != null ) {  
            LOGGER.debug( "trying to resolve system-id [" + systemId + "]" );  
            if ( systemId.startsWith( HOP_DYNAMIC_STATEMENT ) ) {  
                LOGGER.debug( "recognized hop dyanmic statement namespace; attempting to resolve on classpath under com/haier/openplatform/dao/hibernate/" );  
                source = resolveOnClassPath( publicId, systemId, HOP_DYNAMIC_STATEMENT );  
            }  
        }  
        return source;  
    }  
  
    private InputSource resolveOnClassPath(String publicId, String systemId, String namespace) {  
        InputSource source = null;  
        String path = "com/haier/openplatform/dao/hibernate/" + systemId.substring( namespace.length() );  
        InputStream dtdStream = resolveInHibernateNamespace( path );  
        if ( dtdStream == null ) {  
            LOGGER.debug( "unable to locate [" + systemId + "] on classpath" );  
            if ( systemId.substring( namespace.length() ).indexOf( "2.0" ) > -1 ) {  
                LOGGER.error( "Don't use old DTDs, read the Hibernate 3.x Migration Guide!" );  
            }  
        }  
        else {  
            LOGGER.debug( "located [" + systemId + "] in classpath" );  
            source = new InputSource( dtdStream );  
            source.setPublicId( publicId );  
            source.setSystemId( systemId );  
        }  
        return source;  
    }  
  
    protected InputStream resolveInHibernateNamespace(String path) {  
        return this.getClass().getClassLoader().getResourceAsStream( path );  
    }  
  
    protected InputStream resolveInLocalNamespace(String path) {  
        try {  
            return ConfigHelper.getUserResourceAsStream( path );  
        }  
        catch ( Throwable t ) {  
            return null;  
        }  
    }  
}  
