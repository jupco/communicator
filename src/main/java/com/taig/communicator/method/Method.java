package com.taig.communicator.method;

import com.taig.communicator.event.Event;
import com.taig.communicator.data.Data;
import com.taig.communicator.request.Request;
import com.taig.communicator.request.Response;
import com.taig.communicator.result.Ignore;
import com.taig.communicator.result.Image;
import com.taig.communicator.result.Parser;
import com.taig.communicator.result.Text;

import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * A collection of several factory method to easily instantiate {@link Request Requests}. Using on of these methods is
 * the recommended way to create a Request.
 */
public abstract class Method
{
	/**
	 * The supported HTTP Method types.
	 */
	public enum Type
	{
		/**
		 * @see Delete
		 */
		DELETE,

		/**
		 * @see Get
		 */
		GET,

		/**
		 * @see Head
		 */
		HEAD,

		/**
		 * @see Post
		 */
		POST,

		/**
		 * @see Put
		 */
		PUT
	}

	/**
	 * Create an HTTP GET {@link Request}.
	 *
	 * @param parser The {@link Class} of a {@link Parser} used to evaluate the server's response.
	 * @param url    The resource's {@link URL}.
	 * @param <T>    The resource's type after successful parsing.
	 * @return An instance of {@link Get}.
	 */
	public static <T> Get<Response.Payload<T>, Event.Payload<T>, T> GET( Class<? extends Parser<T>> parser, URL url )
	{
		return GET( parser, url, null );
	}

	/**
	 * Create an HTTP GET {@link Request} with {@link Event} callbacks.
	 *
	 * @param parser The {@link Class} of a {@link Parser} used to evaluate the server's response.
	 * @param url    The resource's {@link URL}.
	 * @param event  The Event callbacks that will be executed during the request. May be <code>null</code>.
	 * @param <T>    The resource's type after successful parsing.
	 * @return An instance of {@link Get}.
	 */
	public static <T> Get<Response.Payload<T>, Event.Payload<T>, T> GET( Class<? extends Parser<T>> parser, URL url, Event.Payload<T> event )
	{
		return GET( createParser( parser ), url, event );
	}

	/**
	 * Create an HTTP GET {@link Request} with {@link Event} callbacks.
	 * <p/>
	 * This method accepts an actual {@link Parser} object (instead of a Parser {@link Class}) for complex use cases
	 * when a simple Parser with default constructor is not sufficient.
	 *
	 * @param parser The Parser used to evaluate the server's response.
	 * @param url    The resource's {@link URL}.
	 * @param event  The Event callbacks that will be executed during the request. May be <code>null</code>.
	 * @param <T>    The resource's type after successful parsing.
	 * @return An instance of {@link Get}.
	 */
	public static <T> Get<Response.Payload<T>, Event.Payload<T>, T> GET( Parser<T> parser, URL url, Event.Payload<T> event )
	{
		return new Get<Response.Payload<T>, Event.Payload<T>, T>( parser, url, event )
		{
			@Override
			protected Response.Payload<T> summarize( URL url, int code, String message, Map<String, List<String>> headers, T body )
			{
				return new Response.Payload<T>( url, code, message, headers, body );
			}
		};
	}

	/**
	 * Create an HTTP DELETE {@link Request} with {@link Event} callbacks, but ignore the
	 * server's response payload.
	 *
	 * @param url   The resource's {@link URL}.
	 * @param event The Event callbacks that will be executed during the request. May be <code>null</code>.
	 * @return An instance of {@link Delete}.
	 */
	public static Delete<Response, Event<Response>, Void> DELETE( URL url, Event<Response> event )
	{
		return DELETE( url, null, event );
	}

	/**
	 * Create an HTTP DELETE {@link Request} with payload {@link Data}, but ignore the
	 * server's response payload.
	 *
	 * @param url  The resource's {@link URL}.
	 * @param data The payload Data that will be added to the Request body. May be <code>null</code>.
	 * @return An instance of {@link Delete}.
	 */
	public static Delete<Response, Event<Response>, Void> DELETE( URL url, Data data )
	{
		return DELETE( url, data, null );
	}

	/**
	 * Create an HTTP DELETE {@link Request} with payload {@link Data} and {@link Event} callbacks, but ignore the
	 * server's response payload.
	 *
	 * @param url   The resource's {@link URL}.
	 * @param data  The payload Data that will be added to the Request body. May be <code>null</code>.
	 * @param event The Event callbacks that will be executed during the request. May be <code>null</code>.
	 * @return An instance of {@link Delete}.
	 */
	public static Delete<Response, Event<Response>, Void> DELETE( URL url, Data data, Event<Response> event )
	{
		return new Delete<Response, Event<Response>, Void>( Parser.IGNORE, url, data, event )
		{
			@Override
			protected Response summarize( URL url, int code, String message, Map<String, List<String>> headers, Void body )
			{
				return new Response( url, code, message, headers );
			}
		};
	}

	/**
	 * Create an HTTP DELETE {@link Request}.
	 *
	 * @param parser The {@link Class} of a {@link Parser} used to evaluate the server's response.
	 * @param url    The resource's {@link URL}.
	 * @param <T>    The resource's type after successful parsing.
	 * @return An instance of {@link Delete}.
	 */
	public static <T> Delete<Response.Payload<T>, Event.Payload<T>, T> DELETE( Class<? extends Parser<T>> parser, URL url )
	{
		return DELETE( parser, url, null, null );
	}

	/**
	 * Create an HTTP DELETE {@link Request} with {@link Event} callbacks.
	 *
	 * @param parser The {@link Class} of a {@link Parser} used to evaluate the server's response.
	 * @param url    The resource's {@link URL}.
	 * @param event  The Event callbacks that will be executed during the request. May be <code>null</code>.
	 * @param <T>    The resource's type after successful parsing.
	 * @return An instance of {@link Delete}.
	 */
	public static <T> Delete<Response.Payload<T>, Event.Payload<T>, T> DELETE( Class<? extends Parser<T>> parser, URL url, Event.Payload<T> event )
	{
		return DELETE( parser, url, null, event );
	}

	/**
	 * Create an HTTP DELETE {@link Request} with payload {@link Data}.
	 *
	 * @param parser The {@link Class} of a {@link Parser} used to evaluate the server's response.
	 * @param url    The resource's {@link URL}.
	 * @param data   The payload Data that will be added to the Request body. May be <code>null</code>.
	 * @param <T>    The resource's type after successful parsing.
	 * @return An instance of {@link Delete}.
	 */
	public static <T> Delete<Response.Payload<T>, Event.Payload<T>, T> DELETE( Class<? extends Parser<T>> parser, URL url, Data data )
	{
		return DELETE( parser, url, data, null );
	}

	/**
	 * Create an HTTP DELETE {@link Request} with payload {@link Data} and {@link Event} callbacks.
	 *
	 * @param parser The {@link Class} of a {@link Parser} used to evaluate the server's response.
	 * @param url    The resource's {@link URL}.
	 * @param data   The payload Data that will be added to the Request body. May be <code>null</code>.
	 * @param event  The Event callbacks that will be executed during the request. May be <code>null</code>.
	 * @param <T>    The resource's type after successful parsing.
	 * @return An instance of {@link Delete}.
	 */
	public static <T> Delete<Response.Payload<T>, Event.Payload<T>, T> DELETE( Class<? extends Parser<T>> parser, URL url, Data data, Event.Payload<T> event )
	{
		return DELETE( createParser( parser ), url, data, event );
	}

	/**
	 * Create an HTTP DELETE {@link Request} with payload {@link Data} and {@link Event} callbacks.
	 * <p/>
	 * This method accepts an actual {@link Parser} object (instead of a Parser {@link Class}) for complex use cases
	 * when a simple Parser with default constructor is not sufficient.
	 *
	 * @param parser The Parser used to evaluate the server's response.
	 * @param url    The resource's {@link URL}.
	 * @param data   The payload Data that will be added to the Request body. May be <code>null</code>.
	 * @param event  The Event callbacks that will be executed during the request. May be <code>null</code>.
	 * @param <T>    The resource's type after successful parsing.
	 * @return An instance of {@link Delete}.
	 */
	public static <T> Delete<Response.Payload<T>, Event.Payload<T>, T> DELETE( Parser<T> parser, URL url, Data data, Event.Payload<T> event )
	{
		return new Delete<Response.Payload<T>, Event.Payload<T>, T>( parser, url, data, event )
		{
			@Override
			protected Response.Payload<T> summarize( URL url, int code, String message, Map<String, List<String>> headers, T body )
			{
				return new Response.Payload<T>( url, code, message, headers, body );
			}
		};
	}

	/**
	 * Create an HTTP HEAD {@link Request}.
	 *
	 * @param url The resource's {@link URL}.
	 * @return An instance of {@link Head}.
	 */
	public static Head HEAD( URL url )
	{
		return HEAD( url, null );
	}

	/**
	 * Create an HTTP HEAD {@link Request} with {@link Event} callbacks.
	 *
	 * @param url   The resource's {@link URL}.
	 * @param event The Event callbacks that will be executed during the request. May be <code>null</code>.
	 * @return An instance of {@link Head}.
	 */
	public static Head HEAD( URL url, Event<Response> event )
	{
		return new Head( url, event );
	}

	/**
	 * Create an HTTP POST {@link Request}.
	 *
	 * @param parser The {@link Class} of a {@link Parser} used to evaluate the server's response.
	 * @param url    The resource's {@link URL}.
	 * @param data   The payload Data that will be added to the Request body. May be <code>null</code>.
	 * @param <T>    The resource's type after successful parsing.
	 * @return An instance of {@link Post}.
	 */
	public static <T> Post<Response.Payload<T>, Event.Payload<T>, T> POST( Class<? extends Parser<T>> parser, URL url, Data data )
	{
		return POST( parser, url, data, null );
	}

	/**
	 * Create an HTTP POST {@link Request} with {@link Event} callbacks.
	 *
	 * @param parser The {@link Class} of a {@link Parser} used to evaluate the server's response.
	 * @param url    The resource's {@link URL}.
	 * @param data   The payload Data that will be added to the Request body. May be <code>null</code>.
	 * @param event  The Event callbacks that will be executed during the request. May be <code>null</code>.
	 * @param <T>    The resource's type after successful parsing.
	 * @return An instance of {@link Post}.
	 */
	public static <T> Post<Response.Payload<T>, Event.Payload<T>, T> POST( Class<? extends Parser<T>> parser, URL url, Data data, Event.Payload<T> event )
	{
		return POST( createParser( parser ), url, data, event );
	}

	/**
	 * Create an HTTP POST {@link Request} with {@link Event} callbacks.
	 * <p/>
	 * This method accepts an actual {@link Parser} object (instead of a Parser {@link Class}) for complex use cases
	 * when a simple Parser with default constructor is not sufficient.
	 *
	 * @param parser The {@link Class} of a {@link Parser} used to evaluate the server's response.
	 * @param url    The resource's {@link URL}.
	 * @param data   The payload Data that will be added to the Request body. May be <code>null</code>.
	 * @param event  The Event callbacks that will be executed during the request. May be <code>null</code>.
	 * @param <T>    The resource's type after successful parsing.
	 * @return An instance of {@link Post}.
	 */
	public static <T> Post<Response.Payload<T>, Event.Payload<T>, T> POST( Parser<T> parser, URL url, Data data, Event.Payload<T> event )
	{
		return new Post<Response.Payload<T>, Event.Payload<T>, T>( parser, url, data, event )
		{
			@Override
			protected Response.Payload<T> summarize( URL url, int code, String message, Map<String, List<String>> headers, T body )
			{
				return new Response.Payload<T>( url, code, message, headers, body );
			}
		};
	}

	/**
	 * Create an HTTP PUT {@link Request}, but ignore the server's response payload.
	 *
	 * @param url  The resource's {@link URL}.
	 * @param data The payload Data that will be added to the Request body. May be <code>null</code>.
	 * @return An instance of {@link Put}.
	 */
	public static Put<Response, Event<Response>, Void> PUT( URL url, Data data )
	{
		return PUT( url, data, null );
	}

	/**
	 * Create an HTTP PUT {@link Request} with {@link Event} callbacks, but ignore the server's response payload.
	 *
	 * @param url   The resource's {@link URL}.
	 * @param data  The payload Data that will be added to the Request body. May be <code>null</code>.
	 * @param event The Event callbacks that will be executed during the request. May be <code>null</code>.
	 * @return An instance of {@link Put}.
	 */
	public static Put<Response, Event<Response>, Void> PUT( URL url, Data data, Event<Response> event )
	{
		return new Put<Response, Event<Response>, Void>( Parser.IGNORE, url, data, event )
		{
			@Override
			protected Response summarize( URL url, int code, String message, Map<String, List<String>> headers, Void body )
			{
				return new Response( url, code, message, headers );
			}
		};
	}

	/**
	 * Create an HTTP PUT {@link Request}.
	 *
	 * @param parser The {@link Class} of a {@link Parser} used to evaluate the server's response.
	 * @param url    The resource's {@link URL}.
	 * @param data   The payload Data that will be added to the Request body. May be <code>null</code>.
	 * @param <T>    The resource's type after successful parsing.
	 * @return An instance of {@link Put}.
	 */
	public static <T> Put<Response.Payload<T>, Event.Payload<T>, T> PUT( Class<? extends Parser<T>> parser, URL url, Data data )
	{
		return PUT( parser, url, data, null );
	}

	/**
	 * Create an HTTP PUT {@link Request} with {@link Event} callbacks.
	 *
	 * @param parser The {@link Class} of a {@link Parser} used to evaluate the server's response.
	 * @param url    The resource's {@link URL}.
	 * @param data   The payload Data that will be added to the Request body. May be <code>null</code>.
	 * @param event  The Event callbacks that will be executed during the request. May be <code>null</code>.
	 * @param <T>    The resource's type after successful parsing.
	 * @return An instance of {@link Put}.
	 */
	public static <T> Put<Response.Payload<T>, Event.Payload<T>, T> PUT( Class<? extends Parser<T>> parser, URL url, Data data, Event.Payload<T> event )
	{
		return PUT( createParser( parser ), url, data, event );
	}

	/**
	 * Create an HTTP PUT {@link Request} with {@link Event} callbacks.
	 * <p/>
	 * This method accepts an actual {@link Parser} object (instead of a Parser {@link Class}) for complex use cases
	 * when a simple Parser with default constructor is not sufficient.
	 *
	 * @param parser The {@link Class} of a {@link Parser} used to evaluate the server's response.
	 * @param url    The resource's {@link URL}.
	 * @param data   The payload Data that will be added to the Request body. May be <code>null</code>.
	 * @param event  The Event callbacks that will be executed during the request. May be <code>null</code>.
	 * @param <T>    The resource's type after successful parsing.
	 * @return An instance of {@link Put}.
	 */
	public static <T> Put<Response.Payload<T>, Event.Payload<T>, T> PUT( Parser<T> parser, URL url, Data data, Event.Payload<T> event )
	{
		return new Put<Response.Payload<T>, Event.Payload<T>, T>( parser, url, data, event )
		{
			@Override
			protected Response.Payload<T> summarize( URL url, int code, String message, Map<String, List<String>> headers, T body )
			{
				return new Response.Payload<T>( url, code, message, headers, body );
			}
		};
	}

	/**
	 * Create an instance of a {@link Parser} via reflection.
	 *
	 * @param type The class of the Parser to instantiate.
	 * @return The instantiated Parser.
	 * @throws RuntimeException If the given Parser class does not have a default constructor.
	 */
	@SuppressWarnings( "unchecked" )
	protected static <T> Parser<T> createParser( Class<? extends Parser<T>> type )
	{
		try
		{
			if( type == Text.class )
			{
				return (Parser<T>) Parser.TEXT;
			}
			else if( type == Image.class )
			{
				return (Parser<T>) Parser.IMAGE;
			}
			else if( type == Ignore.class )
			{
				return (Parser<T>) Parser.IGNORE;
			}
			else
			{
				return type.getConstructor().newInstance();
			}
		}
		catch( Exception exception )
		{
			throw new RuntimeException( "Could not instantiate default constructor of type '" + type.getName() + "'" );
		}
	}
}