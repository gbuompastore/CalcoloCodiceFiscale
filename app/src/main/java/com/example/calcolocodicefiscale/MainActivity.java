package com.example.calcolocodicefiscale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;


import com.example.calcolocodicefiscale.utils.Comuni;
import com.example.calcolocodicefiscale.utils.DatePickerFragment;
import com.example.calcolocodicefiscale.utils.FiscalUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    EditText editTextDataDiNascita, editTextNome, editTextCognome;
    EditText textViewCf;
    RadioButton radioButtonM, radioButtonF;
    List<Comuni> comunis;

    AutoCompleteTextView comune;
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextDataDiNascita = findViewById(R.id.editTextDataDiNascita);
        editTextCognome = findViewById(R.id.editTextCognome);
        editTextNome = findViewById(R.id.editTextNome);
        radioButtonF = findViewById(R.id.radioButtonF);
        radioButtonM = findViewById(R.id.radioButtonM);
        comune= findViewById(R.id.autoCompleteComuni);
        textViewCf= findViewById(R.id.textViewCf);

        try {
            InputStream open = getAssets().open("comuni_pv.csv");
            comunis = readFile(open);
            List<String> cm= comunis.stream().map(c->c.getDescrizione()).collect(Collectors.toList());
            adapter= new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, cm);
            comune.setAdapter(adapter);

        }
        catch (IOException e){
            e.printStackTrace();
        }


    }

    public static List<Comuni> readFile(InputStream inputStream) throws IOException {

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

    public void selezionaData(View view) {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");


    }

    public void calcola(View view) {
        LocalDate date = LocalDate.parse(editTextDataDiNascita.getText().toString(), formatter);
        //operatore ternario, ovvero una if else in una sola riga
        String sesso = radioButtonF.isChecked()?"F":"M";
        String nome= editTextNome.getText().toString();
        String cognnome= editTextCognome.getText().toString();
        String codComune=cercaCodBelfiore(comune.getText().toString());
        String cf = FiscalUtils.creaCodiceFiscale(cognnome, nome, codComune,date,sesso );
        textViewCf.setText(cf);
        Log.d("MainActivity",sesso);
        Log.d("MainActivity",date.toString());
    }

    public String cercaCodBelfiore(String comune){
        for (Comuni c:comunis){
            if(c.getDescrizione().equals(comune)){
                return c.getCodBelfiore();
            }
        }
        return "";
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        editTextDataDiNascita.setText(LocalDate.of(year,month,day).format(formatter));
    }

    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Do something with the time chosen by the user
        }
    }


}