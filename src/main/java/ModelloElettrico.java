public class ModelloElettrico extends Modello {
    private String nomeModello;
    private int batteria;
    private int autonomia;
    private int tempoDiRicarica;

    public ModelloElettrico(int batteria, int autonomia, int tempoDiRicarica, String nome, String costruttore, int consumo, int cilindrata, int potenza, int cavalli, int numeroPosti, int numeroPorte, int velocitaMax, int prezzo) {
        super(nome, costruttore, consumo, cilindrata, potenza, cavalli, numeroPosti, numeroPorte, velocitaMax, prezzo);
        this.nomeModello = nome;
        this.batteria = batteria;
        this.autonomia = autonomia;
        this.tempoDiRicarica = tempoDiRicarica;
    }


    @Override
    public String toString() {
        return  "Nome: " + nomeModello + '\n' +
                "Costruttore: " + super.getCostruttore() + '\n' +
                "Consumo: " + super.getConsumo() + " kW/100 km" + '\n' +
                "Potenza: " + super.getPotenza() + " kW" + '\n' +
                "Cavalli: " + super.getCavalli() + '\n' +
                "Numero dei posti: " + super.getNumeroPosti() + '\n' +
                "Numero delle porte: " + super.getNumeroPorte() + '\n' +
                "Velocità massima: " + super.getVelocitaMax() + " km/h" + '\n' +
                "Prezzo: " + super.getPrezzo() + " x€" + '\n' +
                "Batteria: " + batteria + " kWh" + '\n' +
                "Autonomia: " + autonomia + " km" + '\n' +
                "Tempo di ricarica: " + tempoDiRicarica + " h";
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


