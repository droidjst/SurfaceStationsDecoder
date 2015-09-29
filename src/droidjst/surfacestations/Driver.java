
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
import java.sql.SQLException;

public class Driver
{
    private static HTTPUtil httputil;
    private static Database database;
    private static DatabaseUtil dbutil;
    
    private static Exception exception;
    
    private static void init()
    {
        httputil = new HTTPUtil();
        
        database = new Database();
        dbutil = new DatabaseUtil();
    }
    
    public static void main(String[] args)
    {
        init();
        
        if(database.isAvailable() == false)
        {
            try
            {
                httputil.downloadWebpage(Const.URL_NCAR_RAP_STATIONS);
                
                database.createSQLiteTable();
                
                dbutil.parseFileToSQLite();
            }
            catch (IOException | SQLException e)
            {
                exception = e;
            }
            
            if(exception != null)
            {
                System.err.println(exception.getMessage());
                
                System.exit(1);
            }
        }
    }
    
    /*
    @SuppressWarnings("unused")
    private static void printHeaders(File file)
    {
        BufferedReader breader = null;
        
        try
        {
            breader = new BufferedReader(new FileReader(file), 16 * 1024);
            
            String line = null;
            
            while((line = breader.readLine()) != null)
            {
                if(line.matches(".*([\\d]{2}-[A-Z]{3}-[\\d]{2}[\\s]*)"))
                {
                    System.out.println(line);
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                breader.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
    */
}
