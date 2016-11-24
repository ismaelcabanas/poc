package cabanas.garcia.ismael.core;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import java.io.*;
import java.util.Scanner;

/**
 * Created by XI317311 on 23/11/2016.
 */
public class ProcessingLargeFile {
    private final String fileName;

    public ProcessingLargeFile(String fileName) {
        this.fileName = fileName;
    }

    public void readWithJava() {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName)))
        {

            String sCurrentLine;

            while ((sCurrentLine = br.readLine()) != null) {
                //System.out.println(sCurrentLine);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readWithGuava() {
        try {
            Files.readLines(new File(fileName), Charsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readWithApacheCommonsIO() {
        try {
            FileUtils.readLines(new File(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readWithJavaStreaming() {
        FileInputStream inputStream = null;
        Scanner sc = null;
        try {
            inputStream = new FileInputStream(fileName);
            sc = new Scanner(inputStream, "UTF-8");
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                // System.out.println(line);
            }
            // note that Scanner suppresses exceptions
            if (sc.ioException() != null) {
                try {
                    throw sc.ioException();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (sc != null) {
                sc.close();
            }
        }
    }

    public void readWithApacheCommonsIOStreaming() {
        LineIterator it = null;
        try {
            it = FileUtils.lineIterator(new File(fileName), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            while (it.hasNext()) {
                String line = it.nextLine();
                // do something with line
            }
        } finally {
            LineIterator.closeQuietly(it);
        }
    }
}
