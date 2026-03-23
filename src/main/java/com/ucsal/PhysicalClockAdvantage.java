package com.ucsal;

public class PhysicalClockAdvantage {

    static class LockManager {
        private String ownerNodeId = null;
        private long lockExpirationTimeMs = 0;

        public boolean tryAcquireLock(String nodeId, long leaseDurationMs) {
            long tempoFisicoAtual = System.currentTimeMillis();

            if (ownerNodeId == null || tempoFisicoAtual > lockExpirationTimeMs) {
                ownerNodeId = nodeId;
                lockExpirationTimeMs = tempoFisicoAtual + leaseDurationMs;
                System.out.println("-> Lock concedido ao " + nodeId + " até o milissegundo " + lockExpirationTimeMs);
                return true;
            }

            System.out.println("-> " + nodeId + " tentou pegar o lock, mas está ocupado pelo " + ownerNodeId);
            return false;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        LockManager lockManager = new LockManager();

        System.out.println("--- Início do Processamento Distribuído ---");

        System.out.println("Nó A solicitando lock por 2 segundos...");
        lockManager.tryAcquireLock("Nó_A", 2000);

        System.out.println("\n[ALERTA] Nó A sofreu um crash e parou de responder!\n");

        System.out.println("Nó B solicitando lock...");
        lockManager.tryAcquireLock("Nó_B", 2000);

        System.out.println("\nEsperando o tempo físico passar (3 segundos)...\n");
        Thread.sleep(3000);

        System.out.println("Nó B solicitando lock novamente...");
        lockManager.tryAcquireLock("Nó_B", 2000);

        System.out.println("O sistema continuou funcionando porque o tempo físico detectou a falha do Nó A.");
    }
}
