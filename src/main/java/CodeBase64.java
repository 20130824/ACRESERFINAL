/**
 * Created by Dany on 29/11/2016.
 */

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.codec.binary.Base64;

public class CodeBase64 {
    private final boolean IS_CHUNKED = true;
    private File file;
    private Base64 base64;

    public CodeBase64(String Converting) {

        base64 = new Base64();
        this.file = file;
    }

    public String base64Decode(String token) {
        byte[] decodedBytes = Base64.decodeBase64(token.getBytes());
        return new String(decodedBytes, Charset.forName("UTF-8"));
    }

}