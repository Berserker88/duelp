package de.duelp.backend;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

// POJO, no interface no extends

/**
 * This is Lars' REST server application described at http://www.vogella.de/articles/REST/article.html
 * 3.2. Java class
 *
 * The class registers itself using @GET. using the @Produces, it defines that it delivers two MIME types,
 * text and HTML. The browser always requests the HTML MIME type. Also shown is how to get a hold of
 * the HTTP ServletContext you'd have if you weren't using Jersey and all of this nice annotation.
 *
 */
// Sets the path to base URL + /hello
@Path( "/hello" )
public class DuelpServer
{
	@Context ServletContext context;

	// This method is called if request is TEXT_PLAIN
	@GET
	@Produces( MediaType.TEXT_PLAIN )
	public String sayPlainTextHello()
	{
		// (we don't really want to use ServletContext, just show that we could:)
		//ServletContext ctx = context;
		return "Hello Jersey in plain text";
	}

	// This method is called if request is HTML
	@GET
	@Produces( MediaType.TEXT_HTML )
	public String sayHtmlHello()
	{
		return "<html> "
			+ "<title>" + "Hello Jersey" + "</title>"
			+ "<body><h1>"
			+ "Hello Jersey in HTML"
			+ "</body></h1>"
			+ "</html> ";
	}

	// This method is called if request is XML
	@GET
	@Produces( MediaType.TEXT_XML )
	public String sayXMLHello()
	{
		return "<?xml version=\"1.0\"?>" + "<hello> Hello Jersey in XML" + "</hello>";
	}
}