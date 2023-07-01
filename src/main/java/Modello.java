public class Modello {
    private String nome;
    private String costruttore;
    private int consumo;
    private int cilindrata;
    private int potenza;
    private int cavalli;
    private int numeroPosti;
    private int numeroPorte;
    private int velocitaMax;
    private int prezzo;
    private boolean elettrica;

    public Modello(String nome, String costruttore, int consumo, int cilindrata, int potenza, int cavalli, int numeroPosti, int numeroPorte, int velocitaMax, int prezzo, boolean elettrica) {
        this.nome = nome;
        this.costruttore = costruttore;
        this.consumo = consumo;
        this.cilindrata = cilindrata;
        this.potenza = potenza;
        this.cavalli = cavalli;
        this.numeroPosti = numeroPosti;
        this.numeroPorte = numeroPorte;
        this.velocitaMax = velocitaMax;
        this.prezzo = prezzo;
        this.elettrica = elettrica;
    }

    public String getNome() {
        return nome;
    }

    public String getCostruttore() {
        return costruttore;
    }

    public int getConsumo() {
        return consumo;
    }

    public int getCilindrata() {
        return cilindrata;
    }

    public int getPotenza() {
        return potenza;
    }

    public int getCavalli() {
        return cavalli;
    }

    public int getNumeroPosti() {
        return numeroPosti;
    }

    public int getNumeroPorte() {
        return numeroPorte;
    }

    public int getVelocitaMax() {
        return velocitaMax;
    }

    public int getPrezzo() {
        return prezzo;
    }

    public boolean isElettrica() {
        return elettrica;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCostruttore(String costruttore) {
        this.costruttore = costruttore;
    }

    public void setConsumo(int consumo) {
        this.consumo = consumo;
    }

    public void setCilindrata(int cilindrata) {
        this.cilindrata = cilindrata;
    }

    public void setPotenza(int potenza) {
        this.potenza = potenza;
    }

    public void setCavalli(int cavalli) {
        this.cavalli = cavalli;
    }

    public void setNumeroPosti(int numeroPosti) {
        this.numeroPosti = numeroPosti;
    }

    public void setNumeroPorte(int numeroPorte) {
        this.numeroPorte = numeroPorte;
    }

    public void setVelocitaMax(int velocitaMax) {
        this.velocitaMax = velocitaMax;
    }

    public void setPrezzo(int prezzo) {
        this.prezzo = prezzo;
    }

    public void setElettrica(boolean elettrica) {
        this.elettrica = elettrica;
    }


}
