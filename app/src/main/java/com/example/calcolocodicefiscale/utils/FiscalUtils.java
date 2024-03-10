package com.example.calcolocodicefiscale.utils;



import java.math.BigDecimal;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;



public class FiscalUtils {

    private static final List<String> vocali = Arrays.asList("a", "e", "i", "o", "u");

    /**
     * Calcolo del codice Fiscale per la Persona Fisica
     *
     * @param cognome
     * @param nome
     * @param codiceBelfiore
     * @param dataNascita (LocalDate)
     * @param sesso
     * @return
     */


    /**
     * Calcolo del codice Fiscale per la Persona Fisica
     *
     * @param cognome
     * @param nome
     * @param codiceBelfiore
     * @param dataNascita (Date)
     * @param sesso
     * @return
     */
    public static String creaCodiceFiscale(String cognome, String nome, String codiceBelfiore, LocalDate dataNascita,
                                           String sesso) {

        String codiceFiscale="";
        if (cognome.isEmpty() || nome.isEmpty() || codiceBelfiore.isEmpty() || dataNascita == null
                || sesso.isEmpty()) {
            String message = "Impossibile calcolare il codice fiscale. Parametri incompleti o non corretti";

            throw new IllegalArgumentException(message);
        } else {
            cognome = cognome.replaceAll(" ", "").replaceAll("'", "");
            nome = nome.replaceAll(" ", "").replaceAll("'", "");

            // COGNOME
            List<String> consonantiCognome = new ArrayList<>();
            List<String> vocaliCognome = new ArrayList<>();

            for (char ch : cognome.toCharArray()) {
                String lettera = String.valueOf(ch);
                if (isVocale(lettera))
                    vocaliCognome.add(lettera);
                else
                    consonantiCognome.add(lettera);
            }

            // NOME
            List<String> consonantiNome = new ArrayList<>();
            List<String> vocaliNome = new ArrayList<>();
            for (char ch : nome.toCharArray()) {
                String lettera = String.valueOf(ch);

                if (isVocale(lettera))
                    vocaliNome.add(lettera);
                else
                    consonantiNome.add(lettera);

            }
            StringBuffer _cognome = new StringBuffer();
            StringBuffer _nome = new StringBuffer();
            int numConsonantiCognome = consonantiCognome.size();
            int numVocaliCognome = vocaliCognome.size();
            int numConsonantiNome = consonantiNome.size();
            int numVocaliNome = vocaliNome.size();
            // COSTRUZIONE CODICE FISCALE
            // COGNOME
            if (numConsonantiCognome >= 3) {
                _cognome.append(consonantiCognome.get(0));
                _cognome.append(consonantiCognome.get(1));
                _cognome.append(consonantiCognome.get(2));
            } else if (numConsonantiCognome == 2) {
                _cognome.append(consonantiCognome.get(0));
                _cognome.append(consonantiCognome.get(1));
                _cognome.append(vocaliCognome.get(0));
            } else if ((numConsonantiCognome == 1) && (numVocaliCognome >= 2)) {
                _cognome.append(consonantiCognome.get(0));
                _cognome.append(vocaliCognome.get(0));
                _cognome.append(vocaliCognome.get(1));
            } else if ((numConsonantiCognome == 1) && (numVocaliCognome == 1)) {
                _cognome.append(consonantiCognome.get(0));
                _cognome.append(vocaliCognome.get(0));
                _cognome.append("X");
            }

            else if ((numConsonantiCognome == 0) && (numVocaliCognome == 1)) {
                _cognome.append(vocaliCognome.get(0));
                _cognome.append("XX");
            } else if ((numConsonantiCognome == 0) && (numVocaliCognome == 2)) {
                _cognome.append(vocaliCognome.get(0));
                _cognome.append(vocaliCognome.get(1));
                _cognome.append("X");
            } else if ((numConsonantiCognome == 0) && (numVocaliCognome >= 3)) {
                _cognome.append(vocaliCognome.get(0));
                _cognome.append(vocaliCognome.get(1));
                _cognome.append(vocaliCognome.get(2));
            } else if ((numConsonantiCognome == 0) && (numVocaliCognome == 0)) {
                _cognome.append("XXX");
            }

            // NOME
            if (numConsonantiNome >= 4) {
                _nome.append(consonantiNome.get(0));
                _nome.append(consonantiNome.get(2));
                _nome.append(consonantiNome.get(3));
            } else if (numConsonantiNome == 3) {
                _nome.append(consonantiNome.get(0));
                _nome.append(consonantiNome.get(1));
                _nome.append(consonantiNome.get(2));
            } else if (numConsonantiNome == 2) {
                _nome.append(consonantiNome.get(0));
                _nome.append(consonantiNome.get(1));
                _nome.append(vocaliNome.get(0));
            } else if ((numConsonantiNome == 1) && (numVocaliNome >= 2)) {
                _nome.append(consonantiNome.get(0));
                _nome.append(vocaliNome.get(0));
                _nome.append(vocaliNome.get(1));
            } else if ((numConsonantiNome == 1) && (numVocaliNome == 1)) {
                _nome.append(consonantiNome.get(0));
                _nome.append(vocaliNome.get(0));
                _nome.append("X");
            } else if ((numConsonantiNome == 0) && (numVocaliNome == 1)) {
                _nome.append(vocaliNome.get(0));
                _nome.append("XX");
            } else if ((numConsonantiNome == 0) && (numVocaliNome == 2)) {
                _nome.append(vocaliNome.get(0));
                _nome.append(vocaliNome.get(1));
                _nome.append("X");
            } else if ((numConsonantiNome == 0) && (numVocaliNome == 3)) {
                _nome.append(vocaliNome.get(0));
                _nome.append(vocaliNome.get(1));
                _nome.append(vocaliNome.get(2));
            } else if ((numConsonantiNome == 0) && (numVocaliNome == 0)) {
                _nome.append("XXX");
            }

            // ANNO DI NASCITA
            //dont'use Calendar


            String anno = String.valueOf(dataNascita.getYear());
            if (Integer.parseInt(anno) > 1900)
                anno = anno.substring(2);

            String mese = String.format("%02d", dataNascita.getMonthValue());

            String giorno = "";
            if (sesso.equalsIgnoreCase("F")) {
                giorno = String.format("%02d", dataNascita.getDayOfMonth() + 40);
            } else {
                giorno = String.format("%02d", dataNascita.getDayOfMonth());
            }

            if (giorno.length() == 1)
                giorno = "0" + giorno;

            if (mese.equalsIgnoreCase("01")) {
                mese = "A";
            } else if (mese.equalsIgnoreCase("02")) {
                mese = "B";
            } else if (mese.equalsIgnoreCase("03")) {
                mese = "C";
            } else if (mese.equalsIgnoreCase("04")) {
                mese = "D";
            } else if (mese.equalsIgnoreCase("05")) {
                mese = "E";
            } else if (mese.equalsIgnoreCase("06")) {
                mese = "H";
            } else if (mese.equalsIgnoreCase("07")) {
                mese = "L";
            } else if (mese.equalsIgnoreCase("08")) {
                mese = "M";
            } else if (mese.equalsIgnoreCase("09")) {
                mese = "P";
            } else if (mese.equalsIgnoreCase("10")) {
                mese = "R";
            } else if (mese.equalsIgnoreCase("11")) {
                mese = "S";
            } else if (mese.equalsIgnoreCase("12")) {
                mese = "T";
            }

            codiceFiscale = _cognome.toString() + _nome.toString() + anno + mese + giorno + codiceBelfiore;
            // CALCOLO CARATTERE DI CONTROLLO

            int Numero1 = 0;
            for (int i = 1; i <= 13; i = i + 2) {
                String ch = String.valueOf(codiceFiscale.charAt(i)).toUpperCase();
                Numero1 += Integer.parseInt(charPari.get(ch));
            }
            int Numero2 = 0;

            for (int i = 0; i <= 14; i = i + 2) {
                String ch = String.valueOf(codiceFiscale.charAt(i)).toUpperCase();
                Numero2 += Integer.parseInt(charDispari.get(ch));

            }

            int Numero = Numero1 + Numero2;
            int Q = (int) (Numero / 26);
            int Quoz = Numero - (Q * 26);
            String CC = String.valueOf((char) (65 + Quoz));
            codiceFiscale = codiceFiscale + CC;

            return codiceFiscale.toUpperCase();
        }
    }


    private static final Map<String, String> charDispari = Stream
            .of(new String[][] { { "0", "1" }, { "1", "0" }, { "2", "5" }, { "3", "7" }, { "4", "9" }, { "5", "13" },
                    { "6", "15" }, { "7", "17" }, { "8", "19" }, { "9", "21" }, { "A", "1" }, { "B", "0" },
                    { "C", "5" }, { "D", "7" }, { "E", "9" }, { "F", "13" }, { "G", "15" }, { "H", "17" },
                    { "I", "19" }, { "J", "21" }, { "K", "2" }, { "L", "4" }, { "M", "18" }, { "N", "20" },
                    { "O", "11" }, { "P", "3" }, { "Q", "6" }, { "R", "8" }, { "S", "12" }, { "T", "14" },
                    { "U", "16" }, { "V", "10" }, { "W", "22" }, { "X", "25" }, { "Y", "24" }, { "Z", "23" }, })
            .collect(Collectors.toMap(data -> data[0], data -> data[1]));
    //	Stringa2 = "*0*1_,*1*0_,*2*5_,*3*7_,*4*9_,*5*13_,*6*15_,*7*17_,*8*19_,*9*21_,*A*1_,*B*0_,*C*5_,*D*7_,*E*9_,*F*13_,*G*15_,*H*17_,*I*19_,*J*21_,*K*2_,*L*4_,*M*18_,"
//			+ "*N*20_,*O*11_,*P*3_,*Q*6_,*R*8_,*S*12_,*T*14_,*U*16_,*V*10_,*W*22_,*X*25_,*Y*24_,*Z*23_";
//
    private static final Map<String, String> charPari = Stream
            .of(new String[][] { { "0", "0" }, { "1", "1" }, { "2", "2" }, { "3", "3" }, { "4", "4" }, { "5", "5" },
                    { "6", "6" }, { "7", "7" }, { "8", "8" }, { "9", "9" }, { "A", "0" }, { "B", "1" }, { "C", "2" },
                    { "D", "3" }, { "E", "4" }, { "F", "5" }, { "G", "6" }, { "H", "7" }, { "I", "8" }, { "J", "9" },
                    { "K", "10" }, { "L", "11" }, { "M", "12" }, { "N", "13" }, { "O", "14" }, { "P", "15" },
                    { "Q", "16" }, { "R", "17" }, { "S", "18" }, { "T", "19" }, { "U", "20" }, { "V", "21" },
                    { "W", "22" }, { "X", "23" }, { "Y", "24" }, { "Z", "25" }, })
            .collect(Collectors.toMap(data -> data[0], data -> data[1]));


    /**
     * Verifica della correttezza del codice Iban
     *
     * @param iban
     * @return
     */
    public static boolean isIbanValid(String iban) {
        iban = iban.toUpperCase();
        String rearrangedIban = iban.substring(4) + iban.substring(0, 4);
        StringCharacterIterator iterator = new StringCharacterIterator(rearrangedIban);
        StringBuilder builder = new StringBuilder();
        for (char ch = iterator.first(); ch != CharacterIterator.DONE; ch = iterator.next()) {
            if (ch >= 'A' && ch <= 'Z') {
                int valorePerIban = ch - 55;
                builder.append(valorePerIban);
            } else {
                builder.append(ch);
            }
        }
        BigDecimal ibanConvertito = new BigDecimal(builder.toString());
        BigDecimal resto = ibanConvertito.remainder(BigDecimal.valueOf(97));
        if (resto.intValue() == 1) {
            return true;
        }
        return false;
    }


    /**
     * Verifica della correttezza del Codice Fiscale della Persona Fisica
     *
     * @param parCF
     * @return
     */
    public static boolean isCFValid(String parCF) {
        String cf = parCF.toUpperCase();
        if (cf.length() == 16) {
            char[] caratteri = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q',
                    'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
            int[] valori_dispari = { 1, 0, 5, 7, 9, 13, 15, 17, 19, 21, 2, 4, 18, 20, 11, 3, 6, 8, 12, 14, 16, 10, 22,
                    25, 24, 23, 1, 0, 5, 7, 9, 13, 15, 17, 19, 21 };
            int[] valori_pari = new int[36];
            for (int i = 0; i < 26; i++) {
                valori_pari[i] = i;
            }
            for (int i = 26; i < 36; i++) {
                valori_pari[i] = i - 26;
            }
            char[] caratteriCF = cf.toCharArray();
            int valore = 0;
            for (int i = 0; i < caratteriCF.length - 1; i++) {
                if ((i + 1) % 2 == 0) {
                    for (int j = 0; j < caratteri.length; j++) {
                        if (caratteriCF[i] == caratteri[j]) {
                            valore += valori_pari[j];
                        }
                    }
                } else {
                    for (int j = 0; j < caratteri.length; j++) {
                        if (caratteriCF[i] == caratteri[j]) {
                            valore += valori_dispari[j];
                        }
                    }
                }
            }
            valore %= 26;
            for (int i = 0; i < 26; i++) {
                if (caratteriCF[caratteriCF.length - 1] == caratteri[i]) {
                    if (valore == i) {
                        return true;
                    } else {
                        return false;
                    }
                }
            }
            return false;
        } else {
            return false;
        }
    }

    /**
     * Verifica della correttezza della Partita iva o del Codice Fiscale della
     * Persona Giuridica (Azienda)
     *
     * @param paramPI
     * @return
     */
    public static boolean isPivaValid(String paramPI) {

        String piva = paramPI.trim();
        try {
            /*
             * verifica che la partita iva sia composta solo da cifre nel caso non sia
             * composta da sole cifre si verifica l'eccezione NumberFormatException e viene
             * restituito il valore 'false'
             */
            Long.parseLong(piva);
            /*
             * verifica che la partita iva sia composta da 11 caratteri
             */
            if (piva.length() == 11) {
                int i = 0;
                int tot = 0;
                /*
                 * somma delle cifre dispari, tranne l'ultima
                 */
                while (i < 10) {
                    tot += Integer.parseInt(piva.substring(i, i + 1));
                    i += 2;
                }
                int pari = 0;
                i = 1;
                /*
                 * somma (al totale delle cifre dispari) delle cifre pari moltiplicate per 2 se
                 * la cifra moltiplicata Ã¯Â¿Â½ maggiore o uguale a 10 si sommano separatamente
                 * le decine e le unitÃ¯Â¿Â½ esempi 3: rimane 3 13: si somma 1 + 4 = 5 10: si
                 * somma 1 + 0 = 1
                 */
                while (i < 11) {
                    pari = (Integer.parseInt(piva.substring(i, i + 1))) * 2;
                    pari = (pari / 10) + (pari % 10);
                    tot += pari;
                    i += 2;
                }
                /*
                 * ultima cifra della partita iva cifra di controllo
                 */
                int control = Integer.parseInt(piva.substring(10, 11));
                /*
                 * verifica: (resto(somma) = 0 E cifra di controllo = 0) oppure (10 -
                 * resto(somma) = cifra di controllo)
                 */
                if (((tot % 10) == 0 && (control == 0)) || ((10 - (tot % 10)) == control)) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean isVocale(String lettera) {
        if (lettera.equalsIgnoreCase("à"))
            lettera = "a";
        if (lettera.equalsIgnoreCase("è"))
            lettera = "e";
        if (lettera.equalsIgnoreCase("é"))
            lettera = "e";
        if (lettera.equalsIgnoreCase("ì"))
            lettera = "i";
        if (lettera.equalsIgnoreCase("ò"))
            lettera = "o";
        if (lettera.equalsIgnoreCase("ù"))
            lettera = "u";
        return vocali.contains(lettera.toLowerCase());
    }


}

