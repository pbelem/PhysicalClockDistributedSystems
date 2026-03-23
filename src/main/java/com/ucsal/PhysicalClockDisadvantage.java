package com.ucsal;

public class PhysicalClockDisadvantage {

    static class DatabaseRecord {
        String value;
        long timestamp;

        public DatabaseRecord(String value, long timestamp) {
            this.value = value;
            this.timestamp = timestamp;
        }

        public void updateIfNewer(String newValue, long newTimestamp) {
            if (newTimestamp > this.timestamp) {
                this.value = newValue;
                this.timestamp = newTimestamp;
                System.out.println("DB Atualizado para: " + newValue);
            } else {
                System.out.println("DB Rejeitou a atualização: " + newValue + " (Timestamp antigo)");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        DatabaseRecord db = new DatabaseRecord("Estado Inicial", 0);

        long tempoReal = System.currentTimeMillis();

        long clockNoA = tempoReal + 5000;

        long clockNoB = tempoReal;

        System.out.println("--- Início da Simulação ---");

        System.out.println("Nó A tenta atualizar...");
        db.updateIfNewer("Valor do Nó A", clockNoA);

        Thread.sleep(2000);
        clockNoB = System.currentTimeMillis();

        System.out.println("Nó B tenta atualizar (2 segundos depois da vida real)...");
        db.updateIfNewer("Valor do Nó B", clockNoB);

        System.out.println("\nValor final no DB: " + db.value);
    }
}
