package data;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class LoadData {
    private String filename;
    private BufferedReader br;

    public LoadData(String filename) throws FileNotFoundException {
        this.filename = filename;
        this.br = new BufferedReader(new FileReader(this.filename));
    }

    public String readData() throws IOException {
        String a = "";
        while(br.ready()){
            a+=br.readLine();
            a+="\n";
        }
        return a;
    }
}
