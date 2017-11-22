package integration;

import java.io.*;
import java.io.File;
import org.dllearner;

public class LocalDlRun implements DlRunner {



    @Override
    public void localRun() {
               /* to get the path of file */
        File file = new File("movie.conf");
        String fpath = file.getAbsolutePath();

        try {
            String s = null;

            Process p = Runtime.getRuntime().exec("cmd.exe /c cli "+fpath);
            // to get the output in the buffer.
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
            // to get the error in the buffer.
            BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

// read the output from the command
            while ((s = stdInput.readLine()) != null) {
                    System.out.println(s);
            }

// read any errors from the attempted command

            while ((s = stdError.readLine()) != null) {
                System.out.println("Here is the standard error of the command (if any):\n");
                System.out.println(s);
            }


            } catch (IOException e) {
            System.out.println("FROM CATCH" + e.toString());
        }
    }
}
