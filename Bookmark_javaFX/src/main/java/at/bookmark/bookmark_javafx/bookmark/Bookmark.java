package at.bookmark.bookmark_javafx.bookmark;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Bookmark {
    private String title;
    private String page;
    private String link;
    private int num;
}
