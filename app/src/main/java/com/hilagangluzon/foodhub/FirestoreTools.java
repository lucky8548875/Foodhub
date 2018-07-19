package com.hilagangluzon.foodhub;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class FirestoreTools
{
    private FirebaseFirestore db;

    public FirestoreTools(FirebaseFirestore db)
    {
        this.db = db;
    }

    public CollectionReference referToCollection(String collection) {
        return db.collection(collection);
    }

    public Task<QuerySnapshot> selectAll(String from)
    {
        return db.collection(from).get();
    }

    public Task<DocumentSnapshot> select(String from, String key)
    {
        return db.collection(from).document(key).get();
    }

    public Task<QuerySnapshot> select(String from, String where, String operator, String value)
    {
        Query task = db.collection(from);
        switch(operator)
        {
            case "=":
                task = task.whereEqualTo(where, value);
                break;
        }
        return task.get();
    }

    /*public Task<DocumentSnapshot> select(DocumentReference docRef)
    {
        return db.document(docRef.getPath()).get();
    }*/

    public void insert(String into, Object record)
    {
        db.collection(into).add(record);
        //db.collection("").whereEqualTo("userid", 132131);
    }

    public void update(String what, String key, Map<String, Object> fields)
    {
        db.collection(what).document(key).update(fields);
    }

    public void update(String what, String key, Object record)
    {
        db.collection(what).document(key).set(record);
    }

    public void delete(String from, String key)
    {
        db.collection(from).document(key).delete();
    }

    /*public HashMap<String, Object> join(final String from, final String on, final String reference)
    {
        final HashMap<String, Object> map = new HashMap<>();

        select(from).addOnCompleteListener(
                new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for(DocumentSnapshot fromDoc: task.getResult())
                        {
                            select(on, fromDoc.get(reference).toString()).addOnSuccessListener(
                                    new OnSuccessListener<DocumentSnapshot>() {
                                        @Override
                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                            map.put(documentSnapshot.getId(), documentSnapshot.getData());
                                        }
                                    }
                            );
                        }
                    }
                }
        );

        return map;
    }*/
}
