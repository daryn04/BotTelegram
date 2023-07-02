public class Automobile {
    private String targa;
    private String costruttore;
    private String nomeModello;
    private String alimentazione;
    private String colore;
    private int prezzo;
    private String tipoDiCambio;
    private String materialeCerchione;

    public Automobile(){

    }
    public Automobile(String targa, String nomeModello, String colore, String condizione, int prezzo, String tipoDiCambio, String materialeCerchione){
        this.targa = targa;
        this.nomeModello = nomeModello;
        this.colore = colore;
        this.prezzo = prezzo;
        this.tipoDiCambio = tipoDiCambio;
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

    public int getPrezzo() {
        return prezzo;
    }

    public String getTipoDiCambio() {
        return tipoDiCambio;
    }

    public String getCostruttore(){
        return this.costruttore;
    }

    public String getMaterialeCerchione() {
        return materialeCerchione;
    }

    public void setTarga(String targa) {
        this.targa = targa;
    }

    public void setCostruttore(String costruttore){
        this.costruttore = costruttore;
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

    public void setPrezzo(int prezzo) {
        this.prezzo = prezzo;
    }

    public void setTipoDiCambio(String tipoDiCambio) {
        this.tipoDiCambio = tipoDiCambio;
    }

    public void setMaterialeCerchione(String materialeCerchione) {
        this.materialeCerchione = materialeCerchione;
    }

}

