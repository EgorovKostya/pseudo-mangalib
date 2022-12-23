package models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Manga {

    private Long id;

    private String name;

    private String description;

    private Integer pageCount;

    private String url;

    private String extension;

    private String type;

    private String startDate;

    private String status;

    private String author;

    private String alternativeName;

    private String fullDescription;

    private String link;
}
