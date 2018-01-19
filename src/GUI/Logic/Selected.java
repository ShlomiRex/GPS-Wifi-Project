package GUI.Logic;

import java.io.File;

public final class Selected extends File {
    public SelectedFileType type = SelectedFileType.Null;
    public Selected(String pathname, SelectedFileType type) {
        super(pathname);
        this.type = type;
    }
}
