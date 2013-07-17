package com.taig.communicator.request;

import com.taig.communicator.result.Parser;

import java.net.HttpCookie;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Response
{
	protected URL url;

	protected int code;

	protected String message;

	protected Map<String, List<String>> headers;

	public Response( URL url, int code, String message, Map<String, List<String>> headers )
	{
		this.url = url;
		this.code = code;
		this.message = message;
		this.headers = headers;
	}

	public URL getURL()
	{
		return url;
	}

	public int getCode()
	{
		return code;
	}

	public String getMessage()
	{
		return message;
	}

	public Map<String, List<String>> getHeaders()
	{
		return headers;
	}

	public List<String> getHeader( String key )
	{
		return headers.get( key );
	}

	public HttpCookie getCookie( String name )
	{
		List<HttpCookie> cookies = getCookies();

		if( cookies != null )
		{
			for( HttpCookie cookie : cookies )
			{
				if( cookie.getName().equals( name ) )
				{
					return cookie;
				}
			}
		}

		return null;
	}

	public List<HttpCookie> getCookies()
	{
		List<String> headers = getHeader( "Set-Cookie" );

		if( headers != null )
		{
			List<HttpCookie> cookies = new ArrayList<HttpCookie>();

			for( String header : headers )
			{
				cookies.addAll( HttpCookie.parse( header ) );
			}

			return cookies;
		}

		return null;
	}

	public static class Payload<T> extends Response
	{
		protected T result;

		public Payload( URL url, int code, String message, Map<String, List<String>> headers, T result )
		{
			super( url, code, message, headers );
			this.result = result;
		}

		public T getPayload()
		{
			return result;
		}
	}
}