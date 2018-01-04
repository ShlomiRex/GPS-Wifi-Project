package GUI.Logic;

import java.io.File;

public final class Selected extends File {
    public static SelectedFileType type;
    public Selected(String pathname, SelectedFileType type) {
        super(pathname);
        this.type = type;
    }
}
