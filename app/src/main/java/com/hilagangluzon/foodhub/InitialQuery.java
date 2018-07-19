package com.hilagangluzon.foodhub;

/**
 * Created by Student on 7/19/2018.
 */

public class InitialQuery
{
    FirebaseFirestore db;
    FirestoreTools fs;

    public InitialQuery()
    {
        db = FirebaseFirestore.getInstance();
        fs = new FirestoreTools(db);
    }

    public executeQueries()
    {
        String coll = "products";

        Product p = new Product();

        p.setName("Iced Coffee");
        p.setDescription("Low temperature coffee.");
        p.setPrice(120);
        p.setIn_stock(5);
        p.setCategory("Cold Drink");
        fs.insert(coll, p);

        p.setName("Iced Cappuccino");
        p.setDescription("Low temperature coffee.");
        p.setPrice(120);
        p.setIn_stock(5);
        p.setCategory("Cold Drink");
        fs.insert(coll, p);

        p.setName("Iced Latte");
        p.setDescription("Low temperature Latte.");
        p.setPrice(120);
        p.setIn_stock(5);
        p.setCategory("Cold Drink");
        fs.insert(coll, p);

        p.setName("Iced Coffee Mocha");
        p.setDescription("Low temperature Coffee Mocha.");
        p.setPrice(120);
        p.setIn_stock(5);
        p.setCategory("Cold Drink");
        fs.insert(coll, p);

        p.setName("Iced Caramel Macchiato");
        p.setDescription("Low temperature Caramel Macchiato.");
        p.setPrice(120);
        p.setIn_stock(5);
        p.setCategory("Cold Drink");
        fs.insert(coll, p);

        p.setName("Tuna Sandwich");
        p.setDescription("Tunaw na SandWitch.");
        p.setPrice(80);
        p.setIn_stock(5);
        p.setCategory("Sandwich");
        fs.insert(coll, p);

        p.setName("Egg Sandwich");
        p.setDescription("Itlog na bruhangin.");
        p.setPrice(80);
        p.setIn_stock(5);
        p.setCategory("Sandwich");
        fs.insert(coll, p);

        p.setName("Ham & Cheese Sandwich");
        p.setDescription("Hammered Cheese.");
        p.setPrice(100);
        p.setIn_stock(5);
        p.setCategory("Sandwich");
        fs.insert(coll, p);

        p.setName("Chips");
        p.setDescription("For cheap people.");
        p.setPrice(50);
        p.setIn_stock(5);
        p.setCategory("Sandwich");
        fs.insert(coll, p);

        p.setName("Soda");
        p.setDescription("Sodank.");
        p.setPrice(50);
        p.setIn_stock(5);
        p.setCategory("Sandwich");
        fs.insert(coll, p);

        p.setName("Sola");
        p.setDescription("Sola flavored drink.");
        p.setPrice(60);
        p.setIn_stock(5);
        p.setCategory("Bottled Drinks");
        fs.insert(coll, p); //Bottled Drinks

        p.setName("Boiled Water");
        p.setDescription("Burning Water.(For those who can't afford anything.)");
        p.setPrice(25);
        p.setIn_stock(5);
        p.setCategory("Bottled Drinks");
        fs.insert(coll, p);



    }

}
