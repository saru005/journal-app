package net.engineeringdigest.journalApp.entity;

import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "users")
public class User {
    @Id
    private ObjectId id;
    @Indexed(unique = true) //need to enable auto index creating in application.properties
    @NonNull
    private String userName;
    @NonNull
    private String password;

    @DBRef //creating reference of JournalEntry in users collection
    private List<JournalEntry> journalEntries = new ArrayList<>();
    private List<String> roles;
}
