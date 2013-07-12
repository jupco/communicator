package com.taig.communicator.request;

import com.taig.communicator.event.Event;
import com.taig.communicator.event.Stream;

import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public abstract class Write<T> extends Read<T>
{
	protected Data data;

	public Write( String method, URL url, Data data, Event<T> event )
	{
		super( method, url, event );
		this.data = data;
	}

	public Data getData()
	{
		return data;
	}

	@Override
	public HttpURLConnection connect() throws IOException
	{
		HttpURLConnection connection = super.connect();

		if( data != null )
		{
			connection.setRequestProperty( "Content-Type", data.contentType.toString() );
			connection.setDoOutput( true );

			if( data.getLength() >= 0 )
			{
				connection.setRequestProperty( "Content-Length", String.valueOf( data.getLength() ) );
			}
		}
		else
		{
			connection.setRequestProperty( "Content-Length", "0" );
		}

		return connection;
	}

	@Override
	protected void send( HttpURLConnection connection ) throws IOException
	{
		if( data != null )
		{
			OutputStream output = connection.getOutputStream();

			try
			{
				state.send();
				write( new Stream.Output( output, data.getLength() )
				{
					@Override
					public void update() throws IOException
					{
						if( cancelled )
						{
							throw new InterruptedIOException( "Connection cancelled" );
						}

						state.sending( written, length );
					}
				}, data );
			}
			finally
			{
				output.close();

				if( data != null )
				{
					data.close();
				}
			}
		}
	}

	protected void write( Stream.Output output, InputStream data ) throws IOException
	{
		byte[] buffer = new byte[1024];

		while ( data.read( buffer ) != -1 )
		{
			output.write( buffer );
		}
	}
}