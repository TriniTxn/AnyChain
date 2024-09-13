import com.google.gson.GsonBuilder;

import java.util.ArrayList;

public class Main {

    //Armazenamento das blockchains
    public static ArrayList<Block> blockchain = new ArrayList<Block>();
    //Ajuste da dificuldade do algoritmo de criação
    public static int difficulty = 5;

    public static void main(String[] args) {

        //Adição manual de mineração
        blockchain.add(new Block("Primeiro bloco", "0"));
        System.out.println("Tentando minerar o bloco 1...");
        blockchain.get(0).mineBlock(difficulty);

        blockchain.add(new Block("Segundo bloco", blockchain.get(blockchain.size()-1).hash));
        System.out.println("Tentando minerar o bloco 2...");
        blockchain.get(1).mineBlock(difficulty);

        blockchain.add(new Block("Terceiro bloco", blockchain.get(blockchain.size()-1).hash));
        System.out.println("Tentando minerar o bloco 3...");
        blockchain.get(2).mineBlock(difficulty);

        System.out.println("\nBlockchain é válida: " + isChainValid());

        //Transformando o objeto para JSON
        String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
        System.out.println("\nThe blockchain: ");
        System.out.println(blockchainJson);
    }

    //Validação da blockchain
    public static Boolean isChainValid() {
        Block currentBlock;
        Block previousBlock;
        String hashTarget = new String(new char[difficulty]).replace('\0', '0');

        for(int i = 0; i < blockchain.size(); i++) {
            currentBlock = blockchain.get(i);
            previousBlock = blockchain.get(i-1);

            //Validação do hash atual
            if (!currentBlock.hash.equals(currentBlock.calculateHash())) {
                System.out.println("O hash atual não é correspondente");
                return false;
            }
            //Validação do hash anterior
            if (!previousBlock.hash.equals(currentBlock.previousHash)) {
                System.out.println("Hash anterior não é correspondente");
                return false;
            }
            //Validação da integridade do bloco
            if(!currentBlock.hash.substring(0, difficulty).equals(hashTarget)) {
                System.out.println("Esse bloco não foi minerado!");
                return false;
            }
        }
        return true;
    }
}