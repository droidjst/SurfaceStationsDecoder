
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

public class Coordinates
{
    public float convertToDecimalDegrees(String text)
    {
        String hemisphere = text.substring(text.length() - 1);
        
        text = text.substring(0, text.length() - 1);
        
        String $degrees;
        String $minutes;
        
        $degrees = text.split("[\\s]{1}")[0];
        $minutes = text.split("[\\s]{1}")[1];
        
        float degrees_decimal = 0;
        float minutes_decimal;
        
        minutes_decimal  = Float.parseFloat($minutes);
        minutes_decimal /= 60;
        
        degrees_decimal  = Float.parseFloat($degrees);
        degrees_decimal += minutes_decimal;
        
        if(hemisphere.equals("S") || hemisphere.equals("W"))
        {
            degrees_decimal *= -1;
        }
        
        return degrees_decimal;
    }
    
    /*
    public float convertToDegreesMinutes(String text)
    {
        
        return 0;
    }
    
    public float convertToDegreesMinutesSeconds(String text)
    {
        
        return 0;
    }
    */
}
