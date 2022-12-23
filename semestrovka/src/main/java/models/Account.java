package models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Account {

    private Long id;

    private String username;

    private String password;

    private String role;

    private List<Long> mangaId = new ArrayList<>();

    public void add (Long id) {
        mangaId.add(id);
    }

    public void removeManga(Long id) {
        mangaId.remove(id);
    }
}
