package com.hilagangluzon.foodhub;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

public class FirestoreTools
{
    private FirebaseFirestore db;

    public FirestoreTools(FirebaseFirestore db)
    {
        this.db = db;
    }

    public CollectionReference referToCollection(String from) {
        return db.collection(from);
    }

    public Task<QuerySnapshot> select(String from)
    {
        return db.collection(from).get();
    }

    public Task<DocumentSnapshot> select(String from, String key)
    {
        return db.collection(from).document(key).get();
    }

    public void insert(String into, Object record)
    {
        db.collection(into).add(record);
    }

    public void update(String what, String key, Map<String, Object> fields)
    {
        db.collection(what).document(key).update(fields);
    }

    public void delete(String from, String key)
    {
        db.collection(from).document(key).delete();
    }
}
