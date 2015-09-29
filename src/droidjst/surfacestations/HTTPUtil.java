
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

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class HTTPUtil
{
    public void downloadWebpage(String url_string) throws MalformedURLException, IOException
    {
        URL url;
        
        BufferedInputStream bistream = null;
        FileOutputStream fostream = null;
        
        int len;
        byte[] data;
        
        try
        {
            url = new URL(url_string);
            
            bistream = new BufferedInputStream(url.openStream(), 16 * 1024);
            
            fostream = new FileOutputStream(new File("").getAbsolutePath() + Const.FILE_SEP + "stations");
            
            len = -1;
            data = new byte[4 * 1024];
            
            while((len = bistream.read(data)) != -1)
            {
                fostream.write(data, 0, len);
            }
        }
        catch (MalformedURLException e)
        {
            throw e;
        }
        catch (IOException e)
        {
            throw e;
        }
        finally
        {
            finalizeOutputStreams(fostream);
            finalizeInputStreams(bistream);
        }
    }
    
    private void finalizeInputStreams(InputStream ... istreams) throws IOException
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
    
    private void finalizeOutputStreams(OutputStream ... ostreams) throws IOException
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
}
