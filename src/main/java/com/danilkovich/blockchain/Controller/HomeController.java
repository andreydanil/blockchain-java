package com.danilkovich.blockchain.Controller;

import com.danilkovich.blockchain.BlockchainApplication;
import com.danilkovich.blockchain.Implementation.Block;
import com.google.gson.GsonBuilder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;

@RestController
public class HomeController {

    public static ArrayList<Block> blockchain = new ArrayList<Block>();
    public static int difficulty = 1;
    public static int counter = 0;

    @RequestMapping("/")
    public String index() {
        return "Possible methods: read\ncreate\ndrop\nvalid\nsend/{from}/{to}/{amount}";
    }

    /**
     * Display the blockchain in JSON
     * @return JSON
     */
    @RequestMapping("/read")
    public String read() {
        String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
        System.out.println("\nThe block chain: ");
        return blockchainJson;
    }

    /**
     * Create a node in the blockchain
     * @param data
     * @return
     */
    @RequestMapping("/create/{data}")
    public String create(@PathVariable("data") String data) {
        String hash = null;
        if(blockchain.size() != 0) {
            hash = blockchain.get(blockchain.size()-1).hash;
        } else {
            hash = "0";
        }

        try{
            blockchain.add(new Block(data, hash));
            System.out.println("Mining block: " +hash + ", counter: " + counter + "... ");
            blockchain.get(counter).mineBlock(difficulty);
            counter++;
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
        }


        return "Creating block " + hash;
    }

    /**
     * Delete a node from the blockchain given the node
     * @param data
     * @return
     */
    @RequestMapping("/drop/hash")
    public String drop(@PathVariable("data") String data) {
        return "Dropping ";
    }

    /**
     * Check if the blockchain is valid
     * @return
     */
    @RequestMapping("/valid")
    public String valid() {
        return BlockchainApplication.isChainValid().toString();
    }

    @RequestMapping("/send/{from}/{to}/{amount}")
    public String send(@PathVariable("from") String from, @PathVariable("to") String to, @PathVariable("amount") double amount) {
        return "Sending amount: $" + amount + ", from: " + from + " to: " + to;
    }



}