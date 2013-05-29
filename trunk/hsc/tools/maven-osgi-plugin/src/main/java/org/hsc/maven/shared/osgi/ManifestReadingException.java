package org.hsc.maven.shared.osgi;


/**
 * Exception while reading the manifest. Encapsulates an IOException to make it runtime
 * 
 * @author <a href="mailto:carlos@apache.org">Carlos Sanchez</a>
 * @version $Id: ManifestReadingException.java 661727 2008-05-30 14:21:49Z bentmann $
 */
public class ManifestReadingException extends RuntimeException
{
	private static final long serialVersionUID = 1L;


	public ManifestReadingException()
    {
        super();
    }


    public ManifestReadingException( String message, Throwable cause )
    {
        super( message, cause );
    }


    public ManifestReadingException( String message )
    {
        super( message );
    }


    public ManifestReadingException( Throwable cause )
    {
        super( cause );
    }
}
