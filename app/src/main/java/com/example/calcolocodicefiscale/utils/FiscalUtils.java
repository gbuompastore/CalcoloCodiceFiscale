package com.example.calcolocodicefiscale.utils;

import java.math.BigDecimal;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FiscalUtils {

    private static final List<String> vocali = Arrays.asList("a", "e", "i", "o", "u");

    private static final String[] codiciMese = {
            "", "A", "B", "C", "D", "E", "H", "L", "M", "P", "R", "S", "T"
    };

    private static final Map<String, String> charDispari = Stream.of(new String[][]{
            {"0", "1"}, {"1", "0"}, {"2", "5"}, {"3", "7"}, {"4", "9"},
            {"5", "13"}, {"6", "15"}, {"7", "17"}, {"8", "19"}, {"9", "21"},
            {"A", "1"}, {"B", "0"}, {"C", "5"}, {"D", "7"}, {"E", "9"},
            {"F", "13"}, {"G", "15"}, {"H", "17"}, {"I", "19"}, {"J", "21"},
            {"K", "2"}, {"L", "4"}, {"M", "18"}, {"N", "20"}, {"O", "11"},
            {"P", "3"}, {"Q", "6"}, {"R", "8"}, {"S", "12"}, {"T", "14"},
            {"U", "16"}, {"V", "10"}, {"W", "22"}, {"X", "25"}, {"Y", "24"},
            {"Z", "23"}
    }).collect(Collectors.toMap(data -> data[0], data -> data[1]));

    private static final Map<String, String> charPari = Stream.of(new String[][]{
            {"0", "0"}, {"1", "1"}, {"2", "2"}, {"3", "3"}, {"4", "4"},
            {"5", "5"}, {"6", "6"}, {"7", "7"}, {"8", "8"}, {"9", "9"},
            {"A", "0"}, {"B", "1"}, {"C", "2"}, {"D", "3"}, {"E", "4"},
            {"F", "5"}, {"G", "6"}, {"H", "7"}, {"I", "8"}, {"J", "9"},
            {"K", "10"}, {"L", "11"}, {"M", "12"}, {"N", "13"}, {"O", "14"},
            {"P", "15"}, {"Q", "16"}, {"R", "17"}, {"S", "18"}, {"T", "19"},
            {"U", "20"}, {"V", "21"}, {"W", "22"}, {"X", "23"}, {"Y", "24"},
            {"Z", "25"}
    }).collect(Collectors.toMap(data -> data[0], data -> data[1]));

    private FiscalUtils() {
        // Utility class: costruttore privato
    }

    /**
     * Calcolo del codice fiscale per persona fisica.
     *
     * @param cognome         cognome della persona
     * @param nome            nome della persona
     * @param codiceBelfiore  codice Belfiore del comune di nascita
     * @param dataNascita     data di nascita
     * @param sesso           "M" oppure "F"
     * @return codice fiscale calcolato
     */
    public static String creaCodiceFiscale(String cognome,
                                           String nome,
                                           String codiceBelfiore,
                                           LocalDate dataNascita,
                                           String sesso) {

        if (isBlank(cognome) || isBlank(nome) || isBlank(codiceBelfiore) || dataNascita == null || isBlank(sesso)) {
            throw new IllegalArgumentException("Impossibile calcolare il codice fiscale. Parametri incompleti o non corretti");
        }

        cognome = normalizzaTesto(cognome);
        nome = normalizzaTesto(nome);
        codiceBelfiore = codiceBelfiore.trim().toUpperCase();
        sesso = sesso.trim().toUpperCase();

        if (!sesso.equals("M") && !sesso.equals("F")) {
            throw new IllegalArgumentException("Il sesso deve essere 'M' oppure 'F'");
        }

        if (codiceBelfiore.length() != 4) {
            throw new IllegalArgumentException("Il codice Belfiore deve contenere 4 caratteri");
        }

        String codiceCognome = calcolaCodiceCognome(cognome);
        String codiceNome = calcolaCodiceNome(nome);
        String anno = String.format("%02d", dataNascita.getYear() % 100);
        String mese = codiciMese[dataNascita.getMonthValue()];
        String giorno = calcolaGiornoCodiceFiscale(dataNascita, sesso);

        String codiceParziale = codiceCognome + codiceNome + anno + mese + giorno + codiceBelfiore;
        String carattereControllo = calcolaCarattereControllo(codiceParziale);

        return (codiceParziale + carattereControllo).toUpperCase();
    }

    /**
     * Verifica correttezza codice IBAN.
     *
     * @param iban codice IBAN
     * @return true se valido
     */
    public static boolean isIbanValid(String iban) {
        if (iban == null) {
            return false;
        }

        iban = iban.replace(" ", "").toUpperCase();

        if (iban.length() < 5) {
            return false;
        }

        try {
            String rearrangedIban = iban.substring(4) + iban.substring(0, 4);
            CharacterIterator iterator = new StringCharacterIterator(rearrangedIban);
            StringBuilder builder = new StringBuilder();

            for (char ch = iterator.first(); ch != CharacterIterator.DONE; ch = iterator.next()) {
                if (ch >= 'A' && ch <= 'Z') {
                    builder.append(ch - 55);
                } else if (Character.isDigit(ch)) {
                    builder.append(ch);
                } else {
                    return false;
                }
            }

            BigDecimal ibanConvertito = new BigDecimal(builder.toString());
            BigDecimal resto = ibanConvertito.remainder(BigDecimal.valueOf(97));

            return resto.intValue() == 1;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Verifica correttezza codice fiscale persona fisica.
     *
     * @param parCf codice fiscale da verificare
     * @return true se valido
     */
    public static boolean isCfValid(String parCf) {
        if (parCf == null) {
            return false;
        }

        String cf = parCf.trim().toUpperCase();

        if (!cf.matches("[A-Z0-9]{16}")) {
            return false;
        }

        char[] caratteri = {
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
                'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
        };

        int[] valoriDispari = {
                1, 0, 5, 7, 9, 13, 15, 17, 19, 21, 2, 4, 18, 20, 11, 3, 6, 8,
                12, 14, 16, 10, 22, 25, 24, 23, 1, 0, 5, 7, 9, 13, 15, 17, 19, 21
        };

        int[] valoriPari = new int[36];
        for (int i = 0; i < 26; i++) {
            valoriPari[i] = i;
        }
        for (int i = 26; i < 36; i++) {
            valoriPari[i] = i - 26;
        }

        char[] caratteriCf = cf.toCharArray();
        int valore = 0;

        for (int i = 0; i < caratteriCf.length - 1; i++) {
            for (int j = 0; j < caratteri.length; j++) {
                if (caratteriCf[i] == caratteri[j]) {
                    if ((i + 1) % 2 == 0) {
                        valore += valoriPari[j];
                    } else {
                        valore += valoriDispari[j];
                    }
                    break;
                }
            }
        }

        valore %= 26;

        for (int i = 0; i < 26; i++) {
            if (caratteriCf[caratteriCf.length - 1] == caratteri[i]) {
                return valore == i;
            }
        }

        return false;
    }

    /**
     * Verifica correttezza partita IVA.
     *
     * @param paramPiva partita IVA da verificare
     * @return true se valida
     */
    public static boolean isPivaValid(String paramPiva) {
        if (paramPiva == null) {
            return false;
        }

        String piva = paramPiva.trim();

        try {
            Long.parseLong(piva);

            if (piva.length() != 11) {
                return false;
            }

            int totale = 0;

            for (int i = 0; i < 10; i += 2) {
                totale += Integer.parseInt(piva.substring(i, i + 1));
            }

            for (int i = 1; i < 10; i += 2) {
                int pari = Integer.parseInt(piva.substring(i, i + 1)) * 2;
                pari = (pari / 10) + (pari % 10);
                totale += pari;
            }

            int cifraControllo = Integer.parseInt(piva.substring(10, 11));

            return ((totale % 10) == 0 && cifraControllo == 0)
                    || ((10 - (totale % 10)) == cifraControllo);

        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static String calcolaCodiceCognome(String cognome) {
        String consonanti = estraiConsonanti(cognome);
        String vocali = estraiVocali(cognome);
        String risultato = consonanti + vocali + "XXX";
        return risultato.substring(0, 3).toUpperCase();
    }

    private static String calcolaCodiceNome(String nome) {
        String consonanti = estraiConsonanti(nome);
        String vocali = estraiVocali(nome);

        if (consonanti.length() >= 4) {
            return ("" + consonanti.charAt(0) + consonanti.charAt(2) + consonanti.charAt(3)).toUpperCase();
        }

        String risultato = consonanti + vocali + "XXX";
        return risultato.substring(0, 3).toUpperCase();
    }

    private static String estraiConsonanti(String testo) {
        StringBuilder stringBuilder = new StringBuilder();

        for (char ch : testo.toCharArray()) {
            String lettera = String.valueOf(ch);
            if (Character.isLetter(ch) && !isVocale(lettera)) {
                stringBuilder.append(lettera);
            }
        }

        return stringBuilder.toString();
    }

    private static String estraiVocali(String testo) {
        StringBuilder stringBuilder = new StringBuilder();

        for (char ch : testo.toCharArray()) {
            String lettera = String.valueOf(ch);
            if (Character.isLetter(ch) && isVocale(lettera)) {
                stringBuilder.append(lettera);
            }
        }

        return stringBuilder.toString();
    }

    private static String calcolaGiornoCodiceFiscale(LocalDate dataNascita, String sesso) {
        int giorno = dataNascita.getDayOfMonth();

        if ("F".equalsIgnoreCase(sesso)) {
            giorno += 40;
        }

        return String.format("%02d", giorno);
    }

    private static String calcolaCarattereControllo(String codiceParziale) {
        int sommaPari = 0;
        for (int i = 1; i <= 13; i += 2) {
            String ch = String.valueOf(codiceParziale.charAt(i)).toUpperCase();
            sommaPari += Integer.parseInt(charPari.get(ch));
        }

        int sommaDispari = 0;
        for (int i = 0; i <= 14; i += 2) {
            String ch = String.valueOf(codiceParziale.charAt(i)).toUpperCase();
            sommaDispari += Integer.parseInt(charDispari.get(ch));
        }

        int resto = (sommaPari + sommaDispari) % 26;
        return String.valueOf((char) ('A' + resto));
    }

    private static boolean isVocale(String lettera) {
        if (lettera.equalsIgnoreCase("à")) {
            lettera = "a";
        } else if (lettera.equalsIgnoreCase("è") || lettera.equalsIgnoreCase("é")) {
            lettera = "e";
        } else if (lettera.equalsIgnoreCase("ì")) {
            lettera = "i";
        } else if (lettera.equalsIgnoreCase("ò")) {
            lettera = "o";
        } else if (lettera.equalsIgnoreCase("ù")) {
            lettera = "u";
        }

        return vocali.contains(lettera.toLowerCase());
    }

    private static String normalizzaTesto(String testo) {
        return testo.trim()
                .replace(" ", "")
                .replace("'", "")
                .toUpperCase();
    }

    private static boolean isBlank(String testo) {
        return testo == null || testo.trim().isEmpty();
    }
}