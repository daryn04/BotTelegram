public class Automobile {
    private String targa;
    private String nomeModello;
    private String alimentazione;
    private String colore;
    private String stato;
    private int prezzo;
    private String tipoDiCambio;
    private int chilometraggio;
    private String materialeCerchione;

    public Automobile(String targa, String nomeModello, String colore, String stato, int prezzo, String tipoDiCambio, int chilometraggio, String materialeCerchione){
        this.targa = targa;
        this.nomeModello = nomeModello;
        this.colore = colore;
        this.stato = stato;
        this.prezzo = prezzo;
        this.tipoDiCambio = tipoDiCambio;
        this.chilometraggio = chilometraggio;
        this.materialeCerchione = materialeCerchione;
    }

    public String getTarga() {
        return targa;
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

    public String getStato() {
        return stato;
    }

    public int getPrezzo() {
        return prezzo;
    }

    public String getTipoDiCambio() {
        return tipoDiCambio;
    }

    public int getChilometraggio() {
        return chilometraggio;
    }

    public String getMaterialeCerchione() {
        return materialeCerchione;
    }

    public void setTarga(String targa) {
        this.targa = targa;
    }

    public void setNomeModello(String nomeModello) {
        this.nomeModello = nomeModello;
    }

    public void setAlimentazione(String alimentazione) {
        this.alimentazione = alimentazione;
    }

    public void setColore(String colore) {
        this.colore = colore;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public void setPrezzo(int prezzo) {
        this.prezzo = prezzo;
    }

    public void setTipoDiCambio(String tipoDiCambio) {
        this.tipoDiCambio = tipoDiCambio;
    }

    public void setChilometraggio(int chilometraggio) {
        this.chilometraggio = chilometraggio;
    }

    public void setMaterialeCerchione(String materialeCerchione) {
        this.materialeCerchione = materialeCerchione;
    }

}

