# Resultado das Vantagens do Relógio Físico

## Medição de passagem de tempo (Timeouts e Leases)

Relógios físicos são indispensáveis quando o sistema precisa realizar operações que não estejam relacionadas com um período congelado no tempo, mas sim com a medição de um período contínuo. Um relógio físico deve ser usado como um cronômetro, não como um relógio de parede.

![Vantagem - Lock Distribuído](https://github.com/pbelem/PhysicalClockDistributedSystems/blob/main/assets/Screenshot_20260323_150022.png)

**Exemplo Prático: Lock com Prazo de Validade**
Dois nós (Nó A e Nó B) processam pagamentos e não podem processar o mesmo pagamento ao mesmo tempo. Eles precisam de uma trava (*Lock*) em um servidor centralizado. 

 Se o Nó A pegar o Lock e, logo em seguida, a máquina dele pegar fogo (*crash*), o Nó B ficará esperando para sempre?  
 **Não**, porque usamos o relógio físico para dar um "prazo de validade" (*Lease/TTL*) para esse Lock. Se o tempo físico expirar, o sistema assume que o Nó A morreu e libera o Lock para o Nó B.

Assim como o servidor, o cliente também pode usar seu relógio monotônico local para medir internamente a passagem desses 2 segundos para saber que perdeu o direito ao lock (mesmo sem falar com o servidor).

---

# Resultado das Desvantagens do Relógio Físico

## Anomalia do "Último a Escrever Vence" (Last Write Wins - LWW)

Se um *timestamp* da máquina local é usado para decidir qual versão de um dado salvar no banco de dados, uma máquina com o relógio adiantado pode sobrescrever dados criados por uma máquina com o relógio correto.

![Desvantagem - Last Write Wins](https://github.com/pbelem/PhysicalClockDistributedSystems/blob/main/assets/Screenshot_20260323_140021.png)

**Exemplo Prático: Perda de Dados por Relógio Adiantado**
Este exemplo simula dois nós de uma rede escrevendo no mesmo registro. O Nó B escreve *depois* do Nó A, mas porque o relógio do Nó A está desincronizado (adiantado), o banco de dados acha que a escrita do Nó A é a mais recente, rejeitando a atualização legítima.
