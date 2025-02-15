package com.example.calcolocodicefiscale;

import com.example.calcolocodicefiscale.utils.Comuni;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ComuniService {

    public  List<Comuni> readFile(InputStream inputStream) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        List<Comuni> comuni=new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null) {
            String[] split = line.split(",");
            Comuni c = new Comuni();
            c.setCodice(split[0].replaceAll("\"", ""));
            c.setDescrizione(split[1]);
            c.setCap(split[2]);
            c.setCodBelfiore(split[3]);
            c.setProvincia(split[4]);
            comuni.add(c);
        }

        return comuni;
    }
}
