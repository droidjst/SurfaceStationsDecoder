
/*
    Copyright 2015 Joseph Tranquillo {name of copyright owner}

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
 */


package droidjst.surfacestations;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.stream.ImageInputStream;

public class StreamUtil
{
    public static void finalizeInputStreams(InputStream ... istreams) throws IOException
    {
        for(InputStream istream : istreams)
        {
            if(istream != null)
            {
                try
                {
                    istream.close();
                }
                catch (IOException e)
                {
                    throw e;
                }
            }
        }
    }
    
    public static void finalizeOutputStreams(OutputStream ... ostreams) throws IOException
    {
        for(OutputStream ostream : ostreams)
        {
            if(ostream != null)
            {
                try
                {
                    ostream.flush();
                }
                catch (IOException e)
                {
                    throw e;
                }
                finally
                {
                    try
                    {
                        ostream.close();
                    }
                    catch (IOException e)
                    {
                        throw e;
                    }
                }
            }
        }
    }
    
    public static class Image
    {
        public static void finalizeInputStreams(ImageInputStream ... iistreams) throws IOException
        {
            for(ImageInputStream iistream : iistreams)
            {
                if(iistream != null)
                {
                    try
                    {
                        iistream.flush();
                    }
                    catch (IOException e)
                    {
                        throw e;
                    }
                    finally
                    {
                        try
                        {
                            iistream.close();
                        }
                        catch (IOException e)
                        {
                            throw e;
                        }
                    }
                }
            }
        }
    }
}
