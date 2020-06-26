package data;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class WriteData {
    private String filename;
    private BufferedWriter bw;
    private String text;

    public WriteData(String filename, String text) throws IOException {
        this.filename = filename;
        this.bw=new BufferedWriter(new FileWriter(this.filename));
        this.text=text;
    }

    public void writeData() throws IOException {
        try{
            bw.write(this.text);
        }finally {
            bw.close();
        }
    }
}
