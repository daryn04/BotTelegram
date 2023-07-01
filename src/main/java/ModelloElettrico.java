public class ModelloElettrico extends Modello {
    private String nomeModello;
    private int batteria;
    private int autonomia;
    private int tempoDiRicarica;

    public ModelloElettrico(String nomeModello, int batteria, int autonomia, int tempoDiRicarica, String nome, String costruttore, int consumo, int cilindrata, int potenza, int cavalli, int numeroPosti, int numeroPorte, int velocitaMax, int prezzo, boolean elettrica) {
        super(nome, costruttore, consumo, cilindrata, potenza, cavalli, numeroPosti, numeroPorte, velocitaMax, prezzo, elettrica);
        this.nomeModello = nomeModello;
        this.batteria = batteria;
        this.autonomia = autonomia;
        this.tempoDiRicarica = tempoDiRicarica;
    }


    public String getNomeModello() {
        return nomeModello;
    }

    public int getBatteria() {
        return batteria;
    }

    public int getAutonomia() {
        return autonomia;
    }

    public int getTempoDiRicarica() {
        return tempoDiRicarica;
    }

    public void setNomeModello(String nomeModello) {
        this.nomeModello = nomeModello;
    }

    public void setBatteria(int batteria) {
        this.batteria = batteria;
    }

    public void setAutonomia(int autonomia) {
        this.autonomia = autonomia;
    }

    public void setTempoDiRicarica(int tempoDiRicarica) {
        this.tempoDiRicarica = tempoDiRicarica;
    }

}


