package me.banfan;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class FileUtils {

    public static String getFileContent(
            String path
             ) throws IOException
    {
        FileInputStream fis = new FileInputStream(path);

        try( BufferedReader br =
                     new BufferedReader( new InputStreamReader(fis, StandardCharsets.UTF_8)))
        {
            StringBuilder sb = new StringBuilder();
            String line;
            while(( line = br.readLine()) != null ) {
                sb.append( line );
                sb.append( '\n' );
            }
            return sb.toString();
        }
    }


}
