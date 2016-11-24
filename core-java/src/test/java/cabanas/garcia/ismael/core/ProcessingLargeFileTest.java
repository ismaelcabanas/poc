package cabanas.garcia.ismael.core;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

/**
 * Created by XI317311 on 23/11/2016.
 */
public class ProcessingLargeFileTest {

    public static final int MB_BYTES = 1048576;
    //private static final long FILE_SIZE_IN_BYTES = 1024 * 1024 * 1024; // 1GB
    private static final long FILE_SIZE_IN_BYTES = 1024 * 1024; // 1MB
    public static final int CHUNK_FILE_SIZE = 1024 * 1024 * 100;
    public static final String FILE_NAME = System.getProperty("java.io.tmpdir") + "dummy.txt";
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    public void readWithJava_whenReadLargeFile_thenCorrect() {
        // GIVEN
        ProcessingLargeFile processingLargeFile = new ProcessingLargeFile(FILE_NAME);
        File file = createRandomFile(FILE_NAME, FILE_SIZE_IN_BYTES, CHUNK_FILE_SIZE);
        final String filePath = pathOfLargeFile(file);

        // WHEN
        logMemory();
        processingLargeFile.readWithJava();
        logMemory();

        // THEN
    }

    @Test
    public void readWithGuava_whenReadLargeFile_thenCorrect() {
        // GIVEN
        ProcessingLargeFile processingLargeFile = new ProcessingLargeFile(FILE_NAME);
        File file = createRandomFile(FILE_NAME, FILE_SIZE_IN_BYTES, CHUNK_FILE_SIZE);
        final String filePath = pathOfLargeFile(file);

        // WHEN
        logMemory();
        processingLargeFile.readWithGuava();
        logMemory();

        // THEN
    }

    @Test
    public void readWithApacheCommonsIO_whenReadLargeFile_thenCorrect() {
        // GIVEN
        ProcessingLargeFile processingLargeFile = new ProcessingLargeFile(FILE_NAME);
        File file = createRandomFile(FILE_NAME, FILE_SIZE_IN_BYTES, CHUNK_FILE_SIZE);
        final String filePath = pathOfLargeFile(file);

        // WHEN
        logMemory();
        processingLargeFile.readWithApacheCommonsIO();
        logMemory();

        // THEN
    }

    @Test
    public void readWithJavaStreaming_whenReadLargeFile_thenCorrect() {
        // GIVEN
        ProcessingLargeFile processingLargeFile = new ProcessingLargeFile(FILE_NAME);
        File file = createRandomFile(FILE_NAME, FILE_SIZE_IN_BYTES, CHUNK_FILE_SIZE);
        final String filePath = pathOfLargeFile(file);

        // WHEN
        logMemory();
        processingLargeFile.readWithJavaStreaming();
        logMemory();

        // THEN
    }

    @Test
    public void readWithApacheCommonsIOStreaming_whenReadLargeFile_thenCorrect() {
        // GIVEN
        ProcessingLargeFile processingLargeFile = new ProcessingLargeFile(FILE_NAME);
        File file = createRandomFile(FILE_NAME, FILE_SIZE_IN_BYTES, CHUNK_FILE_SIZE);
        final String filePath = pathOfLargeFile(file);

        // WHEN
        logMemory();
        processingLargeFile.readWithApacheCommonsIOStreaming();
        logMemory();

        // THEN
    }

    private File createRandomFile(String fileName, long fileSizeInBytes, int chunkFileSize) {
        byte[] bytes = new byte[chunkFileSize];
        Random rng = new Random();
        rng.nextBytes(bytes);

        File file = new File(fileName);

        // Write bytes to file.
        try(FileOutputStream fos = new FileOutputStream(file)){
            // if file doesn't exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            long numBytesLeft = fileSizeInBytes;
            while(true){

                // If we have less than maxMem left to write, simply redeclare the array
                // to preserve the exact bytes specified in the arguments.
                if (numBytesLeft < chunkFileSize){
                    bytes = new byte[(int)numBytesLeft];
                }

                // Fill the byte array with random data and write it to the file.
                rng.nextBytes(bytes);
                fos.write(bytes);

                // Reduce the number of bytes left to write and stop looping if necessary.
                numBytesLeft -= chunkFileSize;
                if (numBytesLeft <= 0){
                    break;
                }

                // Flush the output so we don't run out of memory.
                fos.flush();
            }
            fos.close();
        }catch (FileNotFoundException e){
            System.err.println("Could not open " + fileName + " for editing: " + e.getMessage());
        }catch (IOException e){
            System.err.println("Could not write to " + fileName + ": " + e.getMessage());
        }
        return file;
    }

    private String pathOfLargeFile(File file) {
        String pathFile = file.getAbsolutePath();
        return pathFile;
    }

    private final void logMemory() {
        logger.info("Max Memory: {} Mb", Runtime.getRuntime().maxMemory() / MB_BYTES);
        logger.info("Total Memory: {} Mb", Runtime.getRuntime().totalMemory() / MB_BYTES);
        logger.info("Free Memory: {} Mb", Runtime.getRuntime().freeMemory() / MB_BYTES);
    }
}
