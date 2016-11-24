package cabanas.garcia.ismael.core

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import spock.lang.Specification

/**
 * Created by XI317311 on 24/11/2016.
 */
class ProcessingLargeFileSpec extends Specification{

    protected final Logger logger = LoggerFactory.getLogger(getClass())
    private static final String FILE_NAME = System.getProperty("java.io.tmpdir") + "dummy.txt"
    private static final long FILE_SIZE_IN_BYTES = 1024 * 1024 * 1024; // 1MB
    private static final int CHUNK_FILE_SIZE = 1024 * 1024 * 100
    private static final int MB_BYTES = 1048576

    def "reading large file with Java"(){
        given:
        ProcessingLargeFile processingLargeFile = new ProcessingLargeFile(FILE_NAME)
        File file = createRandomFile(FILE_NAME, FILE_SIZE_IN_BYTES, CHUNK_FILE_SIZE);
        final String filePath = pathOfLargeFile(file);

        when:
        logMemory()
        processingLargeFile.readWithJava()
        logMemory()

        then:
        println "Test finished successfully"

    }

    def logMemory() {
        logger.info "Max Memory: {} Mb", Runtime.getRuntime().maxMemory() / MB_BYTES
        logger.info "Total Memory: {} Mb", Runtime.getRuntime().totalMemory() / MB_BYTES
        logger.info "Free Memory: {} Mb", Runtime.getRuntime().freeMemory() / MB_BYTES
    }

    String pathOfLargeFile(File file) {
        file.absolutePath
    }

    File createRandomFile(String fileName, long fileSizeInBytes, int chunkFileSize) {
        def bytes = new byte[chunkFileSize]
        def rng = new Random()
        rng.nextBytes(bytes)

        def file = new File(fileName)

        if(!file.exists())
            file.createNewFile()

        file.withOutputStream {
            stream ->
                long numBytesLeft = fileSizeInBytes
                while(true){

                    // If we have less than maxMem left to write, simply redeclare the array
                    // to preserve the exact bytes specified in the arguments.
                    if (numBytesLeft < chunkFileSize){
                        bytes = new byte[(int)numBytesLeft]
                    }

                    // Fill the byte array with random data and write it to the file.
                    rng.nextBytes(bytes)
                    stream.write(bytes)

                    // Reduce the number of bytes left to write and stop looping if necessary.
                    numBytesLeft -= chunkFileSize
                    if (numBytesLeft <= 0){
                        break
                    }

                    // Flush the output so we don't run out of memory.
                    stream.flush()
                }
                stream.close()
        }

        return file;
    }
}
