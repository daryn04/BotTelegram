public class Automobile {
    private String targa;
    private String nomeModello;
    private String alimentazione;
    private String colore;
    private String condizione;
    private int prezzo;
    private String tipoDiCambio;
    private String materialeCerchione;

    public Automobile(){

    }
    public Automobile(String targa, String nomeModello, String alimentazione, String colore, String condizione, int prezzo, String tipoDiCambio, String materialeCerchione){
        this.targa = targa;
        this.nomeModello = nomeModello;
        this.alimentazione = alimentazione;
        this.colore = colore;
        this.condizione = condizione;
        this.prezzo = prezzo;
        this.tipoDiCambio = tipoDiCambio;
        this.materialeCerchione = materialeCerchione;
    }

    @Override
    public String toString() {
        return "Targa: " + targa + '\n' +
                " Nome del modello: " + nomeModello + '\n' +
                " Alimentazione: " + alimentazione + '\n' +
                " Colore: " + colore + '\n' +
                " Condizione: " + condizione + '\n' +
                " Prezzo: " + prezzo + "€" + '\n' +
                " Tipo di cambio: " + tipoDiCambio + '\n' +
                " Materiale del cerchione: " + materialeCerchione + '\n';
    }

    public String getNomeModello() {
        return nomeModello;
    }

    public String getAlimentazione() {
        return alimentazione;
    }

    public String getColore() {
        return colore;
    }

    public int getPrezzo() {
        return prezzo;
    }

    public String getTipoDiCambio() {
        return tipoDiCambio;
    }


    public String getMaterialeCerchione() {
        return materialeCerchione;
    }

    public void setNomeModello(String nomeModello) {
        this.nomeModello = nomeModello;
    }

    public void setAlimentazione(String alimentazione) {
        if (alimentazione.equals("Benzina") || alimentazione.equals("Diesel")) {
            this.alimentazione = alimentazione;
        }
    }

    public void setColore(String colore) {
        this.colore = colore;
    }

    public void setPrezzo(int prezzo) {
        this.prezzo = prezzo;
    }

    public void setTipoDiCambio(String tipoDiCambio) {
        if (tipoDiCambio.equals("Manuale") || tipoDiCambio.equals("Automatico")) {
            this.tipoDiCambio = tipoDiCambio;
        }
    }

    public void setMaterialeCerchione(String materialeCerchione) {
        if (materialeCerchione.equals("Lega") || materialeCerchione.equals("Acciaio")) {
            this.materialeCerchione = materialeCerchione;
        }
    }
}

