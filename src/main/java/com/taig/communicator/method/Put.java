package com.taig.communicator.method;

import com.taig.communicator.event.Event;
import com.taig.communicator.event.Updateable;
import com.taig.communicator.data.Data;
import com.taig.communicator.request.Write;
import com.taig.communicator.result.Parser;

import java.io.IOException;
import java.net.URL;

import static com.taig.communicator.method.Method.*;

/**
 * The PUT method requests that the enclosed entity be stored under the supplied Request-URI. If the Request-URI refers
 * to an already existing resource, the enclosed entity SHOULD be considered as a modified version of the one residing
 * on the origin server. If the Request-URI does not point to an existing resource, and that URI is capable of being
 * defined as a new resource by the requesting user agent, the origin server can create the resource with that URI. If a
 * new resource is created, the origin server MUST inform the user agent via the 201 (Created) response. If an existing
 * resource is modified, either the 200 (OK) or 204 (No Content) response codes SHOULD be sent to indicate successful
 * completion of the request. If the resource could not be created or modified with the Request-URI, an appropriate
 * error response SHOULD be given that reflects the nature of the problem. The recipient of the entity MUST NOT ignore
 * any Content-* (e.g. Content-Range) headers that it does not understand or implement and MUST return a 501 (Not
 * Implemented) response in such cases.
 * <p/>
 * If the request passes through a cache and the Request-URI identifies one or more currently cached entities, those
 * entries SHOULD be treated as stale. Responses to this method are not cacheable.
 * <p/>
 * The fundamental difference between the POST and PUT requests is reflected in the different meaning of the
 * Request-URI. The URI in a POST request identifies the resource that will handle the enclosed entity. That resource
 * might be a data-accepting process, a gateway to some other protocol, or a separate entity that accepts annotations.
 * In contrast, the URI in a PUT request identifies the entity enclosed with the request -- the user agent knows what
 * URI is intended and the server MUST NOT attempt to apply the request to some other resource. If the server desires
 * that the request be applied to a different URI,
 * <p/>
 * it MUST send a 301 (Moved Permanently) response; the user agent MAY then make its own decision regarding whether or
 * not to redirect the request.
 * <p/>
 * A single resource MAY be identified by many different URIs. For example, an article might have a URI for identifying
 * "the current version" which is separate from the URI identifying each particular version. In this case, a PUT request
 * on a general URI might result in several other URIs being defined by the origin server.
 * <p/>
 * HTTP/1.1 does not define how a PUT method affects the state of an origin server.
 * <p/>
 * PUT requests MUST obey the message transmission requirements set out in section 8.2.
 * <p/>
 * Unless otherwise specified for a particular entity-header, the entity-headers in the PUT request SHOULD be applied to
 * the resource created or modified by the PUT.
 *
 * @param <T> The requested resource's expected return type as generated by a supplied {@link Parser}.
 * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html">http://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html</a>
 */
public class Put<T> extends Write<T>
{
	private Parser<T> parser;

	public Put( Parser<T> parser, URL url, Data data, Event.Payload<T> event )
	{
		super( Type.PUT, url, data, event );
		this.parser = parser;
	}

	@Override
	protected T read( URL url, Updateable.Input input ) throws IOException
	{
		return parser.parse( url, input );
	}
}