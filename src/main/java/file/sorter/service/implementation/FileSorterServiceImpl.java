package file.sorter.service.implementation;

import file.sorter.dao.ContentDao;
import file.sorter.service.FileSorterService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileSorterServiceImpl implements FileSorterService {

    public static final String EXTENSION_TIF = "%.tif";
    public static final String EXTENSION_MOV = "%.mov";
    public static final String EXTENSION_AVI = "%.avi";
    private final ContentDao contentDao;

    @Autowired
    public FileSorterServiceImpl(ContentDao contentDao) {
        this.contentDao = contentDao;
    }

    @Override
    public void getFiles() {
        List<Long> filesToConvert = getFilesToConvert();
        List<Long> filesToCopy = getAllFiles();

        filesToCopy.removeIf(filesToConvert::contains);
        System.out.println("Список файлов\\директорий на кодирование:");
        contentDao.getFileNamesByIds(filesToConvert).forEach(System.out::println);
        System.out.println("Список файлов\\директорий на копирование:");
        contentDao.getFileNamesByIds(filesToCopy).forEach(System.out::println);
    }

    public List<Long> getAllFiles() {
        return contentDao.getContentIdsToCopy();
    }

    public List<Long> getFilesToConvert() {
        List<Long> ids = new ArrayList<>();
        List<Long> contentIds = contentDao.getContentIdsByFileNameContains(EXTENSION_TIF);
        ids.addAll(contentDao.getParentIdsByContentIds(contentIds));
        ids.addAll(contentDao.getContentIdsByFileNameContains(EXTENSION_MOV));
        ids.addAll(contentDao.getContentIdsByFileNameContains(EXTENSION_AVI));
        return ids;
    }
}
