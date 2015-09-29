
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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database
{
    public void createSQLiteTable() throws SQLException
    {
        Connection connection = null;
        Statement statement = null;
        
        Object[] tuple = getConnectionAndStatement();
        
        connection = (Connection) tuple[0];
        statement = (Statement) tuple[1];
        
        try
        {
            statement.executeUpdate(
                "CREATE TABLE stations" +
                " ( _id      INTEGER PRIMARY KEY AUTOINCREMENT," +
                "   cd       TEXT," +
                "   station  TEXT," +
                "   icao     TEXT," + 
                "   iata     TEXT," +
                "   synop    TEXT," +
                "   lat      REAL," +
                "   lon      REAL," +
                "   elev     INTEGER," +
                "   m        TEXT," +
                "   n        TEXT," +
                "   v        TEXT," +
                "   u        TEXT," +
                "   a        TEXT," +
                "   c        TEXT," +
                "   p        INTEGER," +
                "   cc       TEXT );");
        }
        catch (SQLException e)
        {
            throw e;
        }
        finally
        {
            finalizeConnection(connection, statement);
        }
    }
    
    public int getCount()
    {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultset = null;
        
        int count = -1;
        
        Object[] tuple = getConnectionAndStatement();
        
        connection = (Connection) tuple[0];
        statement = (Statement) tuple[1];
        
        try
        {
            String sql_count = "SELECT COUNT(*) FROM apod;";
            
            resultset = statement.executeQuery(sql_count);
            
            resultset.next();
            
            count = resultset.getInt(1);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            finalizeResultSet(resultset);
            finalizeConnection(connection, statement);
        }
        
        return count;
    }
    
    public long getNewest()
    {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultset = null;
        
        long date = -1;
        
        Object[] tuple = getConnectionAndStatement();
        
        connection = (Connection) tuple[0];
        statement = (Statement) tuple[1];
        
        try
        {
            String sql_max = "SELECT MAX(date) FROM apod;";
            
            resultset = statement.executeQuery(sql_max);
            
            resultset.next();
            
            date = resultset.getLong(1);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            finalizeResultSet(resultset);
            finalizeConnection(connection, statement);
        }
        
        return date;
    }
    
    public long getOldest()
    {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultset = null;
        
        long date = -1;
        
        Object[] tuple = getConnectionAndStatement();
        
        connection = (Connection) tuple[0];
        statement = (Statement) tuple[1];
        
        try
        {
            String sql_max = "SELECT MIN(date) FROM apod;";
            
            resultset = statement.executeQuery(sql_max);
            
            resultset.next();
            
            date = resultset.getLong(1);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            finalizeResultSet(resultset);
            finalizeConnection(connection, statement);
        }
        
        return date;
    }
    
    public Object[] getConnectionAndStatement()
    {
        Connection connection = null;
        Statement statement = null;
        
        boolean exc_thrown = false;
        
        try
        {
            connection = DriverManager.getConnection("jdbc:sqlite:" + Const.DB_NAME);
            
            connection.setAutoCommit(false);
            
            statement = connection.createStatement();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            
            exc_thrown = true;
            
            finalizeConnection(connection, statement);
        }
        
        if(exc_thrown == false)
        {
            return new Object[] { connection , statement };
        }
        else
        {
            return null;
        }
    }
    
    public boolean isAvailable()
    {
        Connection connection = null;
        Statement statement = null;
        
        boolean exc_thrown = false;
        
        try
        {
            connection = DriverManager.getConnection("jdbc:sqlite:" + Const.DB_NAME);
            
            connection.setAutoCommit(false);
            
            statement = connection.createStatement();
            
            try
            {
                statement.executeQuery("SELECT * FROM stations LIMIT 1");
            }
            catch (Exception e)
            {
                exc_thrown = true;
                
                return false;
            }
        }
        catch (SQLException e)
        {
            exc_thrown = true;
        }
        finally
        {
            finalizeConnection(connection, statement);
        }
        
         return exc_thrown == false;
    }
    
    public void finalizeConnection(Connection connection, Statement ... statement)
    {
        if(statement != null)
        {
            for(Statement _statement : statement)
            {
                try
                {
                    _statement.close();
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
        }
        
        if(connection != null)
        {
            try
            {
                connection.commit();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
            finally
            {
                try
                {
                    connection.close();
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public void finalizeStatement(Statement statement)
    {
        if(statement != null)
        {
            try
            {
                statement.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }
    
    public void finalizeResultSet(ResultSet resultset)
    {
        if(resultset != null)
        {
            try
            {
                resultset.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }
}
