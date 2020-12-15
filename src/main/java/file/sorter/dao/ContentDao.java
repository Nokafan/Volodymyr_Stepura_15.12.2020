package file.sorter.dao;

import java.util.List;

public interface ContentDao {

    List<Long> getContentIdsByFileNameContains(String contains);

    List<String> getFileNamesByIds(List<Long> contentIds);

    List<Long> getContentIdsToCopy();

    List<Long> getParentIdsByContentIds(List<Long> contentIds);
}
